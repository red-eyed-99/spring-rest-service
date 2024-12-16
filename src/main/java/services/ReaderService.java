package services;

import dto.reader.CreateReaderRequestDTO;
import dto.reader.CreateReaderResponseDTO;
import dto.reader.ReaderResponseDTO;
import dto.reader.UpdateReaderPhoneDTO;
import entities.Book;
import exceptions.AlreadyExistException;
import mappers.ReaderMapper;
import org.hibernate.Hibernate;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.BookRepository;
import repositories.ReaderRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class ReaderService {

    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;

    private final ReaderMapper readerMapper;

    public ReaderService(ReaderRepository readerRepository, BookRepository bookRepository, ReaderMapper readerMapper) {
        this.readerRepository = readerRepository;
        this.bookRepository = bookRepository;

        this.readerMapper = readerMapper;
    }

    @Transactional
    public CreateReaderResponseDTO create(CreateReaderRequestDTO createReaderRequestDTO) {
        var reader = readerMapper.toReader(createReaderRequestDTO);

        try {
            readerRepository.create(reader);
        } catch (ConstraintViolationException exception) {
            throw new AlreadyExistException("Reader with this phone number already exists");
        }

        return readerMapper.toCreateResponseDTO(reader);
    }

    @Transactional(readOnly = true)
    public ReaderResponseDTO findById(Long id) {
        var reader = readerRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reader not found"));

        initBooksAuthors(reader.getBooks());

        return readerMapper.toResponseDTO(reader);
    }

    @Transactional(readOnly = true)
    public List<ReaderResponseDTO> findAll() {
        var readers = readerRepository.findAll();

        if (readers.isEmpty()) {
            throw new NoSuchElementException("Readers not found");
        }

        readers.forEach(reader -> initBooksAuthors(reader.getBooks()));

        return readerMapper.toResponseDTOList(readers);
    }

    @Transactional
    public UpdateReaderPhoneDTO updatePhone(UpdateReaderPhoneDTO updateReaderPhoneDTO) {
        var reader = readerRepository
                .findById(updateReaderPhoneDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("Reader not found"));

        var phone = updateReaderPhoneDTO.getPhone();

        reader.setPhone(phone);

        return updateReaderPhoneDTO;
    }

    @Transactional
    public ReaderResponseDTO addBook(Long readerId, Long bookId) {
        var reader = readerRepository
                .findById(readerId)
                .orElseThrow(() -> new NoSuchElementException("Reader not found"));

        var book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));

        reader.addBook(book);

        initBooksAuthors(reader.getBooks());

        return readerMapper.toResponseDTO(reader);
    }

    @Transactional
    public void delete(Long id) {
        var reader = readerRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reader not found"));

        readerRepository.delete(reader);
    }

    private void initBookAuthors(Book book) {
        Hibernate.initialize(book.getAuthors());
    }

    private void initBooksAuthors(Set<Book> books) {
        books.forEach(this::initBookAuthors);
    }
}
