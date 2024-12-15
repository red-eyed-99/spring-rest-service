package dto.reader;

import http.validation.annotations.ValidName;
import http.validation.annotations.ValidPhone;

import java.beans.ConstructorProperties;

public class CreateReaderRequestDTO {

    @ValidName(parameterName = "first-name")
    private final String firstName;

    @ValidName(parameterName = "last-name")
    private final String lastName;

    @ValidPhone
    private final String phone;

    @ConstructorProperties({"first-name", "last-name", "phone"})
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
