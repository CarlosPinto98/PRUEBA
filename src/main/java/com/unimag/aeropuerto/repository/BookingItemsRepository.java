package com.unimag.aeropuerto.repository;

import com.unimag.aeropuerto.entidad.BookingItem;
import com.unimag.aeropuerto.entidad.Cabin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BookingItemsRepository extends JpaRepository<BookingItem, Long> {

    @Query
    List<BookingItem> findByBookingId(@Param("bookingId") Long bookingId);

    @Query
    BigDecimal totalPriceOfBookingItems(@Param("bookingId") Long bookingId);

    @Query
    Long howManyBookingWasSellByFlightIdAndCabin(@Param("flightId")  Long flightId,
                                                 @Param("cabin") Cabin cabin);
}
