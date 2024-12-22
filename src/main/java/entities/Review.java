package entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

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

    private Review(Review.ReviewBuilder reviewBuilder) {
        id = reviewBuilder.id;
        reader = reviewBuilder.reader;
        book = reviewBuilder.book;
        content = reviewBuilder.content;
        date = reviewBuilder.date;
    }

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

    public void setId(Long id) {
        this.id = id;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class ReviewBuilder {
        private Long id;

        private Reader reader;
        private Book book;

        private String content;

        private LocalDate date;

        public ReviewBuilder(Reader reader, String content) {
            this.reader = reader;
            this.content = content;
        }

        public ReviewBuilder(Reader reader, Book book, String content) {
            this.reader = reader;
            this.book = book;
            this.content = content;
        }

        public Review.ReviewBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public Review.ReviewBuilder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }
}
