package services;

import dto.review.BookReviewDTO;
import dto.review.CreateReviewDTO;
import dto.review.ReviewResponseDTO;
import mappers.ReviewMapper;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.BookRepository;
import repositories.ReaderRepository;
import repositories.ReviewRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository,
                         ReaderRepository readerRepository, ReviewMapper reviewMapper) {

        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;

        this.reviewMapper = reviewMapper;
    }

    @Transactional
    public ReviewResponseDTO create(CreateReviewDTO createReviewDTO) {
        var book = bookRepository
                .findById(createReviewDTO.getBookId())
                .orElseThrow(() -> new NoSuchElementException("Book not found"));

        Hibernate.initialize(book.getAuthors());

        var reader = readerRepository
                .findById(createReviewDTO.getReaderId())
                .orElseThrow(() -> new NoSuchElementException("Reader not found"));

        var review = reviewMapper.toReview(createReviewDTO);
        review.setBook(book);
        review.setReader(reader);

        reviewRepository.create(review);

        return reviewMapper.toResponseDTO(review);
    }

    @Transactional(readOnly = true)
    public List<BookReviewDTO> findAll(Long bookId) {
        var reviews = reviewRepository.findAll(bookId);

        if (reviews.isEmpty()) {
            throw new NoSuchElementException("No reviews found");
        }

        return reviewMapper.toBookReviewDTOList(reviews);
    }
}
