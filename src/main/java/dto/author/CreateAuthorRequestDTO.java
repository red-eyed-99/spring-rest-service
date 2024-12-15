package dto.author;

import http.validation.annotations.ValidName;

import java.beans.ConstructorProperties;

public class CreateAuthorRequestDTO {

    public static final String FIRST_NAME_PARAMETER = "first-name";
    public static final String LAST_NAME_PARAMETER = "last-name";

    @ValidName(parameterName = FIRST_NAME_PARAMETER)
    private final String firstName;

    @ValidName(parameterName = LAST_NAME_PARAMETER)
    private final String lastName;

    @ConstructorProperties({FIRST_NAME_PARAMETER, LAST_NAME_PARAMETER})
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
