package com.sanjeevnode.thesecurenote.service;

import com.sanjeevnode.thesecurenote.dto.note.AddNoteRequest;
import com.sanjeevnode.thesecurenote.utils.CustomResponse;

public interface NoteService {
    CustomResponse addNote(AddNoteRequest addNoteRequest);
}
