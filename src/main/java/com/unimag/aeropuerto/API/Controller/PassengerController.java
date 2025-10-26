package com.unimag.aeropuerto.API.Controller;

import com.unimag.aeropuerto.DTO.PassengerDTO;
import com.unimag.aeropuerto.services.PassengerService;
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
@RequestMapping("api/passengers")
@RequiredArgsConstructor
@Validated

public class PassengerController {

    private final PassengerService passengerService;


    @PostMapping
    public ResponseEntity<PassengerDTO.passengerResponse> create(@Valid @RequestBody PassengerDTO.passengerCreateRequest req, UriComponentsBuilder uri) {
        var body = passengerService.create(req);
        var location = uri.path("/api/passengers/{id}").buildAndExpand(body.id()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerDTO.passengerResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<PassengerDTO.passengerResponse>> list(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "8") int size) {
        var result = passengerService.list(PageRequest.of(page, size, Sort.by("id").ascending()));
        return ResponseEntity.ok(result);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        passengerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PassengerDTO.passengerResponse>  update(@PathVariable Long id, @Valid @RequestBody PassengerDTO.passengerUpdateRequest updateRequest) {
        return ResponseEntity.ok(passengerService.update(id, updateRequest));
    }

}
