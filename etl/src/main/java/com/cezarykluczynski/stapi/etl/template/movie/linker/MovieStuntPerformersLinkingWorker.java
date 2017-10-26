package com.cezarykluczynski.stapi.etl.template.movie.linker;

import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MovieStuntPerformersLinkingWorker implements MovieRealPeopleLinkingWorker {

	private final FirstPerformerFindingMovieRealPeopleLinkingWorkerHelper firstPerformerFindingMovieRealPeopleLinkingWorkerHelper;

	public MovieStuntPerformersLinkingWorker(FirstPerformerFindingMovieRealPeopleLinkingWorkerHelper
			firstPerformerFindingMovieRealPeopleLinkingWorkerHelper) {
		this.firstPerformerFindingMovieRealPeopleLinkingWorkerHelper = firstPerformerFindingMovieRealPeopleLinkingWorkerHelper;
	}

	@Override
	public void link(Set<List<String>> source, Movie baseEntity) {
		baseEntity.getStuntPerformers().addAll(firstPerformerFindingMovieRealPeopleLinkingWorkerHelper
				.linkListsToPerformers(source, MovieRealPeopleLinkingWorker.SOURCE));
	}

}
