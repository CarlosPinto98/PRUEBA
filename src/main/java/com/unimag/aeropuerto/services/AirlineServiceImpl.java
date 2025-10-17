package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.AirlineDTO;
import com.unimag.aeropuerto.Mappers.AirlineMapper;
import com.unimag.aeropuerto.entidad.Airline;
import com.unimag.aeropuerto.repository.AirlineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor

public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;
    private final AirlineMapper airlineMapper;

    @Override
    public AirlineDTO.airlineResponse create(AirlineDTO.airlineCreateRequest req) {
        Airline entity = airlineMapper.toEntity(req);
        Airline saved = airlineRepository.save(entity);
        return airlineMapper.toDTO(saved);
    }

    @Override
    public AirlineDTO.airlineResponse get(Long id) {
        return airlineMapper.toDTO(getObjectById(id));
    }

    @Override
    public Airline getObjectById(Long id) {
        return airlineRepository.findById(id).orElseThrow(()-> new NotFoundException("Airline with id " + id + " not found"));
    }

    @Override
    public Page<AirlineDTO.airlineResponse> list(Pageable pageable) {
        return airlineRepository.findAll(pageable).map(airlineMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        airlineRepository.deleteById(id);
    }

    @Override
    public AirlineDTO.airlineResponse update(Long id, AirlineDTO.airlineUpdateRequest req) {
        var a = airlineRepository.findById(id).orElseThrow(()-> new NotFoundException("Airline not found"));
        airlineMapper.updateEntity(a,req);
        return  airlineMapper.toDTO(a);
    }
}
