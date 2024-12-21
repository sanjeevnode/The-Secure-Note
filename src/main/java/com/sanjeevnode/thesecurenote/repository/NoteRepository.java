package com.sanjeevnode.thesecurenote.repository;

import com.sanjeevnode.thesecurenote.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository  extends JpaRepository<Note,Long> {
}
