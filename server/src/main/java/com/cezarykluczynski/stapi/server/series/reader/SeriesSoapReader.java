package com.cezarykluczynski.stapi.server.series.reader;

import com.cezarykluczynski.stapi.client.soap.SeriesRequest;
import com.cezarykluczynski.stapi.client.soap.SeriesResponse;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.server.common.reader.SoapReader;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesSoapMapper;
import com.cezarykluczynski.stapi.server.series.query.SeriesQueryBuilder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class SeriesSoapReader implements SoapReader<SeriesRequest, SeriesResponse> {

	private SeriesQueryBuilder seriesQueryBuilder;

	private SeriesSoapMapper seriesSoapMapper;

	@Inject
	public SeriesSoapReader(SeriesQueryBuilder seriesQueryBuilder, SeriesSoapMapper seriesSoapMapper) {
		this.seriesQueryBuilder = seriesQueryBuilder;
		this.seriesSoapMapper = seriesSoapMapper;
	}

	@Override
	public SeriesResponse read(SeriesRequest seriesRequest) {
		List<Series> seriesList = seriesQueryBuilder.query(seriesRequest);
		SeriesResponse seriesResponse = new SeriesResponse();
		seriesResponse.getSeries().addAll(seriesSoapMapper.map(seriesList));
		return seriesResponse;
	}

}
