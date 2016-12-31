package com.cezarykluczynski.stapi.etl.template.movie.linker;

import com.cezarykluczynski.stapi.etl.common.processor.LinkingWorker;
import com.cezarykluczynski.stapi.etl.movie.creation.dto.MovieLinkedTitlesDTO;
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieClosingCreditsProcessor;
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieLinkedTitlesProcessor;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
@Slf4j
public class MovieRealPeopleLinkingWorkerComposite implements LinkingWorker<Page, Movie> {

	private MovieClosingCreditsProcessor movieClosingCreditsProcessor;

	private MovieLinkedTitlesProcessor movieLinkedTitlesProcessor;

	private MovieWritersLinkingWorker movieWritersLinkingWorker;

	private MovieScreenplayAuthorsLinkingWorker movieScreenplayAuthorsLinkingWorker;

	private MovieStoryAuthorsLinkingWorker movieStoryAuthorsLinkingWorker;

	private MovieDirectorsLinkingWorker movieDirectorsLinkingWorker;

	private MovieProducersLinkingWorker movieProducersLinkingWorker;

	@Inject
	public MovieRealPeopleLinkingWorkerComposite(MovieClosingCreditsProcessor movieClosingCreditsProcessor,
			MovieLinkedTitlesProcessor movieLinkedTitlesProcessor, MovieWritersLinkingWorker movieWritersLinkingWorker,
			MovieScreenplayAuthorsLinkingWorker movieScreenplayAuthorsLinkingWorker,
			MovieStoryAuthorsLinkingWorker movieStoryAuthorsLinkingWorker,
			MovieDirectorsLinkingWorker movieDirectorsLinkingWorker,
			MovieProducersLinkingWorker movieProducersLinkingWorker) {
		this.movieClosingCreditsProcessor = movieClosingCreditsProcessor;
		this.movieLinkedTitlesProcessor = movieLinkedTitlesProcessor;
		this.movieWritersLinkingWorker = movieWritersLinkingWorker;
		this.movieScreenplayAuthorsLinkingWorker = movieScreenplayAuthorsLinkingWorker;
		this.movieStoryAuthorsLinkingWorker = movieStoryAuthorsLinkingWorker;
		this.movieDirectorsLinkingWorker = movieDirectorsLinkingWorker;
		this.movieProducersLinkingWorker = movieProducersLinkingWorker;
	}

	@Override
	public void link(Page source, Movie baseEntity) {
		MovieLinkedTitlesDTO movieLinkedTitlesDTO;
		try {
			List<PageSection> creditsPageSectionList = movieClosingCreditsProcessor.process(source);
			movieLinkedTitlesDTO = movieLinkedTitlesProcessor.process(creditsPageSectionList);
		} catch (Exception e) {
			return;
		}

		movieWritersLinkingWorker.link(movieLinkedTitlesDTO.getWriters(), baseEntity);
		movieScreenplayAuthorsLinkingWorker.link(movieLinkedTitlesDTO.getScreenplayAuthors(), baseEntity);
		movieStoryAuthorsLinkingWorker.link(movieLinkedTitlesDTO.getStoryAuthors(), baseEntity);
		movieDirectorsLinkingWorker.link(movieLinkedTitlesDTO.getDirectors(), baseEntity);
		movieProducersLinkingWorker.link(movieLinkedTitlesDTO.getProducers(), baseEntity);
	}

}
