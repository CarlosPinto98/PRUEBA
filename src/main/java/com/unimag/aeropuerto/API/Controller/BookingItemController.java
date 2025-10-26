package com.unimag.aeropuerto.API.Controller;


import com.unimag.aeropuerto.DTO.BookingItemDTO;
import com.unimag.aeropuerto.services.BookingItemService;
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
@RequestMapping("api/bookingItems")
@RequiredArgsConstructor
@Validated

public class BookingItemController {

    private final BookingItemService bookingItemService;


    @PostMapping
    public ResponseEntity<BookingItemDTO.bookingItemReponse> create(@Valid @RequestBody BookingItemDTO.bookingItemCreateRequest req, UriComponentsBuilder uri) {
        var body = bookingItemService.create(req);
        var location = uri.path("/api/bookingItems/{id}").buildAndExpand(body.bookingitemsId()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingItemDTO.bookingItemReponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(bookingItemService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<BookingItemDTO.bookingItemReponse>> list(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "8") int size) {
        var result = bookingItemService.list(PageRequest.of(page, size, Sort.by("id").ascending()));
        return ResponseEntity.ok(result);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookingItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookingItemDTO.bookingItemReponse>  update(@PathVariable Long id, @Valid @RequestBody BookingItemDTO.bookingItemUpdateRequest updateRequest) {
        return ResponseEntity.ok(bookingItemService.update(id, updateRequest));
    }
}
