package controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dto.review.BookReviewDTO;
import dto.review.CreateReviewDTO;
import dto.review.ReviewResponseDTO;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import services.ReviewService;
import utils.JsonDisplay;

import java.util.List;

@RestController
@RequestMapping("/books/{id}/reviews")
public class ReviewsController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewsController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<BookReviewDTO> getAll(@PathVariable("id")
                                      @Digits(integer = 19, fraction = 0, message = "Incorrect book 'id' parameter")
                                      @Positive(message = "Book 'id' must be a positive number greater than zero")
                                      Long id) {

        return reviewService.findAll(id);
    }

    @JsonView(JsonDisplay.BookWithAuthors.class)
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ReviewResponseDTO create(@PathVariable("id")
                                    @Digits(integer = 19, fraction = 0, message = "Incorrect book 'id' parameter")
                                    @Positive(message = "Book 'id' must be a positive number greater than zero")
                                    Long bookId,
                                    @RequestParam("readerId")
                                    @NotNull(message = "Missing reader 'id' parameter")
                                    @Digits(integer = 19, fraction = 0, message = "Incorrect reader 'id' parameter")
                                    @Positive(message = "Reader 'id' must be a positive number greater than zero")
                                    Long readerId,
                                    @RequestParam("content")
                                    @NotBlank(message = "Missing review 'content' parameter")
                                    @Size(max = 255, message = "Review 'content' parameter must be no more than 500 characters")
                                    String content) {

        var createReviewDTO = new CreateReviewDTO(readerId, bookId, content);

        return reviewService.create(createReviewDTO);
    }
}
