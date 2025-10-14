package com.unimag.aeropuerto.entidad;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"flightsOrigin", "flightsDestination"})@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
    private String city;

    @OneToMany(mappedBy = "originAirport")
    private List<Flight> flightsOrigin;

    @OneToMany(mappedBy = "destinationAirport")
    private List<Flight> flightsDestination;

    public void addFlightOrigin(Flight flight) {
        if (this.flightsOrigin == null) {
            this.flightsOrigin = new ArrayList<>();
        }
        flightsOrigin.add(flight);
    }
    public void addFlightDestination(Flight flight) {
        if (this.flightsDestination == null) {
            this.flightsDestination = new ArrayList<>();
        }
        flightsDestination.add(flight);
    }
}
