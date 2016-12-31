package com.cezarykluczynski.stapi.etl.template.movie.linker;

import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Service
public class MovieProducersLinkingWorker implements MovieRealPeopleLinkingWorker {

	private SimpleMovieRealPeopleLinkingWorkerHelper simpleMovieRealPeopleLinkingWorkerHelper;

	@Inject
	public MovieProducersLinkingWorker(SimpleMovieRealPeopleLinkingWorkerHelper simpleMovieRealPeopleLinkingWorkerHelper) {
		this.simpleMovieRealPeopleLinkingWorkerHelper = simpleMovieRealPeopleLinkingWorkerHelper;
	}

	@Override
	public void link(Set<List<String>> source, Movie baseEntity) {
		baseEntity.getProducers().addAll(simpleMovieRealPeopleLinkingWorkerHelper
				.linkListsToStaff(source, MovieRealPeopleLinkingWorker.SOURCE));
	}
}
