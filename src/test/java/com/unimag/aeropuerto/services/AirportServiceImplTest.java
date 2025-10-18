package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.AirportDTO.*;
import com.unimag.aeropuerto.DTO.AirportDTO.*;
import com.unimag.aeropuerto.Mappers.AirportMapper;
import com.unimag.aeropuerto.entidad.Airport;
import com.unimag.aeropuerto.repository.AirportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class AirportServiceImplTest {

    @Mock
    private AirportRepository airportRepository;
    @Mock
    private AirportMapper airportMapper;

    @InjectMocks
    private AirportServiceImpl airportService;

    private final Long TEST_ID = 10L;

    @Test
    void create() {

        var createRequest = new AirportCreateRequest("El Dorado", "BOG", "Bogotá");
        Airport createdAirport = Airport.builder()
                .name(createRequest.name())
                .code(createRequest.code())
                .city(createRequest.city())
                .build();
        AirportResponse expectedRespose = new AirportResponse(TEST_ID,"El Dorado", "BOG", "Bogotá", null, null);

        when(airportMapper.toEntity(any(AirportCreateRequest.class))).thenReturn(createdAirport);
        when(airportRepository.save(any(Airport.class))).thenAnswer(invocation -> {
            Airport airportEntity = invocation.getArgument(0);
            airportEntity.setId(TEST_ID); // Asigna el ID de prueba
            return airportEntity;
        });
        when(airportMapper.toDTO(any(Airport.class))).thenReturn(expectedRespose);

        AirportResponse actualResponse = airportService.create(createRequest);

        assertEquals(TEST_ID, actualResponse.id(), "El ID debe ser el asignado por la BD (mock)");
        assertEquals("El Dorado", actualResponse.name());
        assertEquals("BOG", actualResponse.code());
        assertEquals("Bogotá", actualResponse.city());
    }

    @Test
    void update() {

        Airport existingAirport = Airport.builder()
                .id(TEST_ID)
                .name("Old Name")
                .code("OLD")
                .city("Old City")
                .build();
        AirportResponse expectedResponse = new AirportResponse(TEST_ID,"New Name","NEW","New City",null,null);

        var updateRequest = new AirportUpdateRequest("New Name", "NEW", "New City");

        when(airportRepository.findById(TEST_ID)).thenReturn(Optional.of(existingAirport));
        doAnswer(invocationOnMock -> {
            AirportUpdateRequest req = invocationOnMock.getArgument(0);
            Airport airportEntity = invocationOnMock.getArgument(1);
            airportEntity.setId(TEST_ID);
            airportEntity.setName(req.name());
            airportEntity.setCode(req.code());
            airportEntity.setCity(req.city());
            return null; //retorna nulo porque el metodo es void
        }).when(airportMapper).updateEntity(any(AirportUpdateRequest.class), any(Airport.class));
        when(airportMapper.toDTO(any(Airport.class))).thenReturn(expectedResponse);

        AirportResponse actualResponse = airportService.update(TEST_ID, updateRequest);


        assertEquals("New Name", existingAirport.getName(), "El nombre de la entidad debe ser actualizado");
        assertEquals("NEW", existingAirport.getCode(), "El código de la entidad debe ser actualizado");
        assertEquals("New City", existingAirport.getCity(), "La ciudad de la entidad debe ser actualizada");

        assertEquals(TEST_ID, actualResponse.id());
        assertEquals("New Name", actualResponse.name());
        assertEquals("NEW", actualResponse.code());
        assertEquals("New City", actualResponse.city());
    }

    @Test
    void list() {

        var airport1 = Airport.builder().id(1L).code("fundacion").city("Santa Marta").build();
        var airport2 = Airport.builder().id(2L).code("Fundacho").city("Santa Maria").build();

        Page<Airport> page = new PageImpl<>(List.of(airport1,airport2));

        when(airportRepository.findAll(PageRequest.of(0,2))).thenReturn(page);

        when(airportMapper.toDTO(any())).thenAnswer(inv -> {
            Airport airportEntity = inv.getArgument(0);

            return new AirportResponse(airportEntity.getId(), airportEntity.getName(), airportEntity.getCode(),
                    airportEntity.getCity(), null, null
            );
        });

        Page<AirportResponse> pages = airportService.list(PageRequest.of(0, 2));

        assertThat(pages).isNotNull();
        assertThat(pages.getTotalElements()).isEqualTo(2);
        assertThat(pages.getTotalPages()).isEqualTo(1);
        assertThat(pages.getContent()).hasSize(2);
        assertThat(pages.getContent().get(0)).isEqualTo(airportMapper.toDTO(airport1));
        assertThat(pages.getContent().get(1)).isEqualTo(airportMapper.toDTO(airport2));
        assertThat(pages.getContent().getFirst().code()).isEqualTo("fundacion");
    }
}