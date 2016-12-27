package com.cezarykluczynski.stapi.etl.template.common.linker;

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
public class MovieRealPeopleLinkingWorker implements LinkingWorker<Page, Movie> {

	private MovieClosingCreditsProcessor movieClosingCreditsProcessor;

	private MovieLinkedTitlesProcessor movieLinkedTitlesProcessor;

	@Inject
	public MovieRealPeopleLinkingWorker(MovieClosingCreditsProcessor movieClosingCreditsProcessor,
			MovieLinkedTitlesProcessor movieLinkedTitlesProcessor) {
		this.movieClosingCreditsProcessor = movieClosingCreditsProcessor;
		this.movieLinkedTitlesProcessor = movieLinkedTitlesProcessor;
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

		// TODO
	}

}
