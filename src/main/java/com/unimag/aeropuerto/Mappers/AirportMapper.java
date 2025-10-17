package com.unimag.aeropuerto.Mappers;

import com.unimag.aeropuerto.DTO.*;
import com.unimag.aeropuerto.entidad.Airport;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {FlightMapper.class})
public interface AirportMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flightsOrigin", ignore = true)
    @Mapping(target = "flightsDestination", ignore = true)
    Airport toEntity(AirportDTO.AirportCreateRequest request);

    AirportDTO.AirportResponse toDTO(Airport entity);

    @Mapping(target = "flightsOrigin", ignore = true)
    @Mapping(target = "flightsDestination", ignore = true)
    AirportDTO.AirportResponse toDTOSimple(Airport entity);

    AirportDTO.AirportFlightView toFlightView(Airport airport);//lo uda mapstruct automaticamente en flight

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flightsOrigin", ignore = true)
    @Mapping(target = "flightsDestination", ignore = true)
    void updateEntity(AirportDTO.AirportUpdateRequest request, @MappingTarget Airport entity);
}
