package com.unimag.aeropuerto.entidad;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"originAirport", "destinationAirport", "seatInventories", "bookingItems", "tags"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Flights")

public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String number;
    private OffsetDateTime arrivalTime;
    private OffsetDateTime departureTime;

    @ManyToOne
    @JoinColumn(name = "AirlineId")
    private Airline airline;
    @ManyToOne
    @JoinColumn(name="originAirportId")
    private Airport originAirport;
    @ManyToOne
    @JoinColumn(name="destinationAirportId")
    private Airport destinationAirport;
    @ManyToMany
    @JoinTable(name="Flight_tag", joinColumns = @JoinColumn(name="FlightId"),
            inverseJoinColumns = @JoinColumn(name="TagId"))

    private Set<Tag> tags = new HashSet<>();
    @OneToMany(mappedBy = "flight")
    private List<SeatInventory> seatInventories;
    @OneToMany(mappedBy = "flight")
    private List<BookingItem> bookingItems;

    public void clearTags() {
        if (this.tags != null) {
            this.tags.forEach(tag -> tag.getFlights().remove(this));
            this.tags.clear();
        }
    }
}
