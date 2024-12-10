package repositories;

import entities.Book;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepository {

    private final Session session;

    @Autowired
    public BookRepository(Session session) {
        this.session = session;
    }

    public void create(Book book) {
        session.persist(book);
    }

    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(
                session.get(Book.class, id));
    }

    public List<Book> findAll() {
        return session.createQuery("from Book", Book.class).list();
    }

    public void delete(Book book) {
        session.remove(book);
    }
}
