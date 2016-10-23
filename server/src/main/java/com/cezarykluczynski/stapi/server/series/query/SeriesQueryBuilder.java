package com.cezarykluczynski.stapi.server.series.query;

import com.cezarykluczynski.stapi.client.soap.SeriesRequest;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SeriesQueryBuilder {

	private SeriesRepository seriesRepository;

	private PageMapper pageMapper;

	@Inject
	public SeriesQueryBuilder(SeriesRepository seriesRepository, PageMapper pageMapper) {
		this.seriesRepository = seriesRepository;
		this.pageMapper = pageMapper;
	}

	public Page<Series> query(SeriesRequest seriesRequest) {
		PageRequest pageRequest = pageMapper.toPageRequest(seriesRequest.getPage());
		return queryWithTitleAndPageRequest(seriesRequest.getTitle(), pageRequest);
	}

	public Page<Series> query(SeriesRestBeanParams seriesRestBeanParams) {
		return queryWithTitleAndPageRequest(seriesRestBeanParams.getTitle(), null);
	}

	private Page<Series> queryWithTitleAndPageRequest(String title, PageRequest pageRequest) {
		if (StringUtils.isEmpty(title)) {
			return seriesRepository.findAll(pageRequest);
		} else {
			return seriesRepository.findByTitleIgnoreCaseContaining(title, pageRequest);
		}
	}

}
