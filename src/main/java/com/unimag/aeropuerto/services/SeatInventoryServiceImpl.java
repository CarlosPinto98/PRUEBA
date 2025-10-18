package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.SeatInventoryDTO;
import com.unimag.aeropuerto.Mappers.SeatInventoryMapper;
import com.unimag.aeropuerto.entidad.SeatInventory;
import com.unimag.aeropuerto.repository.SeatInventoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


@Component
@Transactional
@AllArgsConstructor

public class SeatInventoryServiceImpl implements SeatInventoryService{

    private final SeatInventoryRepository seatInventoryRepository;
    private final SeatInventoryMapper seatInventoryMapper;

    @Override
    public SeatInventoryDTO.seatInventoryDtoResponse create(SeatInventoryDTO.seatInventoryCreateRequest inventoryCreateRequest) {
        SeatInventory seatInventory = seatInventoryMapper.toEntity(inventoryCreateRequest);
        seatInventoryRepository.save(seatInventory);
        return seatInventoryMapper.toDTO(seatInventory);
    }

    @Override
    public SeatInventoryDTO.seatInventoryDtoResponse get(Long id) {
        var s = getObject(id);
        return seatInventoryMapper.toDTO(s);
    }

    @Override
    public SeatInventory getObject(Long id) {
        var s = seatInventoryRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "SeatInventory with id " + id + " not found."
        ));
        return s;
    }

    @Override
    public SeatInventoryDTO.seatInventoryDtoResponse update(Long id, SeatInventoryDTO.seatInventoryUpdateRequest updateRequest) {
        var seatInventory = getObject(id);
        seatInventoryMapper.updateEntity(updateRequest, seatInventory);
        return seatInventoryMapper.toDTO(seatInventory);
    }

    @Override
    public SeatInventoryDTO.seatInventoryDtoResponse update(SeatInventory entity, SeatInventoryDTO.seatInventoryUpdateRequest updateRequest) {
        seatInventoryMapper.updateEntity(updateRequest, entity);
        return seatInventoryMapper.toDTO(entity);
    }

    @Override
    public Page<SeatInventoryDTO.seatInventoryDtoResponse> list(Pageable pageable) {
        return seatInventoryRepository.findAll(pageable).map(seatInventoryMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        var p = getObject(id);
        seatInventoryRepository.delete(p);
    }

    public SeatInventory createAndReturn(SeatInventoryDTO.seatInventoryCreateRequest inventoryCreateRequest){
        SeatInventory seatInventory = seatInventoryMapper.toEntity(inventoryCreateRequest);
        return seatInventoryRepository.save(seatInventory);
    }
}
