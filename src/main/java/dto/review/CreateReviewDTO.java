package dto.review;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CreateReviewDTO {

    @NotBlank(message = "Missing reader 'id' parameter")
    @Digits(integer = 19, fraction = 0, message = "Incorrect reader 'id' parameter")
    @Positive(message = "Reader 'id' must be a positive number greater than zero")
    private final Long readerId;

    @NotBlank(message = "Missing book 'id' parameter")
    @Digits(integer = 19, fraction = 0, message = "Incorrect book 'id' parameter")
    @Positive(message = "Book 'id' must be a positive number greater than zero")
    private final Long bookId;

    @NotBlank(message = "Missing review 'content' parameter")
    @Size(max = 255, message = "Review 'content' parameter must be no more than 500 characters")
    private final String content;

    private final LocalDate date;

    public CreateReviewDTO(Long readerId, Long bookId, String content) {
        this.readerId = readerId;
        this.bookId = bookId;
        this.content = content;
        this.date = LocalDate.now();
    }

    public Long getReaderId() {
        return readerId;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getDate() {
        return date;
    }
}
