package com.unimag.aeropuerto.repository;

import com.unimag.aeropuerto.entidad.PassengerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerProfileRepository extends JpaRepository<PassengerProfile, Long> {
}
