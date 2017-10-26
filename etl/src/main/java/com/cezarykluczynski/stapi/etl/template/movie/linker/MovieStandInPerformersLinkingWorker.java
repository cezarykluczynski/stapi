package com.cezarykluczynski.stapi.etl.template.movie.linker;

import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MovieStandInPerformersLinkingWorker implements MovieRealPeopleLinkingWorker {

	private final FirstPerformerFindingMovieRealPeopleLinkingWorkerHelper firstPerformerFindingMovieRealPeopleLinkingWorkerHelper;

	public MovieStandInPerformersLinkingWorker(FirstPerformerFindingMovieRealPeopleLinkingWorkerHelper
			firstPerformerFindingMovieRealPeopleLinkingWorkerHelper) {
		this.firstPerformerFindingMovieRealPeopleLinkingWorkerHelper = firstPerformerFindingMovieRealPeopleLinkingWorkerHelper;
	}

	@Override
	public void link(Set<List<String>> source, Movie baseEntity) {
		baseEntity.getStandInPerformers().addAll(firstPerformerFindingMovieRealPeopleLinkingWorkerHelper
				.linkListsToPerformers(source, MovieRealPeopleLinkingWorker.SOURCE));
	}

}
