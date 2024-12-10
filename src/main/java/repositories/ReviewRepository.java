package repositories;

import entities.Review;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewRepository {

    private final Session session;

    @Autowired
    public ReviewRepository(Session session) {
        this.session = session;
    }

    public void create(Review review) {
        session.persist(review);
    }

    public List<Review> findAll(Long bookId) {
        return session.createQuery("from Review where book.id = :bookId", Review.class).list();
    }
}
