package com.unimag.aeropuerto.Mappers;

import com.unimag.aeropuerto.DTO.TagDTO;
import com.unimag.aeropuerto.entidad.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.web.bind.annotation.Mapping;


@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag toEntity(TagDTO.tagCreateRequest createRequest);

    @Mapping(target = "tagId", source = "id")
    TagDTO.tagResponse toDTO(Tag entity);

    void updateRequest(TagDTO.tagUpdateRequest updateRequest, @MappingTarget Tag tag);
}
