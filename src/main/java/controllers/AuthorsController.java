package controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dto.author.AuthorResponseDTO;
import dto.author.CreateAuthorRequestDTO;
import dto.author.CreateAuthorResponseDTO;
import dto.author.UpdateAuthorNameDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import services.AuthorService;
import utils.JsonDisplay;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    private final AuthorService authorService;

    @Autowired
    public AuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    @JsonView(JsonDisplay.Default.class)
    public List<AuthorResponseDTO> getAll() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    @JsonView(JsonDisplay.Default.class)
    public AuthorResponseDTO getById(@PathVariable("id")
                                     @Digits(integer = 19, fraction = 0, message = "Incorrect author 'id' parameter")
                                     @Positive(message = "Author 'id' must be a positive number greater than zero")
                                     Long id) {

        return authorService.findById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CreateAuthorResponseDTO create(@Valid CreateAuthorRequestDTO createAuthorRequestDTO) {
        return authorService.create(createAuthorRequestDTO);
    }

    @PatchMapping
    public UpdateAuthorNameDTO updateName(@Valid UpdateAuthorNameDTO updateAuthorNameDTO) {
        return authorService.updateName(updateAuthorNameDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id")
                       @Digits(integer = 19, fraction = 0, message = "Incorrect author 'id' parameter")
                       @Positive(message = "Author 'id' must be a positive number greater than zero")
                       Long id) {

        authorService.delete(id);
    }
}
