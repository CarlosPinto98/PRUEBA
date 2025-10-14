package com.unimag.aeropuerto.entidad;

import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class SeatInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalSeats;
    private Integer availableSeats;
    private Cabin cabin;

    @ManyToOne
    @JoinColumn(name = "flightId")
    private Flight flight;
}
