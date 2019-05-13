package com.friday.colini.attach.utils;

class Digit {
    private final byte MIN_DIGIT = 0;
    private final byte MAX_DIGIT = 9;

    private byte digit;

    //
    //
    //

    Digit(final byte digit) {
        if (MIN_DIGIT > digit || digit > MAX_DIGIT) {
            throw new IllegalArgumentException();
        }

        this.digit = digit;
    }

    //
    //
    //

    @Override
    public String toString() {
        return String.valueOf(digit);
    }
}
