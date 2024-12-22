package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "readers")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String phone;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "readers_books",
            joinColumns = @JoinColumn(name = "reader_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books;

    private Reader(Reader.ReaderBuilder readerBuilder) {
        id = readerBuilder.id;
        firstName = readerBuilder.firstName;
        lastName = readerBuilder.lastName;
        phone = readerBuilder.phone;
        books = readerBuilder.books;
    }

    protected Reader() {
    }

    public Reader(Long id, String firstName, String lastName, String phone, Set<Book> books) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
        book.getReaders().add(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.getReaders().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return Objects.equals(id, reader.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class ReaderBuilder {
        private Long id;

        private String firstName;
        private String lastName;

        private String phone;

        private Set<Book> books;

        public ReaderBuilder(Long id, String phone) {
            this.id = id;
            this.phone = phone;
        }

        public ReaderBuilder(String firstName, String lastName, String phone) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phone = phone;
        }

        public Reader.ReaderBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public Reader.ReaderBuilder setBooks(Set<Book> books) {
            this.books = books;
            return this;
        }

        public Reader build() {
            return new Reader(this);
        }
    }
}
