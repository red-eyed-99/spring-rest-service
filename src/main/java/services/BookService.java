package services;

import dto.book.BookResponseDTO;
import dto.book.CreateBookDTO;
import mappers.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.BookRepository;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public BookResponseDTO create(CreateBookDTO createBookDTO) {
        var book = bookMapper.toBook(createBookDTO);

        bookRepository.create(book);

        return bookMapper.toResponseDTO(book);
    }

    @Transactional(readOnly = true)
    public BookResponseDTO findById(Long id) {
        var optionalBook = bookRepository.findById(id);

        return optionalBook
                .map(bookMapper::toResponseDTO)
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<BookResponseDTO> findAll() {
        var books = bookRepository.findAll();

        return bookMapper.toResponseDTOList(books);
    }

    @Transactional
    public void delete(Long id) {
        var optionalBook = bookRepository.findById(id);

        optionalBook.ifPresent(bookRepository::delete);
    }
}
