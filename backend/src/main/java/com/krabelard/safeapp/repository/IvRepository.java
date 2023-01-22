package com.krabelard.safeapp.repository;

import com.krabelard.safeapp.model.IV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IvRepository extends JpaRepository<IV, Long> {

    Optional<IV> findByNoteUuid(UUID uuid);

}
