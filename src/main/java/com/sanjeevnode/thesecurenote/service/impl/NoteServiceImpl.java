package com.sanjeevnode.thesecurenote.service.impl;

import com.sanjeevnode.thesecurenote.dto.note.AddNoteRequest;
import com.sanjeevnode.thesecurenote.dto.note.NoteContentEncryptDTO;
import com.sanjeevnode.thesecurenote.entity.Note;
import com.sanjeevnode.thesecurenote.entity.User;
import com.sanjeevnode.thesecurenote.repository.NoteRepository;
import com.sanjeevnode.thesecurenote.repository.UserRepository;
import com.sanjeevnode.thesecurenote.service.NoteService;
import com.sanjeevnode.thesecurenote.utils.CryptoUtils;
import com.sanjeevnode.thesecurenote.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    final UserServiceImpl userService;
    final NoteRepository noteRepository;
    final UserRepository userRepository;

    @Override
    public CustomResponse addNote(AddNoteRequest addNoteRequest) {
        try {
            User user = userRepository.findById(addNoteRequest.getUserId()).orElseThrow(
                    () -> new Exception("User not found with id " + addNoteRequest.getUserId())
            );
            if(!userService.verifyMasterPin(user, addNoteRequest.getMetaKey())) {
                return CustomResponse.builder().status(HttpStatus.BAD_REQUEST).message("Invalid Master Pin").build();
            }
            Note note = new Note();
            NoteContentEncryptDTO n = new NoteContentEncryptDTO(
                    addNoteRequest.getContent(),
                    addNoteRequest.getMetaKey(),
                    user.getUsername()
            );
            note.setUser(user);
            note.setTitle(addNoteRequest.getTitle());
            note.setContent(CryptoUtils.encrypt(n));
            noteRepository.save(note);
            return CustomResponse.builder().status(HttpStatus.OK).message("Note added successfully").build();
        } catch (Exception e) {
            return CustomResponse.builder().status(HttpStatus.BAD_REQUEST).message(e.getMessage()).build();
        }
    }
}
