package com.unimag.aeropuerto.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter

public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String code;

    @OneToMany(mappedBy = "airline")
    List<Flight> flights;

    public List<Flight> getFlights() {
        List<Flight> flights = new ArrayList<>();
        for(Flight flight : flights){
            flights.add(flight);
        }
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }


    public void addFlight(Flight fly){
        if (this.flights == null) {
            this.flights = new ArrayList<>();
        }
        this.flights.add(fly);
    }

}
