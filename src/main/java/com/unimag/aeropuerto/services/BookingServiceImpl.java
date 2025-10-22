package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.API.Error.NotFoundException;
import com.unimag.aeropuerto.DTO.BookingDTO;
import com.unimag.aeropuerto.Mappers.BookingMapper;
import com.unimag.aeropuerto.entidad.Booking;
import com.unimag.aeropuerto.entidad.Passenger;
import com.unimag.aeropuerto.repository.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;


@Service
@Transactional
@RequiredArgsConstructor

public class BookingServiceImpl implements BookingService {

    private final PassengerServiceImpl passengerService;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingDTO.bookingResponse create(BookingDTO.bookingCreateRequest request) {
        Passenger p = passengerService.getObject(request.passengerId());

        Booking b = bookingMapper.toEntity(request);
        b.setPassenger(p); // Asignamos la relación Passenger
        b.setCreatedAt(OffsetDateTime.now());
        p.addBooking(b);

        b = bookingRepository.save(b);

        return bookingMapper.toDTO(b);
    }

    @Override
    public BookingDTO.bookingResponse get(Long id) {
        // Usamos la instancia inyectada para el mapeo
        return bookingMapper.toDTO(getObject(id));
    }

    @Override
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    public Booking getObject(Long id){
        return bookingRepository.findById(id).orElseThrow(() -> new NotFoundException("Booking not found"));
    }
}
