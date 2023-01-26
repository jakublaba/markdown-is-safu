package com.krabelard.safeapp.service;


import com.krabelard.safeapp.dto.FileDTO;
import com.krabelard.safeapp.dto.NoteDTO;
import com.krabelard.safeapp.exception.note.NoteConflictException;
import com.krabelard.safeapp.exception.note.NoteIOException;
import com.krabelard.safeapp.exception.note.NoteNotFoundException;
import com.krabelard.safeapp.exception.user.UserNotFoundException;
import com.krabelard.safeapp.mapper.NoteMapper;
import com.krabelard.safeapp.model.Note;
import com.krabelard.safeapp.repository.NoteRepository;
import com.krabelard.safeapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final AesService aes;
    private final NoteMapper noteMapper;

    public UUID create(MultipartFile file) {
        val fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (noteRepository.existsByNameAndOwnerUsername(fileName, currentUser())) {
            throw new NoteConflictException(fileName);
        }

        try {
            val raw = file.getBytes();
            val owner = userRepository.findByUsername(currentUser())
                    .orElseThrow(() -> new UserNotFoundException(currentUser()));
            val password = owner.getPassword();
            val encParams = aes.encrypt(raw, password);
            val note = Note.builder()
                    .uuid(UUID.randomUUID())
                    .name(fileName)
                    .owner(owner)
                    .content(encParams.encryptedFile())
                    .salt(encParams.salt())
                    .iv(encParams.iv())
                    .build();

            return noteRepository.save(note).getUuid();
        } catch (IOException e) {
            throw new NoteIOException(file.getOriginalFilename());
        }
    }

    public List<NoteDTO> fetchNoteList() {
        return noteRepository.findAllByOwnerUsername(currentUser())
                .stream()
                .map(noteMapper::entityToDto)
                .toList();
    }

    public FileDTO downloadNote(UUID uuid) {
        val note = noteRepository.findByUuidAndOwnerUsername(uuid, currentUser())
                .orElseThrow(() -> new NoteNotFoundException(uuid));
        val owner = userRepository.findByUsername(currentUser())
                .orElseThrow(() -> new UserNotFoundException(currentUser()));

        val decrypted = aes.decrypt(note.getContent(), owner.getPassword(), note.getSalt(), note.getIv());

        return FileDTO.builder()
                .fileName(note.getName())
                .content(decrypted)
                .build();
    }

    public NoteDTO update(UUID uuid, MultipartFile file) {
        val note = noteRepository.findByUuidAndOwnerUsername(uuid, currentUser())
                .orElseThrow(() -> new NoteNotFoundException(uuid));
        val owner = userRepository.findByUsername(currentUser())
                .orElseThrow(() -> new UserNotFoundException(currentUser()));
        val fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        note.setName(fileName);
        try {
            val raw = file.getBytes();
            val encrypted = aes.encrypt(raw, owner.getPassword(), note.getSalt(), note.getIv());
            note.setContent(encrypted);
        } catch (IOException e) {
            throw new NoteIOException(fileName);
        }

        return noteMapper.entityToDto(noteRepository.save(note));
    }

    private String currentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
