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

import java.util.List;

@Service
@Slf4j
public class MovieRealPeopleLinkingWorkerComposite implements LinkingWorker<Page, Movie> {

	private final MovieClosingCreditsProcessor movieClosingCreditsProcessor;

	private final MovieLinkedTitlesProcessor movieLinkedTitlesProcessor;

	private final MovieStaffLinkingWorkerComposite movieStaffLinkingWorkerComposite;

	private final MoviePerformersLinkingWorkerComposite moviePerformersLinkingWorkerComposite;

	public MovieRealPeopleLinkingWorkerComposite(MovieClosingCreditsProcessor movieClosingCreditsProcessor,
			MovieLinkedTitlesProcessor movieLinkedTitlesProcessor, MovieStaffLinkingWorkerComposite movieStaffLinkingWorkerComposite,
			MoviePerformersLinkingWorkerComposite moviePerformersLinkingWorkerComposite) {
		this.movieClosingCreditsProcessor = movieClosingCreditsProcessor;
		this.movieLinkedTitlesProcessor = movieLinkedTitlesProcessor;
		this.movieStaffLinkingWorkerComposite = movieStaffLinkingWorkerComposite;
		this.moviePerformersLinkingWorkerComposite = moviePerformersLinkingWorkerComposite;
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

		movieStaffLinkingWorkerComposite.link(movieLinkedTitlesDTO, baseEntity);
		moviePerformersLinkingWorkerComposite.link(movieLinkedTitlesDTO, baseEntity);
	}

}
