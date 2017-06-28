package com.cezarykluczynski.stapi.server.season.reader;

import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullResponse;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.season.mapper.SeasonBaseSoapMapper;
import com.cezarykluczynski.stapi.server.season.mapper.SeasonFullSoapMapper;
import com.cezarykluczynski.stapi.server.season.query.SeasonSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SeasonSoapReader implements BaseReader<SeasonBaseRequest, SeasonBaseResponse>,
		FullReader<SeasonFullRequest, SeasonFullResponse> {

	private final SeasonSoapQuery seasonSoapQuery;

	private final SeasonBaseSoapMapper seasonBaseSoapMapper;

	private final SeasonFullSoapMapper seasonFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public SeasonSoapReader(SeasonSoapQuery seasonSoapQuery, SeasonBaseSoapMapper seasonBaseSoapMapper, SeasonFullSoapMapper seasonFullSoapMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.seasonSoapQuery = seasonSoapQuery;
		this.seasonBaseSoapMapper = seasonBaseSoapMapper;
		this.seasonFullSoapMapper = seasonFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public SeasonBaseResponse readBase(SeasonBaseRequest input) {
		Page<Season> seasonPage = seasonSoapQuery.query(input);
		SeasonBaseResponse seasonResponse = new SeasonBaseResponse();
		seasonResponse.setPage(pageMapper.fromPageToSoapResponsePage(seasonPage));
		seasonResponse.setSort(sortMapper.map(input.getSort()));
		seasonResponse.getSeasons().addAll(seasonBaseSoapMapper.mapBase(seasonPage.getContent()));
		return seasonResponse;
	}

	@Override
	public SeasonFullResponse readFull(SeasonFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Season> seasonPage = seasonSoapQuery.query(input);
		SeasonFullResponse seasonFullResponse = new SeasonFullResponse();
		seasonFullResponse.setSeason(seasonFullSoapMapper.mapFull(Iterables.getOnlyElement(seasonPage.getContent(), null)));
		return seasonFullResponse;
	}

}
