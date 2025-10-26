package com.unimag.aeropuerto.API.Controller;


import com.unimag.aeropuerto.DTO.SeatInventoryDTO;
import com.unimag.aeropuerto.services.SeatInventoryService;
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
@RequestMapping("api/seatInventories")
@RequiredArgsConstructor
@Validated

public class SeatInventoryController {

    private final SeatInventoryService seatInventoryService ;


    @PostMapping
    public ResponseEntity<SeatInventoryDTO.seatInventoryDtoResponse> create(@Valid @RequestBody SeatInventoryDTO.seatInventoryCreateRequest req, UriComponentsBuilder uri) {
        var body = SeatInventoryService.create(req);
        var location = uri.path("/api/seatInventories/{id}").buildAndExpand(body.seatInventoryId()).toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatInventoryDTO.seatInventoryDtoResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(seatInventoryService.get(id));
    }
    @GetMapping
    public ResponseEntity<Page<SeatInventoryDTO.seatInventoryDtoResponse>> list(@RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "8") int size) {
        var result = seatInventoryService.list(PageRequest.of(page, size, Sort.by("id").ascending()));
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        SeatInventoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SeatInventoryDTO.seatInventoryDtoResponse>  update(@PathVariable Long id, @Valid @RequestBody SeatInventoryDTO.seatInventoryUpdateRequest updateRequest) {
        return ResponseEntity.ok(SeatInventoryService.update(id, updateRequest));
    }
}
