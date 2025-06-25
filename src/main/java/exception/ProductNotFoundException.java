package exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long message) {
        super(String.valueOf(message));
    }
}
