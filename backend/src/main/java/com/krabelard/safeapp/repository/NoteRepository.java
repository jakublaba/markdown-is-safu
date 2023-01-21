package com.krabelard.safeapp.repository;

import com.krabelard.safeapp.model.Note;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
@Transactional
public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<Note> findByUuidAndOwnerUsername(UUID uuid, String username);

    List<Note> findAllByOwnerUsername(String username);

    boolean existsByNameAndOwnerUsername(String name, String username);

}
