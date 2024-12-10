package services;

import dto.reader.CreateReaderRequestDTO;
import dto.reader.CreateReaderResponseDTO;
import dto.reader.ReaderResponseDTO;
import dto.reader.UpdateReaderPhoneDTO;
import mappers.ReaderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.ReaderRepository;

import java.util.List;

@Service
public class ReaderService {

    private final ReaderRepository readerRepository;

    private final ReaderMapper readerMapper;

    public ReaderService(ReaderRepository readerRepository, ReaderMapper readerMapper) {
        this.readerRepository = readerRepository;
        this.readerMapper = readerMapper;
    }

    @Transactional
    public CreateReaderResponseDTO create(CreateReaderRequestDTO createReaderRequestDTO) {
        var reader = readerMapper.toReader(createReaderRequestDTO);

        readerRepository.create(reader);

        return readerMapper.toCreateResponseDTO(reader);
    }

    @Transactional(readOnly = true)
    public ReaderResponseDTO findById(Long id) {
        var optionalReader = readerRepository.findById(id);

        return optionalReader
                .map(readerMapper::toResponseDTO)
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<ReaderResponseDTO> findAll() {
        var readers = readerRepository.findAll();

        if (readers.isEmpty()) {
            throw new RuntimeException("No readers found");
        }

        return readerMapper.toResponseDTOList(readers);
    }

    @Transactional
    public UpdateReaderPhoneDTO updatePhone(UpdateReaderPhoneDTO updateReaderPhoneDTO) {
        var optionalReader = readerRepository.findById(updateReaderPhoneDTO.getId());

        var phone = updateReaderPhoneDTO.getPhone();

        optionalReader.ifPresent(reader -> reader.setPhone(phone));

        return updateReaderPhoneDTO;
    }

    @Transactional
    public void delete(Long id) {
        var optionalReader = readerRepository.findById(id);

        optionalReader.ifPresent(readerRepository::delete);
    }
}
