package entities;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import http.json.JsonDisplay;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @JsonView(JsonDisplay.AuthorWithBooks.class)
    @ManyToMany
    @JoinTable(name = "authors_books",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books;

    private Author(Author.AuthorBuilder authorBuilder) {
        id = authorBuilder.id;
        firstName = authorBuilder.firstName;
        lastName = authorBuilder.lastName;
        books = authorBuilder.books;
    }

    protected Author() {
    }

    public Author(Long id, String firstName, String lastName, Set<Book> books) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
        book.getAuthors().add(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.getAuthors().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class AuthorBuilder {
        private Long id;

        private String firstName;
        private String lastName;

        private Set<Book> books;

        public AuthorBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Author.AuthorBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public Author.AuthorBuilder setBooks(Set<Book> books) {
            this.books = books;
            return this;
        }

        public Author build() {
            return new Author(this);
        }
    }
}
