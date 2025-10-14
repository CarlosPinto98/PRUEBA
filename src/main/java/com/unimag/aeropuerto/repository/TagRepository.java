package com.unimag.aeropuerto.repository;

import com.unimag.aeropuerto.entidad.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findTagByNameIgnoreCase(String name);
    List<Tag> findTagsByNameIn(Collection<String> names);
    List<Tag> findAllByName(String name);
}
