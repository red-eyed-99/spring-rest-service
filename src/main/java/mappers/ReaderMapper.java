package mappers;

import dto.reader.CreateReaderRequestDTO;
import dto.reader.CreateReaderResponseDTO;
import dto.reader.ReaderResponseDTO;
import dto.reader.UpdateReaderPhoneDTO;
import entities.Reader;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReaderMapper {

    ReaderResponseDTO toResponseDTO(Reader reader);

    CreateReaderRequestDTO toCreateRequestDTO(Reader reader);

    CreateReaderResponseDTO toCreateResponseDTO(Reader reader);

    UpdateReaderPhoneDTO toUpdatePhoneDTO(Reader reader);

    List<ReaderResponseDTO> toResponseDTOList(List<Reader> readers);

    Reader toReader(CreateReaderRequestDTO createReaderRequestDTO);
}
