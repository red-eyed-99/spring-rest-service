package repositories;

import entities.Author;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository {

    private final Session session;

    @Autowired
    public AuthorRepository(Session session) {
        this.session = session;
    }

    public void create(Author author) {
        session.persist(author);
    }

    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(
                session.get(Author.class, id));
    }

    public List<Author> findAll() {
        return session.createQuery("from Author", Author.class).list();
    }

    public void delete(Author author) {
        session.remove(author);
    }
}
