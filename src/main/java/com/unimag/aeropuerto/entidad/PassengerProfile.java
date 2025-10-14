package com.unimag.aeropuerto.entidad;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class PassengerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    private String countryCode;
    @OneToOne(mappedBy = "profile", fetch = FetchType.EAGER)
    private Passenger passenger;
}