package com.unimag.aeropuerto.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class TagDTO {

    public record tagCreateRequest(
            @NotBlank
            @Size(min = 2, max = 30)
            String name) implements Serializable {}

    public record tagUpdateRequest(
            @Size(min = 2, max = 30)
            String name) implements Serializable {}

    public record tagResponse(
            Long tagId,
            String name) implements Serializable {}
}
