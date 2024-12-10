package services;

import dto.review.BookReviewDTO;
import dto.review.CreateReviewDTO;
import dto.review.ReviewResponseDTO;
import mappers.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    @Transactional
    public ReviewResponseDTO create(CreateReviewDTO createReviewDTO) {
        var review = reviewMapper.toReview(createReviewDTO);

        reviewRepository.create(review);

        return reviewMapper.toResponseDTO(review);
    }

    @Transactional(readOnly = true)
    public List<BookReviewDTO> findAll(Long bookId) {
        var reviews = reviewRepository.findAll(bookId);

        if (reviews.isEmpty()) {
            throw new RuntimeException("No reviews found");
        }

        return reviewMapper.toBookReviewDTOList(reviews);
    }
}
