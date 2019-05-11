package com.friday.colini.attach.utils;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
class Digits {
    @Getter(AccessLevel.PACKAGE)
    private final String value = "0123456789";
}
