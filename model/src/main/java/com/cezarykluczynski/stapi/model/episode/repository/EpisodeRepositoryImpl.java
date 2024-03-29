package com.cezarykluczynski.stapi.model.episode.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.episode.entity.Episode_;
import com.cezarykluczynski.stapi.model.episode.query.EpisodeQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class EpisodeRepositoryImpl implements EpisodeRepositoryCustom {

	private final EpisodeQueryBuilderFactory episodeQueryBuilderFactory;

	public EpisodeRepositoryImpl(EpisodeQueryBuilderFactory episodeQueryBuilderFactory) {
		this.episodeQueryBuilderFactory = episodeQueryBuilderFactory;
	}

	@Override
	public Page<Episode> findMatching(EpisodeRequestDTO criteria, Pageable pageable) {
		QueryBuilder<Episode> episodeQueryBuilder = episodeQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		episodeQueryBuilder.equal(Episode_.uid, uid);
		episodeQueryBuilder.like(Episode_.title, criteria.getTitle());
		episodeQueryBuilder.like(Episode_.productionSerialNumber, criteria.getProductionSerialNumber());
		episodeQueryBuilder.between(Episode_.seasonNumber, criteria.getSeasonNumberFrom(), criteria.getSeasonNumberTo());
		episodeQueryBuilder.between(Episode_.episodeNumber, criteria.getEpisodeNumberFrom(), criteria.getEpisodeNumberTo());
		episodeQueryBuilder.between(Episode_.yearFrom, criteria.getYearFrom(), null);
		episodeQueryBuilder.between(Episode_.yearTo, null, criteria.getYearTo());
		episodeQueryBuilder.between(Episode_.stardateFrom, criteria.getStardateFrom(), null);
		episodeQueryBuilder.between(Episode_.stardateTo, null, criteria.getStardateTo());
		episodeQueryBuilder.equal(Episode_.featureLength, criteria.getFeatureLength());
		episodeQueryBuilder.between(Episode_.usAirDate, criteria.getUsAirDateFrom(), criteria.getUsAirDateTo());
		episodeQueryBuilder.between(Episode_.finalScriptDate, criteria.getFinalScriptDateFrom(), criteria.getFinalScriptDateTo());
		episodeQueryBuilder.setSort(criteria.getSort());
		episodeQueryBuilder.fetch(Episode_.writers, doFetch);
		episodeQueryBuilder.fetch(Episode_.teleplayAuthors, doFetch);
		episodeQueryBuilder.divideQueries();
		episodeQueryBuilder.fetch(Episode_.storyAuthors, doFetch);
		episodeQueryBuilder.fetch(Episode_.directors, doFetch);
		episodeQueryBuilder.divideQueries();
		episodeQueryBuilder.fetch(Episode_.staff, doFetch);
		episodeQueryBuilder.divideQueries();
		episodeQueryBuilder.fetch(Episode_.performers, doFetch);
		episodeQueryBuilder.divideQueries();
		episodeQueryBuilder.fetch(Episode_.stuntPerformers, doFetch);
		episodeQueryBuilder.fetch(Episode_.standInPerformers, doFetch);
		episodeQueryBuilder.divideQueries();
		episodeQueryBuilder.fetch(Episode_.characters, doFetch);

		return episodeQueryBuilder.findPage();
	}

}
