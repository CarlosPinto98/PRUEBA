package com.unimag.aeropuerto.API.Controller;


import com.unimag.aeropuerto.DTO.*;
import com.unimag.aeropuerto.services.PassengerProfileService;
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
@RequestMapping("api/passengerProfiles")
@RequiredArgsConstructor
@Validated
public class PassengerProfileController {


    private final PassengerProfileService passengerProfileService;


    @PostMapping
    public ResponseEntity<PassengerProfileDTO.passengerProfileResponse> create(@Valid @RequestBody PassengerProfileDTO.passengerProfileCreateRequest req, UriComponentsBuilder uri) {
        var body = passengerProfileService.create(req);
        var location = uri.path("/api/passengerProfiles/{id}").buildAndExpand(body.passengerProfileID()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerProfileDTO.passengerProfileResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(passengerProfileService.get(id));
    }
    @GetMapping
    public ResponseEntity<Page<PassengerProfileDTO.passengerProfileResponse>> list(@RequestParam(defaultValue = "0") int page,
                                                                                   @RequestParam(defaultValue = "8") int size) {
        var result = passengerProfileService.list(PageRequest.of(page, size, Sort.by("id").ascending()));
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        passengerProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PassengerProfileDTO.passengerProfileResponse>  update(@PathVariable Long id, @Valid @RequestBody PassengerProfileDTO.passengerProfileUpdateRequest updateRequest) {
        return ResponseEntity.ok(passengerProfileService.update(id, updateRequest));
    }
}
