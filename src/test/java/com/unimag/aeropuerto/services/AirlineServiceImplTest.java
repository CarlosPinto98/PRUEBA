package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.*;
import com.unimag.aeropuerto.Mappers.AirlineMapper;
import com.unimag.aeropuerto.entidad.Airline;
import com.unimag.aeropuerto.repository.AirlineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirlineServiceImplTest {


    @Mock
    AirlineRepository airlineRepository;
    @Mock
    AirlineMapper airlineMapper;
    @InjectMocks
    AirlineServiceImpl airlineServiceImpl;

    private final Long TEST_ID = 42L;

    @Test
    void create() {

        var createRequest = new AirlineDTO.airlineCreateRequest("Aero", "232323");

        Airline airlineEntity = new Airline();
        airlineEntity.setName("Aero");
        airlineEntity.setCode("232323");

        AirlineDTO.airlineResponse expectedResponse = new AirlineDTO.airlineResponse( 11L, "Aero", "232323", Collections.emptyList());

        when(airlineMapper.toEntity(createRequest)).thenReturn(airlineEntity);//(airlineEntity);

        when(airlineRepository.save(any(Airline.class))).thenAnswer(invocation -> {
            Airline airline = invocation.getArgument(0);
            airline.setId(11);
            airline.setFlights(Collections.emptyList());
            return airline;
        });

        when(airlineMapper.toDTO(any(Airline.class))).thenReturn(expectedResponse);

        AirlineDTO.airlineResponse result = airlineServiceImpl.create(createRequest);

        assertNotNull(result);
        assertEquals(11L, result.id());
        assertEquals("Aero", result.name());
        assertEquals("232323", result.code());
        assertEquals(Collections.emptyList(), result.flights());

    }

    @Test
    void get() {
    }

    @Test
    void getObjectById() {
    }

    @Test
    void list() {

        var airline1 = Airline.builder().id(1).code("1").name("areod").build();
        var airline2 = Airline.builder().id(2).code("3").name("eol").build();
        Page page = new PageImpl<>(List.of(airline1, airline2));

        when(airlineRepository.findAll(PageRequest.of(0,2))).thenReturn(page);
        when(airlineMapper.toDTO(any())).thenAnswer(inv -> {
            Airline airline = inv.getArgument(0);

            return new AirlineDTO.airlineResponse((long) airline.getId(),airline.getName(),airline.getCode(),Collections.emptyList());

        });

        Page<AirlineDTO.airlineResponse> pages = airlineServiceImpl.list(PageRequest.of(0, 2));

        assertNotNull(pages);
        assertThat(pages.getTotalElements()).isEqualTo(2);
        assertThat(pages.getTotalPages()).isEqualTo(1);
        assertThat(pages.getContent()).hasSize(2);
        assertThat(pages.getContent().get(1).id()).isEqualTo(2L);
        assertThat(pages.getContent().get(1).name()).isEqualTo("eol");
        assertThat(pages.getContent().get(0).id()).isEqualTo(1L);
        assertThat(pages.getContent().get(0).name()).isEqualTo("areod");

    }

    @Test
    void delete() {
    }

    @Test
    void update() {

        Airline existingAirline = Airline.builder().id(Math.toIntExact(TEST_ID)).name("Maicol").code("123").flights(Collections.emptyList()).build();

        var updateRequest = new AirlineDTO.airlineUpdateRequest(TEST_ID, "Jose", "456");
        var expectedResponse = new AirlineDTO.airlineResponse(TEST_ID, "Jose", "456", Collections.emptyList());

        when(airlineRepository.findById(TEST_ID)).thenReturn(Optional.of(existingAirline));

        doAnswer(invocation -> {
            Airline airline = invocation.getArgument(0);
            AirlineDTO.airlineUpdateRequest req = invocation.getArgument(1);

            airline.setName(req.name());
            airline.setCode(req.code());

            return null;}
        ).when(airlineMapper).updateEntity(any(Airline.class), any(AirlineDTO.airlineUpdateRequest.class));

        when(airlineMapper.toDTO(any())).thenReturn(expectedResponse);

        AirlineDTO.airlineResponse actualResponse = airlineServiceImpl.update(TEST_ID, updateRequest);

        assertEquals("Jose", existingAirline.getName(), "El nombre de la entidad debe ser actualizado");
        assertEquals("456", existingAirline.getCode(), "El código de la entidad debe ser actualizado");

        assertEquals(TEST_ID, actualResponse.id());
        assertEquals("Jose", actualResponse.name());
        assertEquals("456", actualResponse.code());
    }
}