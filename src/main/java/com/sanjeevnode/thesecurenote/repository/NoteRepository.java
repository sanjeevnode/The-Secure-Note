package com.sanjeevnode.thesecurenote.repository;

import com.sanjeevnode.thesecurenote.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository  extends JpaRepository<Note,Long> {
    List<Note> findAllByUserId(Long userId);
}
