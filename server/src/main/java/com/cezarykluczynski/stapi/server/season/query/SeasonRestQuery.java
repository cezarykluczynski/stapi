package com.cezarykluczynski.stapi.server.season.query;

import com.cezarykluczynski.stapi.model.season.dto.SeasonRequestDTO;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.season.dto.SeasonRestBeanParams;
import com.cezarykluczynski.stapi.server.season.mapper.SeasonBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SeasonRestQuery {

	private final SeasonBaseRestMapper seasonBaseRestMapper;

	private final PageMapper pageMapper;

	private final SeasonRepository seasonRepository;

	public SeasonRestQuery(SeasonBaseRestMapper seasonBaseRestMapper, PageMapper pageMapper, SeasonRepository seasonRepository) {
		this.seasonBaseRestMapper = seasonBaseRestMapper;
		this.pageMapper = pageMapper;
		this.seasonRepository = seasonRepository;
	}

	public Page<Season> query(SeasonRestBeanParams seasonRestBeanParams) {
		SeasonRequestDTO seasonRequestDTO = seasonBaseRestMapper.mapBase(seasonRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(seasonRestBeanParams);
		return seasonRepository.findMatching(seasonRequestDTO, pageRequest);
	}

}
