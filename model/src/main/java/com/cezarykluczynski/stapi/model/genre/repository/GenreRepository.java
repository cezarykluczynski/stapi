package com.cezarykluczynski.stapi.model.genre.repository;

import com.cezarykluczynski.stapi.model.genre.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
