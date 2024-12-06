package com.sanjeevnode.thesecurenote.repository;

import com.sanjeevnode.thesecurenote.entity.Metadata;
import com.sanjeevnode.thesecurenote.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MetadataRepository extends JpaRepository<Metadata,Long> {
    Optional<Metadata> findByUser(User user);
}
