package dto.book;

import jakarta.validation.constraints.*;

import java.util.Set;

public class CreateBookDTO {

    @NotBlank(message = "Missing book 'title' parameter")
    @Size(max = 255, message = "Book 'title' parameter must be no more than 255 characters")
    private final String title;

    @NotBlank(message = "Missing book 'publish year' parameter")
    @Digits(integer = 4, fraction = 0, message = "Book 'publish year' must consist of 4 digits")
    @Positive(message = "Book 'publish year' must not be negative")
    private final int publishYear;

    @NotBlank(message = "Missing book 'authors' parameter")
    @Digits(integer = 19, fraction = 0, message = "Incorrect book 'id' parameter")
    @Positive(message = "Book 'id' must be a positive number greater than zero")
    private final Set<Long> authorsId;

    public CreateBookDTO(String title, int publishYear, Set<Long> authorsId) {
        this.title = title;
        this.publishYear = publishYear;
        this.authorsId = authorsId;
    }

    public String getTitle() {
        return title;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public Set<Long> getAuthorsId() {
        return authorsId;
    }
}
