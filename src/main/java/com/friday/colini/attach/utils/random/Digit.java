package com.friday.colini.attach.utils.random;

class Digit {
    static final int MIN_DIGIT = 0;
    static final int MAX_DIGIT = 9;

    private int digit;

    //
    //
    //

    Digit(final int digit) {
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
