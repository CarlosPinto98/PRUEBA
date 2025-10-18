package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.SeatInventoryDTO;
import com.unimag.aeropuerto.entidad.SeatInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SeatInventoryService {

    SeatInventoryDTO.seatInventoryDtoResponse create(SeatInventoryDTO.seatInventoryCreateRequest inventoryCreateRequest);
    SeatInventoryDTO.seatInventoryDtoResponse get(Long id);
    SeatInventory getObject(Long id);
    SeatInventoryDTO.seatInventoryDtoResponse update(Long id, SeatInventoryDTO.seatInventoryUpdateRequest updateRequest);
    SeatInventoryDTO.seatInventoryDtoResponse update(SeatInventory entity, SeatInventoryDTO.seatInventoryUpdateRequest updateRequest);
    Page<SeatInventoryDTO.seatInventoryDtoResponse> list(Pageable pageable);
    void delete(Long id);
}
