package com.friday.colini.attach.utils.random;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
class Digits {
    @Getter(AccessLevel.PACKAGE)
    private final String stringDigits = IntStream.rangeClosed(Digit.MIN_DIGIT, Digit.MAX_DIGIT)
            .mapToObj(Digit::new)
            .map(Digit::toString)
            .collect(Collectors.joining());
}
