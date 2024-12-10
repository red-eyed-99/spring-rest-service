package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    private String content;

    private LocalDate date;

    protected Review() {}

    public Review(Long id, Reader reader, Book book, String content, LocalDate date) {
        this.id = id;
        this.reader = reader;
        this.book = book;
        this.content = content;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Reader getReader() {
        return reader;
    }

    public Book getBook() {
        return book;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getDate() {
        return date;
    }
}
