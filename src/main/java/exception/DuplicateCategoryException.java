package exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DuplicateCategoryException extends RuntimeException {
    public DuplicateCategoryException() {
        super("Category already exists.");
    }
}
