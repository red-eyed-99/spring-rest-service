package controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dto.reader.CreateReaderRequestDTO;
import dto.reader.CreateReaderResponseDTO;
import dto.reader.ReaderResponseDTO;
import dto.reader.UpdateReaderPhoneDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import services.ReaderService;
import utils.JsonDisplay;

import java.util.List;

@RestController
@RequestMapping("/readers")
public class ReadersController {

    private final ReaderService readerService;

    @Autowired
    public ReadersController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping
    @JsonView(JsonDisplay.BookWithAuthors.class)
    public List<ReaderResponseDTO> getAll() {
        return readerService.findAll();
    }

    @GetMapping("/{id}")
    @JsonView(JsonDisplay.BookWithAuthors.class)
    public ReaderResponseDTO getById(@PathVariable("id")
                                     @Digits(integer = 19, fraction = 0, message = "Incorrect reader 'id' parameter")
                                     @Positive(message = "Reader 'id' must be a positive number greater than zero")
                                     Long id) {

        return readerService.findById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CreateReaderResponseDTO create(@Valid CreateReaderRequestDTO createReaderRequestDTO) {
        return readerService.create(createReaderRequestDTO);
    }

    @PostMapping("/{readerId}/books/{bookId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    @JsonView(JsonDisplay.BookWithAuthors.class)
    public ReaderResponseDTO addBook(@PathVariable("readerId")
                                     @Digits(integer = 19, fraction = 0, message = "Incorrect reader 'id' parameter")
                                     @Positive(message = "Reader 'id' must be a positive number greater than zero")
                                     Long readerId,
                                     @PathVariable("bookId")
                                     @Digits(integer = 19, fraction = 0, message = "Incorrect book 'id' parameter")
                                     @Positive(message = "Book 'id' must be a positive number greater than zero")
                                     Long bookId) {

        return readerService.addBook(readerId, bookId);
    }

    @PatchMapping("/{id}")
    public UpdateReaderPhoneDTO updatePhone(@PathVariable("id")
                                            @Digits(integer = 19, fraction = 0, message = "Incorrect reader 'id' parameter")
                                            @Positive(message = "Reader 'id' must be a positive number greater than zero")
                                            Long id,
                                            @RequestParam("phone")
                                            @NotBlank(message = "Missing reader 'phone' parameter")
                                            @Pattern(regexp = "^\\+7\\(\\d{3}\\)-\\d{3}-\\d{2}-\\d{2}$",
                                                    message = "Reader 'phone' parameter must be in this format: +7(xxx)-xxx-xx-xx") String phone) {

        var updateReaderPhoneDTO = new UpdateReaderPhoneDTO(id, phone);

        return readerService.updatePhone(updateReaderPhoneDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id")
                       @Digits(integer = 19, fraction = 0, message = "Incorrect reader 'id' parameter")
                       @Positive(message = "Reader 'id' must be a positive number greater than zero")
                       Long id) {

        readerService.delete(id);
    }
}
