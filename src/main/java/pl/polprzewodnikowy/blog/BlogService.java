package pl.polprzewodnikowy.blog;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polprzewodnikowy.user.UserService;

import javax.persistence.EntityManagerFactory;
import java.util.List;

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

    private Integer pageSize = 10;

    public BlogService(EntityManagerFactory entityManagerFactory) {
        sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    public List<Entry> getPage(Integer page) {
        Session session = sessionFactory.openSession();

        List<Entry> entries;

        try {
            Query<Entry> query = session.createQuery("from Entry order by entry_id desc", Entry.class);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            entries = query.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }

        return entries;
    }

    public Entry getEntryById(Integer id) {
        return entryRepository.findById(id).get();
    }

    public void addNewEntry(Entry entry) {
        entryRepository.save(entry);
    }

    public void deleteEntryById(Integer id) {
        entryRepository.deleteById(id);
    }

    public String markdownToHtml(String text) {
        Node document = parser.parse(text);
        return htmlRenderer.render(document);
    }

    public void markdownToHtml(Entry entry) {
        entry.setBody(markdownToHtml(entry.getBody()));
    }

}
