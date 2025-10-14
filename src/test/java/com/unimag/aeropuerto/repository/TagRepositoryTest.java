package com.unimag.aeropuerto.repository;

import com.unimag.aeropuerto.entidad.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TagRepositoryTest extends AbstractRepositoryPSQL {

    @Autowired
    private TagRepository tagRepository;

    @Test
    @DisplayName("Buscar etiqueta por nombre")
    void findTagByNameIgnoreCase() {

        String tagName = "Wi-Fi";
        tagRepository.save(Tag.builder().name(tagName).build());

        Optional<Tag> foundTag = tagRepository.findTagByNameIgnoreCase("wi-fi");

        assertThat(foundTag).isPresent();
        assertThat(foundTag.get().getName()).isEqualTo(tagName);
    }

    @Test
    @DisplayName("Retorna todos lso tags que estén en una lista dada")
    void findTagsByNameIn() {

        Tag tag1 = Tag.builder().name("on-time").build();
        Tag tag2 = Tag.builder().name("pet-friendly").build();
        Tag tag3 = Tag.builder().name("wifi").build();
        tagRepository.saveAll(List.of(tag1, tag2, tag3));

        Collection<String> namesToFind = List.of("on-time", "wifi");
        List<Tag> foundTags = tagRepository.findTagsByNameIn(namesToFind);

        assertThat(foundTags).hasSize(2);
        assertThat(foundTags).extracting(Tag::getName).containsExactlyInAnyOrder("on-time", "wifi");
    }

    @Test
    void findAllByName() {

        // 1. Arrange (Preparación): Crear y guardar etiquetas de prueba
        // Guardamos varias etiquetas con el mismo nombre para simular duplicados
        String tagName = "EquipajePrioritario";

        var tag1 = Tag.builder().name(tagName).build();
        var tag2 = Tag.builder().name(tagName).build();
        var tag3 = Tag.builder().name("SoloWiFi").build(); // Etiqueta diferente

        tagRepository.saveAll(List.of(tag1, tag2, tag3));

        // 2. Act (Acción): Llamar al método del repositorio con el nombre a buscar
        List<Tag> foundTags = tagRepository.findAllByName(tagName);

        // 3. Assert (Verificación): Comprobar el resultado

        // Verificamos que la lista no sea nula ni vacía
        assertThat(foundTags)
                .isNotNull()
                .isNotEmpty();

        // Verificamos que la cantidad de elementos encontrados sea 2
        assertThat(foundTags).hasSize(2);

        // Verificamos que todas las etiquetas encontradas tengan el nombre correcto
        // 'extracting(Tag::getName)' es una aserción de AssertJ muy útil
        assertThat(foundTags)
                .extracting(Tag::getName)
                .containsOnly(tagName);
    }
}