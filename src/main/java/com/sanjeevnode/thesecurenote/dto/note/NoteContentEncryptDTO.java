package com.sanjeevnode.thesecurenote.dto.note;

public record NoteContentEncryptDTO(String content, String masterPin, String saltString) {
}
