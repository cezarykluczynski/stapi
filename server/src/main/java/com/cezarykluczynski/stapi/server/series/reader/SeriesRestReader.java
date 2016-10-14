package com.cezarykluczynski.stapi.server.series.reader;

import com.cezarykluczynski.stapi.client.rest.model.Series;
import com.cezarykluczynski.stapi.server.common.reader.ListReader;
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesRestMapper;
import com.cezarykluczynski.stapi.server.series.query.SeriesQueryBuilder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class SeriesRestReader implements ListReader<SeriesRestBeanParams, Series> {

	private SeriesQueryBuilder seriesQueryBuilder;

	private SeriesRestMapper seriesRestMapper;

	@Inject
	public SeriesRestReader(SeriesQueryBuilder seriesQueryBuilder, SeriesRestMapper seriesRestMapper) {
		this.seriesQueryBuilder = seriesQueryBuilder;
		this.seriesRestMapper = seriesRestMapper;
	}

	@Override
	public List<Series> read(SeriesRestBeanParams seriesRestBeanParams) {
		return seriesRestMapper.map(seriesQueryBuilder.query(seriesRestBeanParams));
	}

}
