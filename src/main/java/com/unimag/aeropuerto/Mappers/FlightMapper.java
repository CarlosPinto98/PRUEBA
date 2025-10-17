package com.unimag.aeropuerto.Mappers;

import com.unimag.aeropuerto.DTO.FlightDTO;
import com.unimag.aeropuerto.entidad.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.web.bind.annotation.Mapping;


@Mapper(componentModel = "spring", uses = {AirlineMapper.class, AirportMapper.class, SeatInventoryMapper.class, TagMapper.class,})

public interface FlightMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "airline", ignore = true)
    @Mapping(target = "originAirport", ignore = true)
    @Mapping(target = "destinationAirport", ignore = true)
    @Mapping(target = "seatInventories", ignore = true)
    @Mapping(target = "bookingItems", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Flight toEntity(FlightDTO.flightCreateRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "airline", ignore = true)
    @Mapping(target = "originAirport", ignore = true)
    @Mapping(target = "destinationAirport", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "seatInventories", ignore = true)
    @Mapping(target = "bookingItems", ignore = true)
    void patch(FlightDTO.flightUpdateRequest updateRequest, @MappingTarget Flight flight);

    @Mapping(target = "flightId", source = "id")
    FlightDTO.flightResponse toDTO(Flight entity);

    @Mapping(target = "flightId", source = "id")
    FlightDTO.flightAirportView toAirportView(Flight entity); //Lo usa mapstruc automaticamenre en airport

    @Mapping(target = "flightId", source = "id")
    FlightDTO.flightAirlineView toAirlineView(Flight entity); //Lo usa mapstruc automaticamenre en airline
}
