package dto.review;

import entities.Reader;

import java.time.LocalDate;

public class BookReviewDTO {

    private final Long id;

    private final Reader reader;

    private final String content;

    private final LocalDate date;

    public BookReviewDTO(Long id, Reader reader, String content, LocalDate date) {
        this.id = id;
        this.reader = reader;
        this.content = content;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Reader getReader() {
        return reader;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getDate() {
        return date;
    }
}
