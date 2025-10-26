package com.unimag.aeropuerto.API.Controller;


import com.unimag.aeropuerto.DTO.AirlineDTO;
import com.unimag.aeropuerto.services.AirlineService;
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
@RequestMapping("api/airlines")
@RequiredArgsConstructor
@Validated
public class AirlineController {

    private final AirlineService service;

    @PostMapping
    public ResponseEntity<AirlineDTO.airlineResponse> create(@Valid @RequestBody AirlineDTO.airlineCreateRequest req, UriComponentsBuilder uri) {
        var body = service.create(req);
        var location = uri.path("/api/airlines/{id}").buildAndExpand(body.id()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlineDTO.airlineResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));

    }
    @GetMapping
    public ResponseEntity<Page<AirlineDTO.airlineResponse>> list(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "9") int size) {
        var result = service.list(PageRequest.of(page, size, Sort.by("id").ascending()));
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AirlineDTO.airlineResponse>  update(@PathVariable Long id, @Valid @RequestBody AirlineDTO.airlineUpdateRequest updateRequest) {
        return ResponseEntity.ok(service.update(id, updateRequest));
    }
}
