package mappers;

import dto.book.BookResponseDTO;
import dto.book.CreateBookDTO;
import entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {

    BookResponseDTO toResponseDTO(Book book);

    List<BookResponseDTO> toResponseDTOList(List<Book> books);

    Book toBook(CreateBookDTO createBookDTO);
}
