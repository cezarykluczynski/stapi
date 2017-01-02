package com.cezarykluczynski.stapi.model.movie.repository;

import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long>, MovieRepositoryCustom {
}
