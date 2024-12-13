package config;

import mappers.AuthorMapper;
import mappers.BookMapper;
import mappers.ReaderMapper;
import mappers.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import repositories.AuthorRepository;
import repositories.BookRepository;
import repositories.ReaderRepository;
import repositories.ReviewRepository;
import services.AuthorService;
import services.BookService;
import services.ReaderService;
import services.ReviewService;

@Configuration
@ComponentScan({"services", "mappers"})
public class ServiceConfig {

    private final ApplicationContext applicationContext;

    @Autowired
    public ServiceConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public AuthorService authorService() {
        return new AuthorService(
                applicationContext.getBean(AuthorRepository.class),
                applicationContext.getBean(AuthorMapper.class));
    }

    @Bean
    public BookService bookService() {
        return new BookService(
                applicationContext.getBean(BookRepository.class),
                applicationContext.getBean(AuthorRepository.class),
                applicationContext.getBean(BookMapper.class));
    }

    @Bean
    public ReaderService readerService() {
        return new ReaderService(
                applicationContext.getBean(ReaderRepository.class),
                applicationContext.getBean(BookRepository.class),
                applicationContext.getBean(ReaderMapper.class));
    }

    @Bean
    public ReviewService reviewService() {
        return new ReviewService(
                applicationContext.getBean(ReviewRepository.class),
                applicationContext.getBean(BookRepository.class),
                applicationContext.getBean(ReaderRepository.class),
                applicationContext.getBean(ReviewMapper.class));
    }
}