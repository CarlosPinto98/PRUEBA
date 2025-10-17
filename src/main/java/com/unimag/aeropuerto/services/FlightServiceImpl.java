package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.FlightDTO;
import com.unimag.aeropuerto.Mappers.FlightMapper;
import com.unimag.aeropuerto.entidad.Airline;
import com.unimag.aeropuerto.entidad.Airport;
import com.unimag.aeropuerto.entidad.Flight;
import com.unimag.aeropuerto.entidad.Tag;
import com.unimag.aeropuerto.repository.FlightRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Component
@Transactional
@AllArgsConstructor

public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final AirportServiceImpl airportService;
    private final AirlineServiceImpl airlineService;
    private final TagServiceImpl tagService;
    private final SeatInventoryServiceImpl seatInventoryService;
    private final FlightMapper flightMapper;

    @Override
    public FlightDTO.flightResponse create(FlightDTO.flightCreateRequest createRequest) {
        var flight = flightMapper.toEntity(createRequest);
        if (createRequest.airlineId() != null) {
            Airline airline = airlineService.getObjectById(createRequest.airlineId());
            flight.setAirline(airline);
            airline.addFlight(flight);
        }

        if (createRequest.originAirportCode() != null) {
            Airport origin = airportService.getAirportByCode(createRequest.originAirportCode());
            flight.setOriginAirport(origin);
            origin.addFlightOrigin(flight);
        }

        if (createRequest.destinationAirportCode()!= null) {
            Airport destination = airportService.getAirportByCode(createRequest.destinationAirportCode());
            flight.setDestinationAirport(destination);
            destination.addFlightDestination(flight);
        }
        if (createRequest.tags() != null) {
            Set<Tag> tags = createRequest.tags().stream().map(tagService::getObjectByName).collect(Collectors.toSet());
            flight.setTags(tags);
            tags.forEach(tag -> tag.addFlight(flight));
        }
        var seatInventories = createRequest.seatInventories().stream().map(seatInventoryService::createAndReturn).toList();
        flight.setSeatInventories(seatInventories);
        flight.getTags().forEach(tag2 -> tag2.addFlight(flight));
        seatInventories.forEach(seat -> seat.setFlight(flight));

        var saveFlight = flightRepository.save(flight);
        return flightMapper.toDTO(saveFlight);
    }

    @Override
    public FlightDTO.flightResponse update(Long id, FlightDTO.flightUpdateRequest updateRequest) {
        Flight flight = getFlightObject(id);

        flightMapper.patch(updateRequest, flight);

        if (updateRequest.airlineId() != null) {
            Airline airline = airlineService.getObjectById(updateRequest.airlineId());
            flight.setAirline(airline);
            airline.addFlight(flight);
        }

        if (updateRequest.originAirportCode() != null) {
            Airport origin = airportService.getAirportByCode(updateRequest.originAirportCode());
            flight.setOriginAirport(origin);
            origin.addFlightOrigin(flight);
        }

        if (updateRequest.destinationAirportCode() != null) {
            Airport destination = airportService.getAirportByCode(updateRequest.destinationAirportCode());
            flight.setDestinationAirport(destination);
            destination.addFlightDestination(flight);
        }

        if (updateRequest.tags() != null) {
            if (updateRequest.tags().isEmpty()) {
                flight.clearTags();
            } else {
                Set<Tag> tags = updateRequest.tags().stream().map(tagService::getObjectByName).collect(Collectors.toSet());
                flight.setTags(tags);
                tags.forEach(tag -> tag.addFlight(flight));
            }
        }
        Flight updatedFlight = flightRepository.save(flight);
        return flightMapper.toDTO(updatedFli);
    }

    @Override
    public FlightDTO.flightResponse get(Long id) {
        return flightMapper.toDTO(getFlightObject(id));
    }

    @Override
    public Page<FlightDTO.flightResponse> list(Pageable pageable) {
        return flightRepository.findAll(pageable).map(flightMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        flightRepository.deleteById(id);//Revisar el borrado en cascada
    }

    @Override
    public Flight getFlightObject(Long id) {
        var f =  flightRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "flight with id: " + id + " not found"
        ));
        return f;
    }
}
