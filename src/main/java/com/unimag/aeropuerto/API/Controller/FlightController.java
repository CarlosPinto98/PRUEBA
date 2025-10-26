package com.unimag.aeropuerto.API.Controller;


import com.unimag.aeropuerto.DTO.FlightDTO;
import com.unimag.aeropuerto.services.FlightService;
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
@RequestMapping("api/flights")
@RequiredArgsConstructor
@Validated

public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightDTO.flightResponse> create(@Valid @RequestBody FlightDTO.flightCreateRequest req, UriComponentsBuilder uri) {
        var body = flightService.create(req);
        var location = uri.path("/api/flights/{id}").buildAndExpand(body.flightId()).toUri();
        return ResponseEntity.created(location).body(body);
    }
    @GetMapping
    public ResponseEntity<Page<FlightDTO.flightResponse>> list(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "8") int size) {
        var result = flightService.list(PageRequest.of(page, size, Sort.by("id").ascending()));
        return ResponseEntity.ok(result);

    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO.flightResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        flightService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FlightDTO.flightResponse>  update(@PathVariable Long id, @Valid @RequestBody FlightDTO.flightUpdateRequest updateRequest) {
        return ResponseEntity.ok(flightService.update(id, updateRequest));
    }
}
