package com.cezarykluczynski.stapi.etl.movie.creation.processor;

import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class MovieTemplateToMovieProcessor implements ItemProcessor<MovieTemplate, Movie> {

	private final UidGenerator uidGenerator;

	public MovieTemplateToMovieProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public Movie process(MovieTemplate item) throws Exception {
		Movie movie = item.getMovieStub();

		movie.setPage(item.getPage());
		movie.setTitle(item.getTitle());
		movie.setUid(uidGenerator.generateFromPage(item.getPage(), Movie.class));
		movie.setTitle(item.getTitle());
		movie.setTitleBulgarian(item.getTitleBulgarian());
		movie.setTitleCatalan(item.getTitleCatalan());
		movie.setTitleChineseTraditional(item.getTitleChineseTraditional());
		movie.setTitleGerman(item.getTitleGerman());
		movie.setTitleItalian(item.getTitleItalian());
		movie.setTitleJapanese(item.getTitleJapanese());
		movie.setTitlePolish(item.getTitlePolish());
		movie.setTitleRussian(item.getTitleRussian());
		movie.setTitleSerbian(item.getTitleSerbian());
		movie.setTitleSpanish(item.getTitleSpanish());
		movie.setStardateFrom(item.getStardateFrom());
		movie.setStardateTo(item.getStardateTo());
		movie.setYearFrom(item.getYearFrom());
		movie.setYearTo(item.getYearTo());
		movie.setUsReleaseDate(item.getUsReleaseDate());

		return movie;
	}
}

