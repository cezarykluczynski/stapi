package com.cezarykluczynski.stapi.etl.template.movie.linker;


import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class MovieScreenplayAuthorsLinkingWorker implements MovieRealPeopleLinkingWorker {

	private final AllStaffFindingMovieRealPeopleLinkingWorkerHelper allStaffFindingMovieRealPeopleLinkingWorkerHelper;

	public MovieScreenplayAuthorsLinkingWorker(AllStaffFindingMovieRealPeopleLinkingWorkerHelper allStaffFindingMovieRealPeopleLinkingWorkerHelper) {
		this.allStaffFindingMovieRealPeopleLinkingWorkerHelper = allStaffFindingMovieRealPeopleLinkingWorkerHelper;
	}

	@Override
	public void link(Set<List<String>> source, Movie baseEntity) {
		baseEntity.getScreenplayAuthors().addAll(allStaffFindingMovieRealPeopleLinkingWorkerHelper
				.linkListsToStaff(source, MovieRealPeopleLinkingWorker.SOURCE));
	}

}
