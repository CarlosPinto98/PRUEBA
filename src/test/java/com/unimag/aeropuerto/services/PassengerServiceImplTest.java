package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.*;
import com.unimag.aeropuerto.Mappers.*;

import com.unimag.aeropuerto.entidad.Passenger;
import com.unimag.aeropuerto.entidad.PassengerProfile;
import com.unimag.aeropuerto.repository.PassengerProfileRepository;
import com.unimag.aeropuerto.repository.PassengerRepository;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerServiceImplTest {

    @Mock
    PassengerRepository passengerRepository;
    @Mock
    PassengerMapper passengerMapper;
    @Mock
    PassengerProfileMapper passengerProfileMapper;
    @InjectMocks
    PassengerServiceImpl passengerServiceimpl;
    @Mock
    PassengerProfileRepository passengerProfileRepository;
    @Mock
    PassengerProfileServiceImpl passengerProfileServiceImpl;

    @Test
    void create() {

        var p = new PassengerDTO.passengerCreateRequest("Jose", "notiene@gmail.com", new PassengerProfileDTO.passengerProfileCreateRequest("222", "+33"));
        when(passengerRepository.save(any(Passenger.class))).thenAnswer(inv -> {
            Passenger p2 = inv.getArgument(0);
            p2.setId(11L);
            return p2;
        });

        PassengerProfile expectedProfile = PassengerProfile.builder()
                .countryCode("+33")
                .phone("222")
                .id(2L)
                .build();

        when(passengerMapper.toEntity(any())).thenAnswer(inv -> {
            PassengerDTO.passengerCreateRequest request =  inv.getArgument(0);
            return Passenger.builder().fullName(request.fullName()).email(request.email()).build();
        });

        when(passengerProfileServiceImpl.createObject(any())).thenReturn(expectedProfile);

        when(passengerProfileMapper.toPassengerProfileView(any())).thenAnswer(inv -> {
            PassengerProfile object = inv.getArgument(0);
            return new PassengerProfileDTO.passengerProfileView(object.getPhone(), object.getCountryCode());
        });

        when(passengerMapper.toDTO(any())).thenAnswer(inv -> {
            Passenger object = inv.getArgument(0);
            return new PassengerDTO.passengerResponse(object.getId(), object.getFullName(), object.getEmail(), passengerProfileMapper.toPassengerProfileView(object.getProfile()));
        });

        var res = passengerServiceimpl.create(p);

        assertThat(res.id()).isEqualTo(11L);
        assertThat(res.fullName()).isEqualTo("Jose");
        assertThat(res.email()).isEqualTo("notiene@gmail.com");
        assertThat(res.passengerProfile().phone()).isEqualTo("222");
        assertThat(res.passengerProfile().countryCode()).isEqualTo("+33");
        verify(passengerRepository).save(any(Passenger.class));
    }

    @Test
    void update() {

        PassengerProfile profile = new PassengerProfile(2L, "222", "+34",null);
        var passenger = Passenger.builder().id(1L).email("notiene@gmail.com").fullName("Holita").profile(profile).build();

        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));

        doAnswer(inv -> {
            Passenger object =  inv.getArgument(0);
            PassengerDTO.passengerUpdateRequest request =  inv.getArgument(1);
            if (request.fullName() != null){
                object.setFullName(request.fullName());
            }
            if (request.email() != null){
                object.setEmail(request.email());
            }
            return null;
        }).when(passengerMapper).updateEntity(any(), any());

        doAnswer(inv -> {
            PassengerProfileDTO.passengerProfileUpdateRequest request =  inv.getArgument(0);
            PassengerProfile profileTemp = inv.getArgument(1);

            if (request.phoneNumber() != null){
                profileTemp.setPhone(request.phoneNumber());
            }
            if (request.countryCode() != null){
                profileTemp.setCountryCode(request.countryCode());
            }
            return null;


        }).when(passengerProfileMapper).updateEntity(any(), any());

        when(passengerProfileServiceImpl.update( (PassengerProfile) any(), any())).thenAnswer(inv -> {
            passengerProfileMapper.updateEntity(inv.getArgument(1), profile);
            return passengerProfileMapper.toDTO(profile);
        });

        when(passengerProfileMapper.toPassengerProfileView(any())).thenAnswer(inv -> {
            PassengerProfile object = inv.getArgument(0);
            if (object != null) {
                return new PassengerProfileDTO.passengerProfileView(object.getPhone(), object.getCountryCode());
            }
            return null;
        });

        when(passengerMapper.toDTO(any())).thenAnswer(inv -> {
            Passenger object = inv.getArgument(0);
            return new PassengerDTO.passengerResponse(object.getId(), object.getFullName(), object.getEmail(), passengerProfileMapper.toPassengerProfileView(object.getProfile()));
        });

        var passengerProfileUpdateRequest = new PassengerProfileDTO.passengerProfileUpdateRequest("304", "+57");
        var passengerUpdate = new PassengerDTO.passengerUpdateRequest("Que tal", "yatengo@gmail.com", passengerProfileUpdateRequest);
        var update = passengerServiceimpl.update(1L, passengerUpdate);

        assertThat(update.fullName()).isEqualTo("Que tal");
        assertThat(update.email()).isEqualTo("yatengo@gmail.com");
        assertThat(update.passengerProfile().countryCode()).isEqualTo("+57");
        assertThat(update.passengerProfile().phone()).isEqualTo("304");
    }

    @Test
    void list() {

        var profile = PassengerProfile.builder().id(1L).countryCode("45").build();
        var passenger  = Passenger.builder().id(1L).fullName("Jose").profile(profile).build();
        Page<Passenger> findAllss = new PageImpl<>(List.of(
                passenger
        ));

        when(passengerRepository.findAll(PageRequest.of(0,1))).thenReturn(findAllss);

        when(passengerProfileMapper.toPassengerProfileView(any())).thenAnswer(inv -> {
            PassengerProfile object = inv.getArgument(0);
            if (object != null) {
                return new PassengerProfileDTO.passengerProfileView(object.getPhone(), object.getCountryCode());
            }
            return null;
        });

        when(passengerMapper.toDTO(any())).thenAnswer(inv -> {
            Passenger object = inv.getArgument(0);
            return new PassengerDTO.passengerResponse(object.getId(), object.getFullName(), object.getEmail(), passengerProfileMapper.toPassengerProfileView(object.getProfile()));
        });

        var passengerDTO = passengerServiceimpl.list(PageRequest.of(0,1));

        assertThat(passengerDTO.getContent().size()).isEqualTo(1);
        assertThat(passengerDTO.getContent().getFirst().fullName()).isEqualTo("Jose");
        assertThat(passengerDTO.getContent().getFirst().passengerProfile().countryCode()).isEqualTo("45");
    }
}