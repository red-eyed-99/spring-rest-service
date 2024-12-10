package mappers;

import dto.review.BookReviewDTO;
import dto.review.CreateReviewDTO;
import dto.review.ReviewResponseDTO;
import entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {

    ReviewResponseDTO toResponseDTO(Review review);

    CreateReviewDTO toCreateDTO(Review review);

    List<BookReviewDTO> toBookReviewDTOList(List<Review> reviews);

    Review toReview(CreateReviewDTO createReviewDTO);
}
