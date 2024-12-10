package dto.reader;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateReaderRequestDTO {

    @NotBlank(message = "Missing reader 'first-name' parameter")
    @Size(max = 30, message = "Reader 'first-name' must be no more than 30 characters")
    private final String firstName;

    @NotBlank(message = "Missing reader 'first-name' parameter")
    @Size(max = 30, message = "Reader 'first-name' must be no more than 30 characters")
    private final String lastName;

    @NotBlank(message = "Missing reader 'phone' parameter")
    @Pattern(regexp = "^\\+7\\(\\d{3}\\)-\\d{3}-\\d{2}-\\d{2}$",
             message = "Reader 'phone' parameter must be in this format: +7(xxx)-xxx-xx-xx")
    private final String phone;

    public CreateReaderRequestDTO(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
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
}
