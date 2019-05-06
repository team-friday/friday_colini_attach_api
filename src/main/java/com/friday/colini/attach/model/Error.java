package com.friday.colini.attach.model;

import lombok.Builder;

@Builder
public class Error {
    private String code;
    private String message;

    public String getErrCode() {
        return code;
    }

    public String getErrMessage() {
        return message;
    }
}
