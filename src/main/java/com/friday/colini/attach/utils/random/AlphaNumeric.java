package com.friday.colini.attach.utils.random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
class AlphaNumeric {
    private final Random random = ThreadLocalRandom.current();
    private final String value;

    //
    //
    //

    @Autowired
    public AlphaNumeric(
            @NonNull final Alphabet alphabet,
            @NonNull final Digits digits
    ) {
        value = alphabet.getUpperCase() + alphabet.getLowerCase() + digits.getStringDigits();
    }

    //
    //
    //

    char randomChar() {
        return value.charAt(random.nextInt(length()));
    }

    private int length() {
        return value.length();
    }
}
