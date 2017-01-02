package com.cezarykluczynski.stapi.model.movie.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;

public interface MovieRepositoryCustom extends CriteriaMatcher<MovieRequestDTO, Movie> {
}
