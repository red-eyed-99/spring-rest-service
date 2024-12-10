package config;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import repositories.AuthorRepository;
import repositories.BookRepository;
import repositories.ReaderRepository;
import repositories.ReviewRepository;

@Configuration
@ComponentScan("repositories")
public class RepositoryConfig {

    private final ApplicationContext applicationContext;

    @Autowired
    public RepositoryConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public AuthorRepository authorRepository() {
        return new AuthorRepository(applicationContext.getBean(Session.class));
    }

    @Bean
    public BookRepository bookRepository() {
        return new BookRepository(applicationContext.getBean(Session.class));
    }

    @Bean
    public ReaderRepository readerRepository() {
        return new ReaderRepository(applicationContext.getBean(Session.class));
    }

    @Bean
    public ReviewRepository reviewRepository() {
        return new ReviewRepository(applicationContext.getBean(Session.class));
    }
}
