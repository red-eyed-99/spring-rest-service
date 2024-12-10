package dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;

public class CreateAuthorRequestDTO {

    @NotBlank(message = "Missing author 'first-name' parameter")
    @Size(max = 30, message = "Author 'first-name' must be no more than 30 characters")
    private final String firstName;

    @NotBlank(message = "Missing author 'last-name' parameter")
    @Size(max = 30, message = "Author 'last-name' must be no more than 30 characters")
    private final String lastName;

    @ConstructorProperties({"first-name", "last-name"})
    public CreateAuthorRequestDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
