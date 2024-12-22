import dto.author.CreateAuthorRequestDTO;
import dto.author.UpdateAuthorNameDTO;
import entities.Author;
import mappers.AuthorMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import repositories.AuthorRepository;
import services.AuthorService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Spy
    private AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);

    @InjectMocks
    private AuthorService authorService;

    @Test
    @DisplayName("Create author")
    void shouldReturnCreateAuthorResponseDTO() {
        var createAuthorRequestDTO = new CreateAuthorRequestDTO("dummy", "dummy");

        var createAuthorResponseDTO = authorService.create(createAuthorRequestDTO);

        assertAll(
                () -> verify(authorRepository).create(any(Author.class)),
                () -> assertEquals(createAuthorRequestDTO.getFirstName(), createAuthorResponseDTO.getFirstName()),
                () -> assertEquals(createAuthorRequestDTO.getLastName(), createAuthorResponseDTO.getLastName())
        );
    }

    @Test
    @DisplayName("Find author by id")
    void shouldReturnAuthorResponseDTO() {
        var author = new Author.AuthorBuilder("dummy", "dummy")
                .setId(1L)
                .build();

        doReturn(Optional.of(author))
                .when(authorRepository).findById(author.getId());

        var authorResponseDTO = authorService.findById(author.getId());

        assertAll(
                () -> verify(authorRepository).findById(author.getId()),
                () -> assertEquals(author.getId(), authorResponseDTO.getId())
        );
    }

    @Test
    @DisplayName("Find non-existent author by id")
    void findNonExistentAuthor_throwsNoSuchElementException() {
        doReturn(Optional.empty())
                .when(authorRepository).findById(anyLong());

        assertThrows(NoSuchElementException.class, () -> authorService.findById(anyLong()), "Author not found");
    }

    @Test
    @DisplayName("Find all authors")
    void shouldReturnAllAuthorsResponseDTO() {
        var author = new Author.AuthorBuilder("dummy", "dummy")
                .setId(1L)
                .build();

        doReturn(List.of(author))
                .when(authorRepository).findAll();

        var authorResponseDTOList = authorService.findAll();

        assertAll(
                () -> verify(authorRepository).findAll(),
                () -> assertFalse(authorResponseDTOList.isEmpty())
        );
    }

    @Test
    @DisplayName("Update author name")
    void shouldReturnUpdateAuthorNameDTO() {
        var author = new Author.AuthorBuilder("Ivan", "Ivanov")
                .setId(1L)
                .build();

        var updateAuthorNameDTO = new UpdateAuthorNameDTO(1L, "Petr", "Petrov");

        doReturn(Optional.of(author))
                .when(authorRepository).findById(author.getId());

        var expectedDTO = authorService.updateName(updateAuthorNameDTO);

        assertAll(
                () -> verify(authorRepository).findById(updateAuthorNameDTO.getId()),
                () -> assertEquals(updateAuthorNameDTO, expectedDTO),
                () -> assertEquals("Petr", expectedDTO.getFirstName()),
                () -> assertEquals("Petrov", expectedDTO.getLastName())
        );
    }

    @Test
    @DisplayName("Delete author by id")
    void shouldInvokeAuthorRepositoryDeleteMethod() {
        var author = new Author.AuthorBuilder("dummy", "dummy")
                .setId(1L)
                .build();

        doReturn(Optional.of(author))
                .when(authorRepository).findById(author.getId());

        authorService.delete(author.getId());

        assertAll(
                () -> verify(authorRepository).findById(author.getId()),
                () -> verify(authorRepository).delete(any(Author.class))
        );
    }
}