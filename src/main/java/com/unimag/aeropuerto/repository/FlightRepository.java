package com.unimag.aeropuerto.repository;

import java.util.Collection;
import com.unimag.aeropuerto.entidad.Flight;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findFlightByAirline_NameIgnoreCase(String airlineName, Pageable pageable);

    @Query("""

    select f 
    from Flight f 
    where f.originAirport.code = :origen and f.destinationAirport.code = :destino
    and f.departureTime between :fechaInicio and :fechaSalida""")
    Page<Flight> findFlightByOriginAndDestinationFlighs(@Param("origen") String origen,
                                                        @Param("destino") String destino,
                                                        @Param("fechaInicio") OffsetDateTime fechaInicio,
                                                        @Param("fechaSalida") OffsetDateTime fechaSalida , Pageable pageable);

    @Query("""
        select f from Flight f 
        left join fetch f.airline 
        left join fetch f.originAirport 
        left join fetch f.destinationAirport 
        left join fetch f.tags 
        where (:origen is null or f.originAirport.code = :origen)
          and (:destino is null or f.destinationAirport.code = :destino)
          and f.departureTime between :fechaInicio and :fechaSalida""")
    List<Flight> findFlightsByOptionalOriginAndDestination(
            @Param("origen") String origen,
            @Param("destino") String destino,
            @Param("fechaInicio") OffsetDateTime fechaInicio,
            @Param("fechaSalida") OffsetDateTime fechaSalida
    );

    @Query(value = """
select f.* 
from Flights f 
join Flight_tag at on f.id = at.flight_id 
join Tags t on at.tag_id = t.id
where t.name in (:tags) 
group by f.id
having count(distinct t.id) = :tamañoTags""", nativeQuery = true)
    List<Flight> findFlightsWithTags(@Param("tags") Collection<String> tags,
                                     @Param("tamañoTags") int sizeTags);
}
