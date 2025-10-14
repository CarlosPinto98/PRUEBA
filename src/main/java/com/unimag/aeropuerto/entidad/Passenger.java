package com.unimag.aeropuerto.entidad;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;

    @OneToOne
    @JoinColumn(name = "passengerProfileId")
    private PassengerProfile profile;

    @OneToMany(mappedBy = "passenger")
    @Builder.Default
    private List<Booking> bookings = new ArrayList<>();

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
}