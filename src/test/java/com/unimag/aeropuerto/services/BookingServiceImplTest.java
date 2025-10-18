package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.BookingDTO.*;
import com.unimag.aeropuerto.DTO.PassengerDTO;
import com.unimag.aeropuerto.Mappers.BookingMapper;
import com.unimag.aeropuerto.entidad.Booking;
import com.unimag.aeropuerto.entidad.Passenger;
import com.unimag.aeropuerto.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private BookingMapper bookingMapper;
    @InjectMocks
    private BookingServiceImpl bookingServiceImpl;
    @Mock
    private PassengerServiceimpl passengerService;


    private final Long bookingId = 1L;
    private final Long passengerId = 2L;

    @Test
    void create() {

        Passenger passenger = Passenger.builder().
                id(passengerId).
                fullName("Josesito crack7770").
                email("mamamaa@gmail.com").
                bookings(new ArrayList<>()).
                profile(null).
                build();

        bookingCreateRequest createRequest = new bookingCreateRequest(passengerId,null);

        PassengerDTO.passengerResponse passengerResponse= new PassengerDTO.passengerResponse(passengerId,passenger.getFullName(),passenger.getEmail(),null);
        Booking booking = new Booking();
        booking.setCreatedAt(OffsetDateTime.now());
        booking.setId(bookingId);
        booking.setItems(null);
        booking.setPassenger(passenger);


        when(passengerService.getObject(any())).thenReturn(passenger);
        when(bookingMapper.toEntity(any(bookingCreateRequest.class))).thenReturn(booking);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(bookingMapper.toDTO(any(Booking.class))).thenAnswer(invocation -> {
            Booking b = invocation.getArgument(0);
            return new bookingResponse(
                    b.getId(),
                    b.getCreatedAt(),
                    passengerResponse,
                    null
            );
        });

        bookingResponse response = bookingServiceImpl.create(createRequest);

        assertEquals(bookingId, response.id());
        assertNotNull(response.passenger());
        assertEquals(passengerId, response.passenger().id());
        assertNull(response.bookingItems());
    }

    @Test
    void get() {

        Passenger passenger = Passenger.builder()
                .id(passengerId)
                .fullName("Josesito crack7770")
                .email("mamamaa@gmail.com")
                .bookings(new ArrayList<>())
                .profile(null)
                .build();

        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setCreatedAt(OffsetDateTime.now());
        booking.setPassenger(passenger);
        booking.setItems(new ArrayList<>());

        PassengerDTO.passengerResponse passengerResponse = new PassengerDTO.passengerResponse(
                passengerId,
                passenger.getFullName(),
                passenger.getEmail(),
                null
        );

        bookingResponse expectedResponse = new bookingResponse(
                bookingId,
                booking.getCreatedAt(),
                passengerResponse,
                null
        );

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(bookingMapper.toDTO(booking)).thenReturn(expectedResponse);

        bookingResponse response = bookingServiceImpl.get(bookingId);

        assertNotNull(response);
        assertEquals(bookingId, response.id());
        assertNotNull(response.passenger());
        assertEquals(passengerId, response.passenger().id());
        assertEquals("Josesito crack7770", response.passenger().fullName());
        assertEquals("mamamaa@gmail.com", response.passenger().email());
    }
}