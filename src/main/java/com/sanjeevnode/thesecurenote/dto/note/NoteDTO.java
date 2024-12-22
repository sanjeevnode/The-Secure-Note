package com.sanjeevnode.thesecurenote.dto.note;

import com.sanjeevnode.thesecurenote.entity.Note;
import com.sanjeevnode.thesecurenote.utils.CryptoUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteDTO {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String createdAt;
    private String updatedAt;

    public static NoteDTO fromEntity(Note note,String metaKey) throws Exception {
        NoteContentEncryptDTO  n = new NoteContentEncryptDTO(note.getContent(), metaKey, note.getUser().getUsername());
        return NoteDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(CryptoUtils.decrypt(n))
                .userId(note.getUser().getId())
                .createdAt(note.getCreatedAt().toString())
                .updatedAt(note.getUpdatedAt().toString())
                .build();
    }

    public static List<NoteDTO> fromEntity(List<Note> notes,String metaKey){
        return notes.stream().map(note -> {
            try {
                return fromEntity(note,metaKey);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }
}
