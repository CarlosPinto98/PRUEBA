package com.unimag.aeropuerto.repository;

import com.unimag.aeropuerto.entidad.Booking;
import com.unimag.aeropuerto.entidad.BookingItem;
import com.unimag.aeropuerto.entidad.Cabin;
import com.unimag.aeropuerto.entidad.Flight;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat; // 👈 CORRECCIÓN 1: Se usa la importación estándar

class BookingItemsRepositoryTest extends AbstractRepositoryPSQL{

    @Autowired
    BookingItemsRepository repository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    FlightRepository flightRepository;

    @Test
    @DisplayName("BookingItem: Buscar items de reserva por ID y ordenarlos por segmento") // 👈 Ajuste para mayor claridad
    void findByBookingId() {

        // Creamos los objeto de prueba
        var booking = Booking.builder().build();
        Booking savedBooking = bookingRepository.save(booking);

        var bookingItems1 = BookingItem.builder().booking(savedBooking).segmentOrder(4).build();
        var bookingItems2 = BookingItem.builder().booking(savedBooking).segmentOrder(2).build();

        // Se guardan los bookingItems
        repository.saveAll(List.of(bookingItems1, bookingItems2));


        List<BookingItem> byBookingId = repository.findByBookingId(savedBooking.getId());

        // Se comprueba que el objeto no este vacio y que tenga la longitud de los bookings realizados
        // Nota: Las aserciones para Listas funcionan directamente con AssertJ
        assertThat(byBookingId).isNotNull().hasSize(2);

        // Se comprueba el ordenamiento
        assertThat(byBookingId.get(0).getSegmentOrder()).isEqualTo(2);
        assertThat(byBookingId.get(1).getSegmentOrder()).isEqualTo(4);
    }

    @Test
    @DisplayName("BookingItem: Suma total del precio de los items cargados") // 👈 Ajuste en la descripción
    void totalPriceOfBookingItems() {

        // Datos de prueba
        var booking = Booking.builder().build();
        Booking savedBooking = bookingRepository.save(booking);

        var bookingItems1 = BookingItem.builder()
                .booking(savedBooking)
                .price(new BigDecimal("100.00")) // Se usa una escala consistente
                .build();
        var bookingItems2 = BookingItem.builder()
                .booking(savedBooking)
                .price(new BigDecimal("200.00")) // Se usa una escala consistente
                .build();

        repository.saveAll(List.of(bookingItems1, bookingItems2));

        // LLamo al metodo del repositorio
        BigDecimal totalCalculado = repository.totalPriceOfBookingItems(savedBooking.getId());

        // Verificamos la suma. Usamos isEqualByComparingTo para comparar BigDecimals correctamente. 👈 CORRECCIÓN 2
        assertThat(totalCalculado).isEqualByComparingTo(new BigDecimal("300.00"));
    }

    @Test
    @DisplayName("BookingItem: Contar los asientos reservados por ID de vuelo y cabina") // 👈 Ajuste en la descripción
    void howManyBookingWasSellByFlightIdAndCabin() {

        var flight= Flight.builder().build();
        Flight savedFlight = flightRepository.save(flight);

        var bookingItem1 =  BookingItem.builder().flight(savedFlight).
                cabin(Cabin.BUSINESS).build();
        var bookingItem2 =  BookingItem.builder().flight(savedFlight).
                cabin(Cabin.BUSINESS).build();

        repository.saveAll(List.of(bookingItem1, bookingItem2));

        Long asientosReservados = repository.howManyBookingWasSellByFlightIdAndCabin(savedFlight.getOriginAirport().getId(), Cabin.BUSINESS);

        assertThat(asientosReservados).isNotNull();
        // Se usa 2L para asegurar la comparación con el tipo Long
        assertThat(asientosReservados).isEqualTo(2L);
    }
}