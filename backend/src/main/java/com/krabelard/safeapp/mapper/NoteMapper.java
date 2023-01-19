package com.krabelard.safeapp.mapper;


import com.krabelard.safeapp.dto.NoteDTO;
import com.krabelard.safeapp.model.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface NoteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "content", ignore = true)
    Note dtoToEntity(NoteDTO dto);

    NoteDTO entityToDto(Note entity);

}
