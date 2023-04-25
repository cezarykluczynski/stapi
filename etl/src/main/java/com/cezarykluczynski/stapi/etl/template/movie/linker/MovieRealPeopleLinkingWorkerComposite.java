package com.cezarykluczynski.stapi.etl.template.movie.linker;

import com.cezarykluczynski.stapi.etl.common.processor.LinkingWorker;
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.movie.creation.dto.MovieLinkedTitlesDTO;
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieClosingCreditsProcessor;
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieLinkedTitlesProcessor;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieRealPeopleLinkingWorkerComposite implements LinkingWorker<Page, Movie> {

	private final MovieClosingCreditsProcessor movieClosingCreditsProcessor;

	private final MovieLinkedTitlesProcessor movieLinkedTitlesProcessor;

	private final MovieStaffLinkingWorkerComposite movieStaffLinkingWorkerComposite;

	private final MoviePerformersLinkingWorkerComposite moviePerformersLinkingWorkerComposite;

	@Override
	public void link(Page source, Movie baseEntity) {
		MovieLinkedTitlesDTO movieLinkedTitlesDTO;
		try {
			Pair<List<PageSection>, Page> creditsPageSectionsAndPage = movieClosingCreditsProcessor.process(source);
			movieLinkedTitlesDTO = movieLinkedTitlesProcessor.process(creditsPageSectionsAndPage);
		} catch (Exception e) {
			return;
		}

		movieStaffLinkingWorkerComposite.link(movieLinkedTitlesDTO, baseEntity);
		moviePerformersLinkingWorkerComposite.link(movieLinkedTitlesDTO, baseEntity);

	}

}
