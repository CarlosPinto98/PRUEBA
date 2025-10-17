package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.BookingDTO;

public interface BookingService {

    public BookingDTO.bookingResponse create(BookingDTO.bookingCreateRequest request);
    public BookingDTO.bookingResponse get(Long id);
    public void delete(Long id);
}
