package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.API.Error.NotFoundException;
import com.unimag.aeropuerto.DTO.AirportDTO;
import com.unimag.aeropuerto.Mappers.AirportMapper;
import com.unimag.aeropuerto.entidad.Airport;
import com.unimag.aeropuerto.repository.AirportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor

public class AirportServiceImpl implements AirportService {

    public AirportRepository airportRepository;
    public AirportMapper airportMapper;

    @Override
    public AirportDTO.AirportResponse create(AirportDTO.AirportCreateRequest request) {
        return airportMapper.toDTO(airportRepository.save(airportMapper.toEntity(request)));
    }

    @Override
    public AirportDTO.AirportResponse get(Long id) {
        var a = airportRepository.findById(id).orElseThrow(()-> new NotFoundException("Airport not found"));
        return airportMapper.toDTO(a);
    }

    @Override
    public Airport getObjectById(Long id) {
        return airportRepository.findById(id).orElseThrow(()-> new NotFoundException("Airport with code " + id + " not found"));
    }

    @Override
    public AirportDTO.AirportResponse update(Long id, AirportDTO.AirportUpdateRequest request) {
        var a = airportRepository.findById(id).orElseThrow(()-> new NotFoundException("Airport not found"));
        airportMapper.updateEntity(request,a);
        return airportMapper.toDTO(a);
    }

    @Override
    public void delete(Long id) {
        airportRepository.deleteById(id);
    }

    @Override
    public Page<AirportDTO.AirportResponse> list(Pageable pageable) {
        return airportRepository.findAll(pageable).map(airportMapper::toDTO);
    }

    public Airport getAirportByCode(String code) {
        return airportRepository.findByCode(code).orElseThrow(()-> new NotFoundException("Airport with id: " + code + " not found"));
    }
}
