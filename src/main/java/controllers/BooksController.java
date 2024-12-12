package controllers;

import dto.book.BookResponseDTO;
import dto.book.CreateBookDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import services.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookResponseDTO> getAll() {
        return bookService.findAll();
    }


    @GetMapping("/{id}")
    public BookResponseDTO getById(@PathVariable("id")
                                   @Digits(integer = 19, fraction = 0, message = "Incorrect book 'id' parameter")
                                   @Positive(message = "Book 'id' must be a positive number greater than zero")
                                   Long id) {

        return bookService.findById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public BookResponseDTO create(@Valid CreateBookDTO createBookDTO) {
        return bookService.create(createBookDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id")
                       @Digits(integer = 19, fraction = 0, message = "Incorrect book 'id' parameter")
                       @Positive(message = "Book 'id' must be a positive number greater than zero")
                       Long id) {

        bookService.delete(id);
    }
}
