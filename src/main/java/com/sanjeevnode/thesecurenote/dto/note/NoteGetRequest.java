package com.sanjeevnode.thesecurenote.dto.note;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteGetRequest {

    @NotNull(message = "UserId is mandatory")
    private Long userId;
    @NotBlank(message = "MetaKey is mandatory")
    private String metaKey;
}
