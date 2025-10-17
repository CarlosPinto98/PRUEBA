package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.BookingItemDTO;
import com.unimag.aeropuerto.Mappers.BookingItemMapper;
import com.unimag.aeropuerto.entidad.Booking;
import com.unimag.aeropuerto.entidad.BookingItem;
import com.unimag.aeropuerto.entidad.Flight;
import com.unimag.aeropuerto.repository.BookingItemsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
@Transactional
@RequiredArgsConstructor

public class BookingItemServiceImpl implements BookingItemService {

    private final BookingItemsRepository bookingItemsRepository;
    private final BookingItemMapper bookingItemMapper;
    private final BookingServiceImpl bookingServiceImpl;
    private final FlightServiceImpl flightServiceImpl;

    @Override
    public BookingItemDTO.bookingItemReponse create(BookingItemDTO.bookingItemCreateRequest bookingItemCreateRequest) {
        BookingItem bookingItem = bookingItemMapper.toEntity(request);
        Flight f = flightServiceImpl.getFlightObject(request.fligthId());
        Booking b = bookingServiceImpl.getObject(request.bookingId());

        bookingItem.setBooking(b);
        bookingItem.setFlight(f);
        if (b.getItems() == null) {
            b.setItems(new ArrayList<>());
        }
        b.getItems().add(bookingItem);

        if (f.getBookingItems() == null) {
            f.setBookingItems(new ArrayList<>());
        }
        f.getBookingItems().add(bookingItem);

        bookingItem = bookingItemsRepository.save(bookingItem);

        return bookingItemMapper.toDTO(bookingItem);
    }

    @Override
    public BookingItemDTO.bookingItemReponse update(Long id, BookingItemDTO.bookingItemUpdateRequest bookingItemUpdateRequest) {
        BookingItem bookingItem = findBookingItem(id);
        bookingItemMapper.updateEntity(request, bookingItem);
        if (bookingItem.getFlight().getId() != request.flightId()){
            Flight f =  flightServiceImpl.getFlightObject(request.flightId());
            bookingItem.getFlight().getBookingItems().remove(bookingItem);
            bookingItem.setFlight(f);

        }
        if (bookingItem.getBooking().getId() != request.bookingId()){
            Booking b =  bookingServiceImpl.getObject(request.bookingId());
            bookingItem.getBooking().getItems().remove(bookingItem);
            bookingItem.setBooking(b);
        }
        BookingItem savedItem = bookingItemsRepository.save(bookingItem);


        return bookingItemMapper.toDTO(savedItem);
    }

    @Override
    public void delete(Long id) {
        bookingItemsRepository.deleteById(id);
    }

    @Override
    public BookingItem findBookingItem(Long id) {
        return bookingItemsRepository.findById(id).orElseThrow(()-> new NotFoundException("Booking Item not found"));
    }

    @Override
    public Page<BookingItemDTO.bookingItemReponse> list(Pageable pageable) {
        return bookingItemsRepository.findAll(pageable).map(bookingItemMapper::toDTO);
    }

    @Override
    public BookingItemDTO.bookingItemReponse get(Long bookingId) {
        return bookingItemMapper.toDTO(bookingItemsRepository.getReferenceById(bookingId));
    }
}
