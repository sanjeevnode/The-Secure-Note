package com.sanjeevnode.thesecurenote.controller;

import com.sanjeevnode.thesecurenote.dto.note.AddNoteRequest;
import com.sanjeevnode.thesecurenote.dto.note.NoteGetRequest;
import com.sanjeevnode.thesecurenote.repository.NoteRepository;
import com.sanjeevnode.thesecurenote.service.NoteService;
import com.sanjeevnode.thesecurenote.utils.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {
    final private NoteService noteService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping()
    public ResponseEntity<CustomResponse> getNotes(@Valid @RequestBody NoteGetRequest noteGetRequest){
        CustomResponse response = noteService.getNotes(noteGetRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/add")
    public ResponseEntity<CustomResponse> addNote(@Valid @RequestBody AddNoteRequest addNoteRequest){
        CustomResponse response = noteService.addNote(addNoteRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
