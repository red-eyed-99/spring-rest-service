package dto.reader;

import entities.Book;

import java.util.Set;

public class ReaderResponseDTO {

    private final Long id;

    private final String firstName;
    private final String lastName;

    private final String phone;

    private final Set<Book> books;

    public ReaderResponseDTO(Long id, String firstName, String lastName, String phone, Set<Book> books) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public Set<Book> getBooks() {
        return books;
    }
}
