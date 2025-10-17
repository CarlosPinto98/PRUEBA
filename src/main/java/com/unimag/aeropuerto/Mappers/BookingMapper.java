package com.unimag.aeropuerto.Mappers;

import com.unimag.aeropuerto.DTO.BookingDTO;
import com.unimag.aeropuerto.entidad.Booking;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring", uses = {PassengerMapper.class, BookingItemMapper.class})
public interface BookingMapper {

    @Mapping(target = "passenger", ignore = true)
    @Mapping(target = "items", ignore = true)
    Booking toEntity(BookingDTO.bookingCreateRequest request);

    @Mapping(target = "bookingItems", source = "items")
    BookingDTO.bookingResponse toDTO(Booking booking);
}
