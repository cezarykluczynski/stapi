package com.cezarykluczynski.stapi.etl.template.movie.linker;

import com.cezarykluczynski.stapi.etl.common.processor.LinkingWorker;
import com.cezarykluczynski.stapi.etl.movie.creation.dto.MovieLinkedTitlesDTO;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovieStaffLinkingWorkerComposite implements LinkingWorker<MovieLinkedTitlesDTO, Movie> {

	private final MovieWritersLinkingWorker movieWritersLinkingWorker;

	private final MovieScreenplayAuthorsLinkingWorker movieScreenplayAuthorsLinkingWorker;

	private final MovieStoryAuthorsLinkingWorker movieStoryAuthorsLinkingWorker;

	private final MovieDirectorsLinkingWorker movieDirectorsLinkingWorker;

	private final MovieProducersLinkingWorker movieProducersLinkingWorker;

	private final MovieStaffLinkingWorker movieStaffLinkingWorker;

	public MovieStaffLinkingWorkerComposite(MovieWritersLinkingWorker movieWritersLinkingWorker,
	MovieScreenplayAuthorsLinkingWorker movieScreenplayAuthorsLinkingWorker,
	MovieStoryAuthorsLinkingWorker movieStoryAuthorsLinkingWorker,
	MovieDirectorsLinkingWorker movieDirectorsLinkingWorker,
	MovieProducersLinkingWorker movieProducersLinkingWorker,
	MovieStaffLinkingWorker movieStaffLinkingWorker) {
		this.movieWritersLinkingWorker = movieWritersLinkingWorker;
		this.movieScreenplayAuthorsLinkingWorker = movieScreenplayAuthorsLinkingWorker;
		this.movieStoryAuthorsLinkingWorker = movieStoryAuthorsLinkingWorker;
		this.movieDirectorsLinkingWorker = movieDirectorsLinkingWorker;
		this.movieProducersLinkingWorker = movieProducersLinkingWorker;
		this.movieStaffLinkingWorker = movieStaffLinkingWorker;
	}

	@Override
	public void link(MovieLinkedTitlesDTO source, Movie baseEntity) {
		movieWritersLinkingWorker.link(source.getWriters(), baseEntity);
		movieScreenplayAuthorsLinkingWorker.link(source.getScreenplayAuthors(), baseEntity);
		movieStoryAuthorsLinkingWorker.link(source.getStoryAuthors(), baseEntity);
		movieDirectorsLinkingWorker.link(source.getDirectors(), baseEntity);
		movieProducersLinkingWorker.link(source.getProducers(), baseEntity);
		movieStaffLinkingWorker.link(source.getStaff(), baseEntity);
	}
}
