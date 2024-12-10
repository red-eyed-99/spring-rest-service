package dto.reader;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class UpdateReaderPhoneDTO {

    @NotBlank(message = "Missing reader 'id' parameter")
    @Digits(integer = 19, fraction = 0, message = "Incorrect reader 'id' parameter")
    @Positive(message = "Reader 'id' must be a positive number greater than zero")
    private final Long id;

    @NotBlank(message = "Missing reader 'phone' parameter")
    @Pattern(regexp = "^\\+7\\(\\d{3}\\)-\\d{3}-\\d{2}-\\d{2}$",
            message = "Reader 'phone' parameter must be in this format: +7(xxx)-xxx-xx-xx")
    private final String phone;

    public UpdateReaderPhoneDTO(Long id, String phone) {
        this.id = id;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }
}
