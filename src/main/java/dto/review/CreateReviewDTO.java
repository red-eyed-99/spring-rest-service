package dto.review;

import java.time.LocalDate;

public class CreateReviewDTO {

    private final Long readerId;

    private final Long bookId;

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
