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

    public void addPageNumbersToModel(Model model) {
        int totalPages = getTotalPages();
        model.addAttribute("pages", totalPages);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }

    public Entry getEntryById(Integer id) {
        Entry entry = entryRepository.findById(id).get();
        entry.setBody(markdownToHtml(entry.getBody()));
        return entry;
    }

    public void addNewEntry(Entry entry) {
        entryRepository.save(entry);
    }

    public void deleteEntryById(Integer id) {
        entryRepository.deleteById(id);
    }

    public Integer getTotalPages() {
        return (int)Math.floor((entryRepository.count() - 1) / pageSize) + 1;
    }

    public List<Entry> getPage(Integer page) {
        return query("from Entry order by entry_id desc", pageSize, page);
    }

    public Entry preparePreview(Entry entry) {
        entry.setBody(markdownToHtml(entry.getBody()));
        return entry;
    }

    public List<Entry> searchEntries(String query, Integer page) {
        return query("from Entry e where e.body like ?1 order by entry_id desc", pageSize, page, "%" + query + "%");
    }

    private List<Entry> query(String query, Integer items, Integer page, Object... parameters) {
        Session session = sessionFactory.openSession();
        List<Entry> entries;
        try {
            Query<Entry> entryQuery = session.createQuery(query, Entry.class);
            entryQuery.setFirstResult((page - 1) * items);
            entryQuery.setMaxResults(items);
            Integer index = 1;
            for(Object parameter : parameters) {
                entryQuery.setParameter(index++, parameter);
            }
            entries = entryQuery.getResultList();
            for(Entry entry : entries) {
                entry.setBody(markdownToHtml(entry.getBody()));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }

        return entries;
    }

    private String markdownToHtml(String text) {
        Node document = parser.parse(text);
        return htmlRenderer.render(document);
    }

}
