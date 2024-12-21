package com.sanjeevnode.thesecurenote.controller;

import com.sanjeevnode.thesecurenote.dto.note.AddNoteRequest;
import com.sanjeevnode.thesecurenote.service.NoteService;
import com.sanjeevnode.thesecurenote.utils.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {
    final private NoteService noteService;

    @PostMapping("/add")
    public ResponseEntity<CustomResponse> addNote(@Valid @RequestBody AddNoteRequest addNoteRequest){
        CustomResponse response = noteService.addNote(addNoteRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
