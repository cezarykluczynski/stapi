package com.cezarykluczynski.stapi.server.season.query;

import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullRequest;
import com.cezarykluczynski.stapi.model.season.dto.SeasonRequestDTO;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.season.mapper.SeasonBaseSoapMapper;
import com.cezarykluczynski.stapi.server.season.mapper.SeasonFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SeasonSoapQuery {

	private final SeasonBaseSoapMapper seasonBaseSoapMapper;

	private final SeasonFullSoapMapper seasonFullSoapMapper;

	private final PageMapper pageMapper;

	private final SeasonRepository seasonRepository;

	public SeasonSoapQuery(SeasonBaseSoapMapper seasonBaseSoapMapper, SeasonFullSoapMapper seasonFullSoapMapper, PageMapper pageMapper,
			SeasonRepository seasonRepository) {
		this.seasonBaseSoapMapper = seasonBaseSoapMapper;
		this.seasonFullSoapMapper = seasonFullSoapMapper;
		this.pageMapper = pageMapper;
		this.seasonRepository = seasonRepository;
	}

	public Page<Season> query(SeasonBaseRequest seasonBaseRequest) {
		SeasonRequestDTO seasonRequestDTO = seasonBaseSoapMapper.mapBase(seasonBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(seasonBaseRequest.getPage());
		return seasonRepository.findMatching(seasonRequestDTO, pageRequest);
	}

	public Page<Season> query(SeasonFullRequest seasonFullRequest) {
		SeasonRequestDTO seriesRequestDTO = seasonFullSoapMapper.mapFull(seasonFullRequest);
		return seasonRepository.findMatching(seriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
