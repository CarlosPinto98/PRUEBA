package com.unimag.aeropuerto.API.Controller;


import com.unimag.aeropuerto.DTO.BookingDTO;
import com.unimag.aeropuerto.services.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/bookings")
@RequiredArgsConstructor
@Validated

public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDTO.bookingResponse> create(@Valid @RequestBody BookingDTO.bookingCreateRequest req, UriComponentsBuilder uri) {
        var body = bookingService.create(req);
        var location = uri.path("/api/bookings/{id}").buildAndExpand(body.id()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO.bookingResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookingService .delete(id);
        return ResponseEntity.noContent().build();
    }

}
