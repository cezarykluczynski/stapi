package com.cezarykluczynski.stapi.model.episode.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.episode.entity.Episode_;
import com.cezarykluczynski.stapi.model.episode.query.EpisodeQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.season.entity.Season_;
import com.cezarykluczynski.stapi.model.series.entity.Series_;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EpisodeRepositoryImpl extends AbstractRepositoryImpl<Episode> implements EpisodeRepositoryCustom {

	private final EpisodeQueryBuilderFactory episodeQueryBuilderFactory;

	public EpisodeRepositoryImpl(EpisodeQueryBuilderFactory episodeQueryBuilderFactory) {
		this.episodeQueryBuilderFactory = episodeQueryBuilderFactory;
	}

	@Override
	@Transactional(readOnly = true)
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
		episodeQueryBuilder.fetch(Episode_.series);
		episodeQueryBuilder.fetch(Episode_.series, Series_.productionCompany, doFetch);
		episodeQueryBuilder.fetch(Episode_.series, Series_.originalBroadcaster, doFetch);
		episodeQueryBuilder.fetch(Episode_.season, doFetch);
		episodeQueryBuilder.fetch(Episode_.season, Season_.series, doFetch);
		episodeQueryBuilder.fetch(Episode_.writers, doFetch);
		episodeQueryBuilder.fetch(Episode_.teleplayAuthors, doFetch);
		episodeQueryBuilder.fetch(Episode_.storyAuthors, doFetch);
		episodeQueryBuilder.fetch(Episode_.directors, doFetch);
		episodeQueryBuilder.fetch(Episode_.staff, doFetch);
		episodeQueryBuilder.fetch(Episode_.performers, doFetch);
		episodeQueryBuilder.fetch(Episode_.stuntPerformers, doFetch);
		episodeQueryBuilder.fetch(Episode_.standInPerformers, doFetch);
		episodeQueryBuilder.fetch(Episode_.characters, doFetch);

		Page<Episode> episodePage = episodeQueryBuilder.findPage();
		clearProxies(episodePage, !doFetch);
		return episodePage;
	}

	@Override
	protected void clearProxies(Page<Episode> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(episode -> {
			episode.setWriters(Sets.newHashSet());
			episode.setTeleplayAuthors(Sets.newHashSet());
			episode.setStoryAuthors(Sets.newHashSet());
			episode.setDirectors(Sets.newHashSet());
			episode.setStaff(Sets.newHashSet());
			episode.setPerformers(Sets.newHashSet());
			episode.setStuntPerformers(Sets.newHashSet());
			episode.setStandInPerformers(Sets.newHashSet());
			episode.setCharacters(Sets.newHashSet());
		});
	}

}
