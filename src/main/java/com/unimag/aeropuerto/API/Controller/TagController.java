package com.unimag.aeropuerto.API.Controller;

import com.unimag.aeropuerto.DTO.*;
import com.unimag.aeropuerto.services.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/tags")
@RequiredArgsConstructor
@Validated

public class TagController {

    private final TagService tagService;


    @PostMapping
    public ResponseEntity<TagDTO.tagResponse> create(@Valid @RequestBody TagDTO.tagCreateRequest req, UriComponentsBuilder uri) {
        var body = tagService.create(req);
        var location = uri.path("/api/tags/{id}").buildAndExpand(body.tagId()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO.tagResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.get(id));
    }

    @GetMapping("/by-name")
    public ResponseEntity<TagDTO.tagResponse> get(@RequestParam String email) {
        return ResponseEntity.ok(tagService.getByName(email));
    }
    @GetMapping
    public ResponseEntity<Page<TagDTO.tagResponse>> list(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "8") int size) {
        var result = tagService.list(PageRequest.of(page, size, Sort.by("id").ascending()));
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TagDTO.tagResponse> update(@PathVariable Long id, @Valid @RequestBody TagDTO.tagUpdateRequest updateRequest) {
        return ResponseEntity.ok(tagService.update(id, updateRequest));
    }

}
