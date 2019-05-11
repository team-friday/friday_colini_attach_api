package com.friday.colini.attach.utils;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter(AccessLevel.PACKAGE)
@Component
class Alphabet {
    private final String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String lowerCase = upperCase.toLowerCase();
}
