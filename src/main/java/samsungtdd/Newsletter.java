package samsungtdd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Newsletter {
    private final List<String> subscribers =  new ArrayList<>(
            Arrays.asList("cucumber@gmail.com", "khoascript@gmail.com", "cachuacute@gmail.com"));

    public boolean subscribe(String email) {
        if (!isValidEmail(email)) {
            throw new InvalidEmailFormatException("Invalid email format.");
        }
        if (subscribers.contains(email)) {
            throw new EmailAlreadySubscribedException("Email already subscribed.");
        }

        subscribers.add(email);
        return true;
    }

    public List<String> getSubscribers() {
        return subscribers;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9].[a-zA-Z0-9._%+\\-]{0,63}@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,30}$";
        return Pattern.matches(emailRegex, email);
    }
}

// Custom Exception 1: Invalid email format
class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException(String message) {
        super(message);
    }
}

// Custom Exception 2: Duplicate email
class EmailAlreadySubscribedException extends RuntimeException {
    public EmailAlreadySubscribedException(String message) {
        super(message);
    }
}

