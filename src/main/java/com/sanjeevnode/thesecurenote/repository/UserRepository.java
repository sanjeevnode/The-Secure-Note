package com.sanjeevnode.thesecurenote.repository;

import com.sanjeevnode.thesecurenote.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
