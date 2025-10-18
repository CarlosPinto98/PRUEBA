package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.BookingItemDTO.*;
import com.unimag.aeropuerto.DTO.FlightDTO;
import com.unimag.aeropuerto.Mappers.BookingItemMapper;
import com.unimag.aeropuerto.entidad.Booking;
import com.unimag.aeropuerto.entidad.BookingItem;
import com.unimag.aeropuerto.entidad.Cabin;
import com.unimag.aeropuerto.entidad.Flight;
import com.unimag.aeropuerto.repository.BookingItemsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingItemServiceImplTest {


    @Mock
    private BookingItemsRepository bookingItemsRepository;

    @Mock
    private BookingItemMapper bookingItemMapper;

    @Mock
    private FlightServiceImpl flightServiceImpl;

    @Mock
    private BookingServiceImpl bookingServiceImpl;

    @InjectMocks
    private BookingItemServiceImpl bookingItemService;

    private static final Long BOOKING_ITEM_ID = 1L;
    private static final Long BOOKING_ID = 1L;
    private static final Long FLIGHT_ID = 1L;

    @Test
    void create() {

        // Preparar datos
        Booking mockBooking = new Booking();
        mockBooking.setId(BOOKING_ID);
        mockBooking.setItems(new ArrayList<>());

        Flight mockFlight = Flight.builder()
                .id(Math.toIntExact(FLIGHT_ID))
                .number("FL101")
                .bookingItems(new ArrayList<>())
                .build();

        var createRequest = new bookingItemCreateRequest(
                new BigDecimal("500.00"), 1, Cabin.ECONOMY, BOOKING_ID, FLIGHT_ID
        );

        BookingItem mockBookingItem = BookingItem.builder()
                .id(BOOKING_ITEM_ID)
                .price(createRequest.price())
                .segmentOrder(createRequest.segmentOrder())
                .cabin(createRequest.cabin())
                .booking(mockBooking)
                .flight(mockFlight)
                .build();

        when(bookingItemMapper.toEntity(any(bookingItemCreateRequest.class))).thenReturn(mockBookingItem);

        when(flightServiceImpl.getFlightObject(FLIGHT_ID)).thenReturn(mockFlight);

        when(bookingServiceImpl.getObject(BOOKING_ID)).thenReturn(mockBooking);

        when(bookingItemsRepository.save(any(BookingItem.class))).thenReturn(mockBookingItem);

        when(bookingItemMapper.toDTO(any(BookingItem.class)))
                .thenReturn(
                        new bookingItemReponse(
                                BOOKING_ITEM_ID,
                                Cabin.ECONOMY,
                                BOOKING_ID,
                                new FlightDTO.flightResponse(FLIGHT_ID, "FL101", null, null, null, null, null, null, null)
                        )
                );

        bookingItemReponse actualResponse = bookingItemService.create(createRequest);

        assertNotNull(actualResponse);
        assertEquals(BOOKING_ITEM_ID, actualResponse.bookingitemsId());
        assertEquals(BOOKING_ID, actualResponse.bookingId());
        assertEquals(Cabin.ECONOMY, actualResponse.cabin());
        assertEquals(FLIGHT_ID, actualResponse.flight().flightId());
    }

    @Test
    void list() {

        var booking1 = BookingItem.builder().id(1L).cabin(Cabin.ECONOMY).build();
        var booking2 = BookingItem.builder().id(2L).cabin(Cabin.BUSINESS).build();

        Page<BookingItem> page = new PageImpl<>(List.of(booking1,booking2));

        when(bookingItemsRepository.findAll(PageRequest.of(0,2))).thenReturn(page);

        when(bookingItemMapper.toDTO(any())).thenAnswer(inv -> {
            BookingItem bookingItem = inv.getArgument(0);
            Long bookingId = null;
            if (bookingItem != null) {
                bookingId = bookingItem.getId();
            }

            return new bookingItemReponse(bookingItem.getId(), bookingItem.getCabin(),bookingId, null );
        });

        Page<bookingItemReponse> pages = bookingItemService.list(PageRequest.of(0, 2));

        assertThat(pages).isNotNull();
        assertThat(pages.getTotalElements()).isEqualTo(2);
        assertThat(pages.getTotalPages()).isEqualTo(1);
        assertThat(pages.getContent()).hasSize(2);
        assertThat(pages.getContent().get(0)).isEqualTo(bookingItemMapper.toDTO(booking1));
        assertThat(pages.getContent().get(1)).isEqualTo(bookingItemMapper.toDTO(booking2));
    }
}