package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.API.Error.NotFoundException;
import com.unimag.aeropuerto.DTO.TagDTO;
import com.unimag.aeropuerto.Mappers.TagMapper;
import com.unimag.aeropuerto.entidad.Tag;
import com.unimag.aeropuerto.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor

public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagDTO.tagResponse create(TagDTO.tagCreateRequest createRequest) {
        var tag = tagMapper.toEntity(createRequest);
        return tagMapper.toDTO(tagRepository.save(tag));
    }

    @Override
    public TagDTO.tagResponse get(Long id) {
        return tagMapper.toDTO(getObject(id));
    }

    @Override
    public TagDTO.tagResponse update(Long id, TagDTO.tagUpdateRequest updateRequest) {
        var tag = getObject(id);
        tagMapper.updateRequest(updateRequest, tag);
        return tagMapper.toDTO(tag);
    }

    @Override
    public Page<TagDTO.tagResponse> list(Pageable pageable) {
        return  tagRepository.findAll(pageable).map(tagMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Tag getObject(Long id) {
        return tagRepository.findById(id).orElseThrow(() -> new NotFoundException("Tag no encontrado"));
    }

    @Override
    public Tag getObjectByName(String name) {
        return tagRepository.findTagByNameIgnoreCase(name).orElseThrow(() -> new NotFoundException("Tag no encontrado"));
    }

    @Override
    public TagDTO.tagResponse getByName(String name) {
        return tagMapper.toDTO(getObjectByName(name));
    }
}
