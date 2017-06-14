package com.cezarykluczynski.stapi.etl.book_series.link.configuration;


import com.cezarykluczynski.stapi.etl.book_series.link.processor.BookSeriesLinkReader;
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepsProperties;
import com.cezarykluczynski.stapi.model.book_series.repository.BookSeriesRepository;
import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import javax.inject.Inject;

@Configuration
public class BookSeriesLinkConfiguration {

	@Inject
	private BookSeriesRepository bookSeriesRepository;

	@Inject
	private StepsProperties stepsProperties;

	@Bean
	public BookSeriesLinkReader bookSeriesLinkReader() throws Exception {
		BookSeriesLinkReader bookSeriesLinkReader = new BookSeriesLinkReader();
		bookSeriesLinkReader.setPageSize(stepsProperties.getLinkBookSeries().getCommitInterval());
		bookSeriesLinkReader.setRepository(bookSeriesRepository);
		bookSeriesLinkReader.setSort(ImmutableMap.of("id", Sort.Direction.ASC));
		bookSeriesLinkReader.setMethodName("findAll");
		bookSeriesLinkReader.afterPropertiesSet();
		return bookSeriesLinkReader;
	}

}
