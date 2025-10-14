package com.unimag.aeropuerto.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Tags")

public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ManyToMany(mappedBy = "tags")
    private Set<Flight> flights;

    public void  addFlight(Flight flight) {
        if (this.flights == null) {
            this.flights = new HashSet<>();
        }
        this.flights.add(flight);
    }
}
