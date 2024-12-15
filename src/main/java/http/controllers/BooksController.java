package http.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dto.book.BookResponseDTO;
import dto.book.CreateBookDTO;
import http.json.JsonDisplay;
import http.validation.annotations.ValidId;
import jakarta.validation.Valid;
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
    @JsonView(JsonDisplay.BookWithAuthors.class)
    public List<BookResponseDTO> getAll() {
        return bookService.findAll();
    }


    @GetMapping("/{id}")
    @JsonView(JsonDisplay.BookWithAuthors.class)
    public BookResponseDTO getById(@PathVariable("id") @ValidId Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @JsonView(JsonDisplay.BookWithAuthors.class)
    public BookResponseDTO create(@Valid CreateBookDTO createBookDTO) {
        return bookService.create(createBookDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") @ValidId Long id) {
        bookService.delete(id);
    }
}
