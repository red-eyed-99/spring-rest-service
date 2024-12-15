package services;

import dto.author.AuthorResponseDTO;
import dto.author.CreateAuthorRequestDTO;
import dto.author.CreateAuthorResponseDTO;
import dto.author.UpdateAuthorNameDTO;
import mappers.AuthorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.AuthorRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Transactional
    public CreateAuthorResponseDTO create(CreateAuthorRequestDTO createAuthorRequestDTO) {
        var author = authorMapper.toAuthor(createAuthorRequestDTO);

        authorRepository.create(author);

        return authorMapper.toCreateResponseDTO(author);
    }

    @Transactional(readOnly = true)
    public AuthorResponseDTO findById(Long id) {
        var author = authorRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Author not found"));

        return authorMapper.toResponseDTO(author);
    }

    @Transactional(readOnly = true)
    public List<AuthorResponseDTO> findAll() {
        var authors = authorRepository.findAll();

        if (authors.isEmpty()) {
            throw new NoSuchElementException("Authors not found");
        }

        return authorMapper.toResponseDTOList(authors);
    }

    @Transactional
    public UpdateAuthorNameDTO updateName(UpdateAuthorNameDTO updateAuthorNameDTO) {
        var author = authorRepository
                .findById(updateAuthorNameDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("Author not found"));

        var authorFirstName = updateAuthorNameDTO.getFirstName();
        var authorLastName = updateAuthorNameDTO.getLastName();

        author.setFirstName(authorFirstName);
        author.setLastName(authorLastName);

        return updateAuthorNameDTO;
    }

    @Transactional
    public void delete(Long id) {
        var author = authorRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Author not found"));

        authorRepository.delete(author);
    }
}
