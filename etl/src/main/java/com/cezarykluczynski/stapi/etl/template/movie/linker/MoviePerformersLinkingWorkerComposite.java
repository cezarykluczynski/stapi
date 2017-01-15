package com.cezarykluczynski.stapi.etl.template.movie.linker;

import com.cezarykluczynski.stapi.etl.common.processor.LinkingWorker;
import com.cezarykluczynski.stapi.etl.movie.creation.dto.MovieLinkedTitlesDTO;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class MoviePerformersLinkingWorkerComposite implements LinkingWorker<MovieLinkedTitlesDTO, Movie> {

	private MoviePerformersCharactersLinkingWorker moviePerformersCharactersLinkingWorker;

	private MovieStuntPerformersLinkingWorker movieStuntPerformersLinkingWorker;

	private MovieStandInPerformersLinkingWorker movieStandInPerformersLinkingWorker;

	@Inject
	public MoviePerformersLinkingWorkerComposite(MoviePerformersCharactersLinkingWorker moviePerformersCharactersLinkingWorker,
			MovieStuntPerformersLinkingWorker movieStuntPerformersLinkingWorker,
			MovieStandInPerformersLinkingWorker movieStandInPerformersLinkingWorker) {
		this.moviePerformersCharactersLinkingWorker = moviePerformersCharactersLinkingWorker;
		this.movieStuntPerformersLinkingWorker = movieStuntPerformersLinkingWorker;
		this.movieStandInPerformersLinkingWorker = movieStandInPerformersLinkingWorker;
	}

	@Override
	public void link(MovieLinkedTitlesDTO source, Movie baseEntity) {
		moviePerformersCharactersLinkingWorker.link(source.getPerformers(), baseEntity);
		movieStuntPerformersLinkingWorker.link(source.getStuntPerformers(), baseEntity);
		movieStandInPerformersLinkingWorker.link(source.getStandInPerformers(), baseEntity);
	}
}
