package com.cezarykluczynski.stapi.model.season.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.season.dto.SeasonRequestDTO;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.season.entity.Season_;
import com.cezarykluczynski.stapi.model.season.query.SeasonQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class SeasonRepositoryImpl implements SeasonRepositoryCustom {

	private final SeasonQueryBuilderFactory seasonQueryBuilderFactory;

	@Inject
	public SeasonRepositoryImpl(SeasonQueryBuilderFactory seasonQueryBuilderFactory) {
		this.seasonQueryBuilderFactory = seasonQueryBuilderFactory;
	}

	@Override
	public Page<Season> findMatching(SeasonRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Season> seasonQueryBuilder = seasonQueryBuilderFactory.createQueryBuilder(pageable);

		seasonQueryBuilder.equal(Season_.uid, criteria.getUid());
		seasonQueryBuilder.like(Season_.title, criteria.getTitle());
		seasonQueryBuilder.between(Season_.numberOfEpisodes, criteria.getNumberOfEpisodesFrom(), criteria.getNumberOfEpisodesTo());
		seasonQueryBuilder.between(Season_.seasonNumber, criteria.getSeasonNumberFrom(), criteria.getSeasonNumberTo());
		seasonQueryBuilder.setSort(criteria.getSort());

		return seasonQueryBuilder.findPage();
	}

}
