package com.unimag.aeropuerto.entidad;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private OffsetDateTime createdAt = OffsetDateTime.now();
    @ManyToOne
    @JoinColumn(name ="passengerId")
    private Passenger passenger;
    @OneToMany(mappedBy = "booking")
    private List<BookingItem> items;

    public void addItem(BookingItem item) {
        this.items.add(item);
    }
}