package com.unimag.aeropuerto.repository;

import com.unimag.aeropuerto.entidad.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query
    Page<Booking> pageOfReservationsAboutPassenger(@Param("email") String email,
                                                   Pageable pageable);

    @Query
    Optional<Booking> findBookingById(@Param("bookingId") Long bookingId);


}
