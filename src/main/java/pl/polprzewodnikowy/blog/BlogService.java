package pl.polprzewodnikowy.blog;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.polprzewodnikowy.user.UserService;

import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class BlogService {

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private Parser parser;

    @Autowired
    private HtmlRenderer htmlRenderer;

    private SessionFactory sessionFactory;

    private Integer pageSize = 5;

    public BlogService(EntityManagerFactory entityManagerFactory) {
        sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    public Entry getEntryByIdUnformatted(Integer id) {
        return entryRepository.findById(id).get();
    }

    public Entry getEntryByIdFormatted(Integer id) {
        return prepareEntryFormatting(getEntryByIdUnformatted(id));
    }

    public List<Entry> getPage(Integer page) {
        return entryQuery("from Entry order by entry_id desc", pageSize, page);
    }

    public Integer getTotalPages() {
        long count = entryRepository.count();
        return (int)(count / pageSize) + (count % pageSize == 0 ? 0 : 1);
    }

    public void addOrEditEntry(Entry entry) {
        if (entry.getAuthor() == null) {
            entry.setAuthor(userService.getCurrentUser());
        }
        if (entry.getTimestamp() == null) {
            entry.setTimestamp(new Date());
        }
        entryRepository.save(entry);
    }

    public void deleteEntryById(Integer id) {
        entryRepository.deleteById(id);
    }

    public Entry prepareEntryFormatting(Entry entry) {
        entry.setBody(markdownToHtml(entry.getBody()));
        return entry;
    }

    public List<Entry> searchEntries(String query, Integer page) {
        return entryQuery("from Entry e where e.body like ?1 order by entry_id desc", pageSize, page, "%" + query + "%");
    }

    public void addPageNumbersToModel(Model model) {
        int totalPages = getTotalPages();
        model.addAttribute("pages", totalPages);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }

    private List<Entry> entryQuery(String query, Integer items, Integer page, Object... parameters) {
        List<Entry> entries;

        try (Session session = sessionFactory.openSession()) {
            Query<Entry> entryQuery = session.createQuery(query, Entry.class);
            entryQuery.setFirstResult((page - 1) * items);
            entryQuery.setMaxResults(items);

            Integer index = 1;
            for (Object parameter : parameters) {
                entryQuery.setParameter(index++, parameter);
            }

            entries = entryQuery.getResultList();

            for (Entry entry : entries) {
                entry.setBody(markdownToHtml(entry.getBody()));
            }
        } catch (Exception e) {
            throw e;
        }

        return entries;
    }

    private String markdownToHtml(String text) {
        Node document = parser.parse(text);
        return htmlRenderer.render(document);
    }

}
