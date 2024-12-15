package http.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dto.review.BookReviewDTO;
import dto.review.CreateReviewDTO;
import dto.review.ReviewResponseDTO;
import http.json.JsonDisplay;
import http.validation.annotations.ValidId;
import http.validation.annotations.ValidReviewContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import services.ReviewService;

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
    public List<BookReviewDTO> getAll(@PathVariable("id") @ValidId Long id) {
        return reviewService.findAll(id);
    }

    @JsonView(JsonDisplay.BookWithAuthors.class)
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ReviewResponseDTO create(@PathVariable("id") @ValidId Long bookId,
                                    @RequestParam("readerId") @ValidId Long readerId,
                                    @RequestParam("content") @ValidReviewContent(parameterName = "content") String content) {

        var createReviewDTO = new CreateReviewDTO(readerId, bookId, content);

        return reviewService.create(createReviewDTO);
    }
}
