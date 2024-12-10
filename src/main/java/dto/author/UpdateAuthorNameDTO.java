package dto.author;

import jakarta.validation.constraints.*;

import java.beans.ConstructorProperties;

public class UpdateAuthorNameDTO {

    @NotNull(message = "Missing author 'id' parameter")
    @Digits(integer = 19, fraction = 0, message = "Incorrect author 'id' parameter")
    @Positive(message = "Author 'id' must be a positive number greater than zero")
    private final Long id;

    @NotBlank(message = "Missing author 'first-name' parameter")
    @Size(max = 30, message = "Author 'first-name' must be no more than 30 characters")
    private final String firstName;

    @NotBlank(message = "Missing author 'last-name' parameter")
    @Size(max = 30, message = "Author 'last-name' must be no more than 30 characters")
    private final String lastName;

    @ConstructorProperties(value = {"id", "first-name", "last-name"})
    public UpdateAuthorNameDTO(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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
}
