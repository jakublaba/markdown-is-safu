package com.krabelard.safeapp.mapper;


import com.krabelard.safeapp.dto.NoteDTO;
import com.krabelard.safeapp.model.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface NoteMapper {

    NoteDTO entityToDto(Note entity);

}
