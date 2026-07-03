package uesc.web.lectio.exception;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(String message) { 
        super(message); 
    }
}