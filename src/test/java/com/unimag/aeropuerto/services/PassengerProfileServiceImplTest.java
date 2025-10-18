package com.unimag.aeropuerto.services;

import com.unimag.aeropuerto.DTO.PassengerProfileDTO;
import com.unimag.aeropuerto.Mappers.PassengerProfileMapper;
import com.unimag.aeropuerto.entidad.PassengerProfile;
import com.unimag.aeropuerto.repository.PassengerProfileRepository;
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
class PassengerProfileServiceImplTest {

    @Mock
    PassengerProfileRepository passengerProfileRepository;
    @InjectMocks
    PassengerProfileServiceImpl passengerProfileService;
    @Mock
    PassengerProfileMapper passengerProfileMapper;

    @Test
    void create() {

        var p = new PassengerProfileDTO.passengerProfileCreateRequest("222", "+3");
        when(passengerProfileRepository.save(any())).thenAnswer(inv -> {
            PassengerProfile pp = inv.getArgument(0);
            pp.setId(1L);
            return pp;
        });

        when(passengerProfileMapper.toEntity(any())).thenAnswer(inv -> {
            PassengerProfileDTO.passengerProfileCreateRequest request = inv.getArgument(0);
            return PassengerProfile.builder().phone(request.phoneNumber()).countryCode(request.countryCode()).build();
        });

        when(passengerProfileMapper.toDTO(any())).thenAnswer(inv -> {
            PassengerProfile object = inv.getArgument(0);
            return new PassengerProfileDTO.passengerProfileResponse(object.getId(), object.getPhone(), object.getCountryCode());
        });



        var saved = passengerProfileService.create(p);
        assertThat(saved).isNotNull();
        assertThat(saved.passengerProfileID()).isEqualTo(1L);
        assertThat(saved.phoneNumber()).isEqualTo("222");
        assertThat(saved.countryCode()).isEqualTo("+3");
        verify(passengerProfileRepository).save(any(PassengerProfile.class));
    }

    @Test
    void update() {

        var p = PassengerProfile.builder().id(1L).phone("222").build();
        when(passengerProfileRepository.findById(1L)).thenReturn(Optional.of(p));

        when(passengerProfileMapper.toDTO(any())).thenAnswer(inv -> {
            PassengerProfile object = inv.getArgument(0);
            return new PassengerProfileDTO.passengerProfileResponse(object.getId(), object.getPhone(), object.getCountryCode());
        });

        doAnswer(inv -> {
            PassengerProfileDTO.passengerProfileUpdateRequest request = inv.getArgument(0);
            PassengerProfile object = inv.getArgument(1);
            if (request.phoneNumber() != null){
                object.setPhone(request.phoneNumber());
            }
            if (request.countryCode() != null){
                object.setCountryCode(request.countryCode());
            }
            return null;
        }).when(passengerProfileMapper).updateEntity(any(), any());

        var passengerProfileUpdate = new PassengerProfileDTO.passengerProfileUpdateRequest("33", "+34");
        var update = passengerProfileService.update(1L, passengerProfileUpdate);

        assertThat(update.passengerProfileID()).isEqualTo(1L);
        assertThat(update.phoneNumber()).isEqualTo("33");
        assertThat(update.countryCode()).isEqualTo("+34");
    }

    @Test
    void list() {

        var pp = PassengerProfile.builder().id(1L).phone("222").build();
        Page<PassengerProfile> page = new PageImpl<>(List.of(pp));
        when(passengerProfileRepository.findAll(PageRequest.of(0,2))).thenReturn(page);

        when(passengerProfileMapper.toDTO(any())).thenAnswer(inv -> {
            PassengerProfile object = inv.getArgument(0);
            return new PassengerProfileDTO.passengerProfileResponse(object.getId(), object.getPhone(), object.getCountryCode());
        });

        var saved = passengerProfileService.list(PageRequest.of(0, 2));

        assertThat(saved.getContent().getFirst().passengerProfileID()).isEqualTo(1L);
        assertThat(saved.getContent().getFirst().phoneNumber()).isEqualTo("222");
    }
}