package com.cezarykluczynski.stapi.model.genre.repository;

import com.cezarykluczynski.stapi.model.genre.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

	Optional<Genre> findByName(String genderName);

}
