package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.BookingItemDTO;
import com.unimag.aeropuerto.entidad.BookingItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingItemService {

    BookingItemDTO.bookingItemReponse create(BookingItemDTO.bookingItemCreateRequest bookingItemCreateRequest);
    BookingItemDTO.bookingItemReponse update(Long id , BookingItemDTO.bookingItemUpdateRequest bookingItemUpdateRequest);
    void delete(Long id);
    BookingItem findBookingItem(Long id);
    Page<BookingItemDTO.bookingItemReponse> list(Pageable pageable);

    BookingItemDTO.bookingItemReponse get(Long l);
}
