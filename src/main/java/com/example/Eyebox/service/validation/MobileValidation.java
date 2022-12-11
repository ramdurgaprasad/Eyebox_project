package com.example.Eyebox.service.validation;
import com.example.Eyebox.exception.InvalidUserDataException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileValidation {
    private static int MAX_PHONE_LENGTH = 50;

    private static final String PHONE_REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";

    private Pattern pattern;

    public MobileValidation() {
        pattern = Pattern.compile(PHONE_REGEX);
    }

    public void checkPhone(Long phone) {

        // check max phone length
        if (phone.longValue() > MAX_PHONE_LENGTH) {
            throw new InvalidUserDataException(String.format("The phone is too long: max number of chars is %s",
                    MAX_PHONE_LENGTH));
        }

        Matcher matcher = pattern.matcher(String.valueOf(phone));
        if (!matcher.matches()) {
            throw new InvalidUserDataException(String.format("The phone provided %s is not formal valid", phone));
        }
    }

}

