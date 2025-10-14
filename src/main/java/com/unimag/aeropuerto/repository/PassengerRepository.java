package com.unimag.aeropuerto.repository;

import com.unimag.aeropuerto.entidad.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Optional<Passenger> findPassengerByEmailIgnoreCase(String email);
    @Query("""
select p 
from Passenger p left join fetch p.profile 
where lower(p.email) = lower(:email)""")
    Optional<Passenger> findPassengerByEmailIgnoreCaseJPQL(@Param("email") String email);


    Optional<Passenger> findByid(Long id);
}
