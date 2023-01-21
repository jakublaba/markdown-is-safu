package com.krabelard.safeapp.controller;


import com.krabelard.safeapp.dto.NoteDTO;
import com.krabelard.safeapp.service.NoteService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UUID> upload(
            @RequestParam("note") MultipartFile file,
            @RequestParam("owner") String username
    ) {
        val uuid = noteService.create(file, username);

        return new ResponseEntity<>(uuid, HttpStatus.CREATED);
    }

    @GetMapping(
            value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<NoteDTO>> all(@RequestParam("owner") String username) {
        val notes = noteService.fetchNoteList(username);

        return ResponseEntity.ok(notes);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> download(
            @RequestParam("uuid") UUID uuid,
            @RequestParam("owner") String username
    ) {
        val note = noteService.downloadNote(uuid, username);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", note.fileName()))
                .body(note.content());
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteDTO> update(
            @RequestParam("uuid") UUID uuid,
            @RequestParam("owner") String username
    ) {
        noteService.delete(uuid, username);

        return ResponseEntity.ok(null);
    }

}
