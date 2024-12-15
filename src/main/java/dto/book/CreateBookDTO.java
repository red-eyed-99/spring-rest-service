package dto.book;

import http.validation.annotations.ValidId;
import http.validation.annotations.ValidTitle;
import http.validation.annotations.ValidYear;
import jakarta.validation.constraints.NotEmpty;

import java.beans.ConstructorProperties;
import java.util.Set;

public class CreateBookDTO {

    @ValidTitle(parameterName = "title")
    private final String title;

    @ValidYear(parameterName = "publish-year")
    private final Integer publishYear;

    @NotEmpty(message = "Missing book 'authors' parameter")
    private final Set<@ValidId Long> authorsId;

    @ConstructorProperties({"title", "publish-year", "authors"})
    public CreateBookDTO(String title, Integer publishYear, Set<Long> authorsId) {
        this.title = title;
        this.publishYear = publishYear;
        this.authorsId = authorsId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public Set<Long> getAuthorsId() {
        return authorsId;
    }
}
