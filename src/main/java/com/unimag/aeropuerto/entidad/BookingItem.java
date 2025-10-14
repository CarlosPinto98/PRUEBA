package com.unimag.aeropuerto.entidad;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class BookingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private BigDecimal price;
    private Integer segmentOrder;
    private Cabin cabin;

    @ManyToOne
    @JoinColumn(name = "bookingId")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "flightId")
    private Flight flight;
}
