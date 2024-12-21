package com.sanjeevnode.thesecurenote.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomResponse {
    private HttpStatus status;
    private String message;
    private Object body;
}
