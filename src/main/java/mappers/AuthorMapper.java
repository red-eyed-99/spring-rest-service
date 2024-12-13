package mappers;

import dto.author.AuthorResponseDTO;
import dto.author.CreateAuthorRequestDTO;
import dto.author.CreateAuthorResponseDTO;
import entities.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {

    AuthorResponseDTO toResponseDTO(Author author);

    CreateAuthorResponseDTO toCreateResponseDTO(Author author);

    List<AuthorResponseDTO> toResponseDTOList(List<Author> authors);

    Author toAuthor(CreateAuthorRequestDTO createAuthorRequestDTO);
}
