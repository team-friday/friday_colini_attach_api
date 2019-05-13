package com.friday.colini.attach.utils;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
class Digits {
    // TODO: using??
    private final Digit[] value = new Digit[10];

    @Getter(AccessLevel.PACKAGE)
    private final String stringDigits;

    //
    //
    //

    Digits() {
        for (byte i = 0; i < 10; i++) {
            value[i] = new Digit(i);
        }

        stringDigits = Arrays.stream(value).map(Digit::toString).collect(Collectors.joining());
    }
}
