package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.TagDTO;
import com.unimag.aeropuerto.entidad.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

    TagDTO.tagResponse create(TagDTO.tagCreateRequest createRequest);
    TagDTO.tagResponse get(Long id);
    TagDTO.tagResponse update(Long id, TagDTO.tagUpdateRequest updateRequest);
    Page<TagDTO.tagResponse> list(Pageable pageable);
    void delete(Long id);
    Tag getObject(Long id);
    Tag getObjectByName(String name);
    TagDTO.tagResponse getByName(String name);
}
