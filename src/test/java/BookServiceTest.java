import dto.book.CreateBookDTO;
import entities.Author;
import entities.Book;
import mappers.BookMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import repositories.AuthorRepository;
import repositories.BookRepository;
import services.BookService;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Spy
    private BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    @InjectMocks
    private BookService bookService;

    @Test
    @DisplayName("Create book")
    void shouldReturnCreateAuthorResponseDTO() {
        var author = new Author.AuthorBuilder("dummy", "dummy")
                .setId(1L)
                .setBooks(new HashSet<>())
                .build();

        var authors = Set.of(author);

        var createBookDTO = new CreateBookDTO("dummy", 1111, getAuthorsId(authors));

        doReturn(Optional.of(author))
                .when(authorRepository).findById(anyLong());

        var bookResponseDTO = bookService.create(createBookDTO);

        assertAll(
                () -> verify(bookRepository).create(any(Book.class)),
                () -> assertEquals(createBookDTO.getTitle(), bookResponseDTO.getTitle()),
                () -> assertEquals(createBookDTO.getPublishYear(), bookResponseDTO.getPublishYear()),
                () -> assertEquals(authors, bookResponseDTO.getAuthors()),
                () -> assertFalse(author.getBooks().isEmpty())
        );
    }

    private Set<Long> getAuthorsId(Set<Author> authors) {
        return authors.stream()
                .map(Author::getId)
                .collect(Collectors.toSet());
    }

    @Test
    @DisplayName("Find book by id")
    void shouldReturnBookResponseDTO() {
        var book = new Book.BookBuilder("dummy", 1111)
                .setId(1L)
                .build();

        doReturn(Optional.of(book))
                .when(bookRepository).findById(book.getId());

        var bookResponseDTO = bookService.findById(book.getId());

        assertAll(
                () -> verify(bookRepository).findById(book.getId()),
                () -> assertEquals(book.getId(), bookResponseDTO.getId())
        );
    }

    @Test
    @DisplayName("Find non-existent book by id")
    void findNonExistentBook_throwsNoSuchElementException() {
        doReturn(Optional.empty())
                .when(bookRepository).findById(anyLong());

        assertThrows(NoSuchElementException.class, () -> bookService.findById(anyLong()), "Book not found");
    }

    @Test
    @DisplayName("Find all books")
    void shouldReturnAllAuthorsResponseDTO() {
        var book = new Book.BookBuilder("dummy", 1111)
                .build();

        doReturn(List.of(book))
                .when(bookRepository).findAll();

        var bookResponseDTOList = bookService.findAll();

        assertAll(
                () -> verify(bookRepository).findAll(),
                () -> assertFalse(bookResponseDTOList.isEmpty())
        );
    }

    @Test
    @DisplayName("Delete book by id")
    void shouldInvokeAuthorRepositoryDeleteMethod() {
        var book = new Book.BookBuilder("dummy", 1111)
                .setId(1L)
                .build();

        doReturn(Optional.of(book))
                .when(bookRepository).findById(book.getId());

        bookService.delete(book.getId());

        assertAll(
                () -> verify(bookRepository).findById(book.getId()),
                () -> verify(bookRepository).delete(any(Book.class))
        );
    }
}