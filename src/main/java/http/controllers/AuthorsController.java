package http.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import dto.author.AuthorResponseDTO;
import dto.author.CreateAuthorRequestDTO;
import dto.author.CreateAuthorResponseDTO;
import dto.author.UpdateAuthorNameDTO;
import http.json.JsonDisplay;
import http.validation.annotations.ValidId;
import http.validation.annotations.ValidName;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import services.AuthorService;

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
    public AuthorResponseDTO getById(@PathVariable("id") @ValidId Long id) {
        return authorService.findById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CreateAuthorResponseDTO create(@Valid CreateAuthorRequestDTO createAuthorRequestDTO) {
        return authorService.create(createAuthorRequestDTO);
    }

    @PatchMapping("/{id}")
    public UpdateAuthorNameDTO updateName(@PathVariable("id") @ValidId Long id,
                                          @RequestParam("first-name") @ValidName(parameterName = "first-name") String firstName,
                                          @RequestParam("last-name") @ValidName(parameterName = "last-name") String lastName) {

        var updateAuthorNameDTO = new UpdateAuthorNameDTO(id, firstName, lastName);

        return authorService.updateName(updateAuthorNameDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") @ValidId Long id) {
        authorService.delete(id);
    }
}
