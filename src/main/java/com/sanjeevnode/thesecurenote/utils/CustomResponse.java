package com.sanjeevnode.thesecurenote.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class CustomResponse {
    private HttpStatus status;
    private String message;
    private Object data;
}
