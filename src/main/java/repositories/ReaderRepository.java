package repositories;

import entities.Reader;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReaderRepository {

    private final Session session;

    @Autowired
    public ReaderRepository(Session session) {
        this.session = session;
    }

    public void create(Reader reader) {
        session.persist(reader);
    }

    public Optional<Reader> findById(Long id) {
        return Optional.ofNullable(
                session.get(Reader.class, id));
    }

    public List<Reader> findAll() {
        return session.createQuery("from Reader", Reader.class).list();
    }

    public void delete(Reader reader) {
        session.remove(reader);
    }
}
