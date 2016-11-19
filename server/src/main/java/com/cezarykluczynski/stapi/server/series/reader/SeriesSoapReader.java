package com.cezarykluczynski.stapi.server.series.reader;

import com.cezarykluczynski.stapi.client.v1.soap.SeriesRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesResponse;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesSoapMapper;
import com.cezarykluczynski.stapi.server.series.query.SeriesSoapQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SeriesSoapReader implements Reader<SeriesRequest, SeriesResponse> {

	private SeriesSoapQuery seriesSoapQuery;

	private SeriesSoapMapper seriesSoapMapper;

	private PageMapper pageMapper;

	@Inject
	public SeriesSoapReader(SeriesSoapQuery seriesSoapQuery, SeriesSoapMapper seriesSoapMapper, PageMapper pageMapper) {
		this.seriesSoapQuery = seriesSoapQuery;
		this.seriesSoapMapper = seriesSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public SeriesResponse read(SeriesRequest input) {
		Page<Series> seriesPage = seriesSoapQuery.query(input);
		SeriesResponse seriesResponse = new SeriesResponse();
		seriesResponse.setPage(pageMapper.fromPageToSoapResponsePage(seriesPage));
		seriesResponse.getSeries().addAll(seriesSoapMapper.map(seriesPage.getContent()));
		return seriesResponse;
	}

}
