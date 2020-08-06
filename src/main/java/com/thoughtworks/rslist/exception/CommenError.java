package com.thoughtworks.rslist.exception;

import lombok.Data;

@Data
public class CommenError {
    private String error;

    public void setError(String error) {
        this.error=error;
    }
}
