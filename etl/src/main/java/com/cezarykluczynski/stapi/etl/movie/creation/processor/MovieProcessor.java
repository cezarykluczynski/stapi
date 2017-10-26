package com.cezarykluczynski.stapi.etl.movie.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.movie.processor.MovieTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class MovieProcessor extends CompositeItemProcessor<PageHeader, Movie> {

	public MovieProcessor(PageHeaderProcessor pageHeaderProcessor, MovieTemplatePageProcessor movieTemplatePageProcessor,
			MovieTemplateToMovieProcessor movieTemplateToMovieProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, movieTemplatePageProcessor, movieTemplateToMovieProcessor));
	}

}
