package com.krabelard.safeapp.controller;


import com.krabelard.safeapp.dto.NoteDTO;
import com.krabelard.safeapp.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UUID> upload(@RequestParam("note") MultipartFile file) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(noteService.create(file));
    }

    @GetMapping(
            value = "all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<NoteDTO>> all() {
        val notes = noteService.fetchNoteList();

        return ResponseEntity.ok(notes);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> download(@RequestParam("uuid") UUID uuid) {
        val note = noteService.downloadNote(uuid);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", note.fileName()))
                .body(note.content());
    }

    @SneakyThrows
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteDTO> update(
            @RequestParam("uuid") UUID uuid,
            @RequestParam("note") MultipartFile file
    ) {
        return ResponseEntity
                .ok(noteService.update(uuid, file));
    }

}
