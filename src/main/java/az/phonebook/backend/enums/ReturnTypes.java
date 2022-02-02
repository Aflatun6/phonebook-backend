package az.phonebook.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReturnTypes {
    OK(HttpStatus.OK),
    NO_CONTENT(HttpStatus.NOT_FOUND);

    private final HttpStatus status;
}
