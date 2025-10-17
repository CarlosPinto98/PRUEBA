package com.unimag.aeropuerto.Mappers;

import com.unimag.aeropuerto.DTO.BookingItemDTO;
import com.unimag.aeropuerto.entidad.*;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingItemMapper {

    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "flight", ignore = true)
    BookingItem toEntity(BookingItemDTO.bookingItemCreateRequest createRequest);

    @Mapping(target = "bookingId", source = "booking")
    @Mapping(target = "flight", source = "flight")
    BookingItemDTO.bookingItemReponse toDTO(BookingItem entity);

    default Long mapBookingToId(Booking booking) {
        if (booking == null) {
            return null;
        }
        return booking.getId();
    }
    default Long mapFlightToId(Flight flight) {
        if (flight == null) {return null;}
        return (long) flight.getId();
    }


    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "flight", ignore = true)
    void updateEntity(BookingItemDTO.bookingItemUpdateRequest dto, @MappingTarget BookingItem entity);
}
