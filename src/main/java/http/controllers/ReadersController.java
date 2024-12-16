package http.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dto.reader.CreateReaderRequestDTO;
import dto.reader.CreateReaderResponseDTO;
import dto.reader.ReaderResponseDTO;
import dto.reader.UpdateReaderPhoneDTO;
import http.json.JsonDisplay;
import http.validation.annotations.ValidId;
import http.validation.annotations.ValidPhone;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import services.ReaderService;

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
    public ReaderResponseDTO getById(@PathVariable("id") @ValidId Long id) {
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
    public ReaderResponseDTO addBook(@PathVariable("readerId") @ValidId Long readerId,
                                     @PathVariable("bookId") @ValidId Long bookId) {

        return readerService.addBook(readerId, bookId);
    }

    @PatchMapping("/{id}")
    public UpdateReaderPhoneDTO updatePhone(@PathVariable("id") @ValidId Long id,
                                            @RequestParam("phone") @ValidPhone String phone) {

        var updateReaderPhoneDTO = new UpdateReaderPhoneDTO(id, phone);

        try {
            readerService.updatePhone(updateReaderPhoneDTO);
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException("This phone is already taken");
        }

        return updateReaderPhoneDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") @ValidId Long id) {
        readerService.delete(id);
    }
}
