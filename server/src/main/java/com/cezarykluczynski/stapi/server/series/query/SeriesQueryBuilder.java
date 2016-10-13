package com.cezarykluczynski.stapi.server.series.query;

import com.cezarykluczynski.stapi.client.soap.SeriesRequest;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class SeriesQueryBuilder {

	private SeriesRepository seriesRepository;

	@Inject
	public SeriesQueryBuilder(SeriesRepository seriesRepository) {
		this.seriesRepository = seriesRepository;
	}

	public List<Series> query(SeriesRequest seriesRequest) {
		String title = seriesRequest.getTitle();

		if (StringUtils.isEmpty(title)) {
			return seriesRepository.findAll();
		} else {
			return seriesRepository.findByTitleIgnoreCaseContaining(title);
		}
	}

}
