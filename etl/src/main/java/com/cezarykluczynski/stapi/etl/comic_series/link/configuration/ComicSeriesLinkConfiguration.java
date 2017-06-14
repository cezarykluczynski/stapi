package com.cezarykluczynski.stapi.etl.comic_series.link.configuration;

import com.cezarykluczynski.stapi.etl.comic_series.link.processor.ComicSeriesLinkReader;
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepsProperties;
import com.cezarykluczynski.stapi.model.comic_series.repository.ComicSeriesRepository;
import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import javax.inject.Inject;

@Configuration
public class ComicSeriesLinkConfiguration {

	@Inject
	private ComicSeriesRepository comicSeriesRepository;

	@Inject
	private StepsProperties stepsProperties;

	@Bean
	public ComicSeriesLinkReader comicSeriesLinkReader() throws Exception {
		ComicSeriesLinkReader comicSeriesLinkReader = new ComicSeriesLinkReader();
		comicSeriesLinkReader.setPageSize(stepsProperties.getLinkComicSeries().getCommitInterval());
		comicSeriesLinkReader.setRepository(comicSeriesRepository);
		comicSeriesLinkReader.setSort(ImmutableMap.of("id", Sort.Direction.ASC));
		comicSeriesLinkReader.setMethodName("findAll");
		comicSeriesLinkReader.afterPropertiesSet();
		return comicSeriesLinkReader;
	}

}
