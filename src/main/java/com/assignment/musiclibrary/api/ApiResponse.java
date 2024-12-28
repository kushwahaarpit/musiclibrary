package com.assignment.musiclibrary.api;

import lombok.Data;

@Data
public class ApiResponse {
    private int status;
    private String message;
    private Object data;
    private String error;

    public ApiResponse(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.error = null;
    }


}
