package services;

import dto.book.BookResponseDTO;
import dto.book.CreateBookDTO;
import entities.Author;
import mappers.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.AuthorRepository;
import repositories.BookRepository;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public BookResponseDTO create(CreateBookDTO createBookDTO) {
        var book = bookMapper.toBook(createBookDTO);

        var bookAuthors = findAuthors(createBookDTO.getAuthorsId());

        book.setAuthors(bookAuthors);

        bookRepository.create(book);

        bookAuthors.forEach(author -> author.addBook(book));

        return bookMapper.toResponseDTO(book);
    }

    private Set<Author> findAuthors(Set<Long> authorsId) {
        var authors = new HashSet<Author>();

        for (var id : authorsId) {
            var author = authorRepository
                    .findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Author not found"));

            authors.add(author);
        }

        return authors;
    }

    @Transactional(readOnly = true)
    public BookResponseDTO findById(Long id) {
        var book = bookRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));

        return bookMapper.toResponseDTO(book);
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> findAll() {
        var books = bookRepository.findAll();

        if (books.isEmpty()) {
            throw new NoSuchElementException("Books not found");
        }

        return bookMapper.toResponseDTOList(books);
    }

    @Transactional
    public void delete(Long id) {
        var book = bookRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));

        bookRepository.delete(book);
    }
}
