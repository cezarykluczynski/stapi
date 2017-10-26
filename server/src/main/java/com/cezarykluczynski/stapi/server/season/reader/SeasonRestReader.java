package com.cezarykluczynski.stapi.server.season.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonFullResponse;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.season.dto.SeasonRestBeanParams;
import com.cezarykluczynski.stapi.server.season.mapper.SeasonBaseRestMapper;
import com.cezarykluczynski.stapi.server.season.mapper.SeasonFullRestMapper;
import com.cezarykluczynski.stapi.server.season.query.SeasonRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SeasonRestReader implements BaseReader<SeasonRestBeanParams, SeasonBaseResponse>, FullReader<String, SeasonFullResponse> {

	private final SeasonRestQuery seasonRestQuery;

	private final SeasonBaseRestMapper seasonBaseRestMapper;

	private final SeasonFullRestMapper seasonFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public SeasonRestReader(SeasonRestQuery seasonRestQuery, SeasonBaseRestMapper seasonBaseRestMapper,
			SeasonFullRestMapper seasonFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.seasonRestQuery = seasonRestQuery;
		this.seasonBaseRestMapper = seasonBaseRestMapper;
		this.seasonFullRestMapper = seasonFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public SeasonBaseResponse readBase(SeasonRestBeanParams input) {
		Page<Season> seasonPage = seasonRestQuery.query(input);
		SeasonBaseResponse seasonResponse = new SeasonBaseResponse();
		seasonResponse.setPage(pageMapper.fromPageToRestResponsePage(seasonPage));
		seasonResponse.setSort(sortMapper.map(input.getSort()));
		seasonResponse.getSeasons().addAll(seasonBaseRestMapper.mapBase(seasonPage.getContent()));
		return seasonResponse;
	}

	@Override
	public SeasonFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		SeasonRestBeanParams seasonRestBeanParams = new SeasonRestBeanParams();
		seasonRestBeanParams.setUid(uid);
		Page<Season> seasonPage = seasonRestQuery.query(seasonRestBeanParams);
		SeasonFullResponse seasonResponse = new SeasonFullResponse();
		seasonResponse.setSeason(seasonFullRestMapper.mapFull(Iterables.getOnlyElement(seasonPage.getContent(), null)));
		return seasonResponse;
	}
}
