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

import java.util.List;

@Repository
public class EpisodeRepositoryImpl extends AbstractRepositoryImpl<Episode> implements EpisodeRepositoryCustom {

	private final EpisodeQueryBuilderFactory episodeQueryBuilderFactory;

	public EpisodeRepositoryImpl(EpisodeQueryBuilderFactory episodeQueryBuilderFactory) {
		this.episodeQueryBuilderFactory = episodeQueryBuilderFactory;
	}

	@Override
	public Page<Episode> findMatching(EpisodeRequestDTO criteria, Pageable pageable) {
		String uid = criteria.getUid();
		boolean doFetch = uid != null;
		if (doFetch) {
			final Page<Episode> episodes = privateFindMatching(criteria, pageable, true, uid, 1);
			final List<Episode> fetch1Episodes = episodes.getContent();

			if (fetch1Episodes.isEmpty()) {
				return episodes;
			}
			final Page<Episode> fetch2 = privateFindMatching(criteria, pageable, true, uid, 2);
			final Page<Episode> fetch3 = privateFindMatching(criteria, pageable, true, uid, 3);
			final Page<Episode> fetch4 = privateFindMatching(criteria, pageable, true, uid, 4);
			final Episode episode = fetch1Episodes.get(0);

			// merging several results make things faster than doing join across 10 tables
			final Episode fetch2Episode = fetch2.getContent().get(0);
			episode.getWriters().addAll(fetch2Episode.getWriters());
			episode.getTeleplayAuthors().addAll(fetch2Episode.getTeleplayAuthors());
			episode.getStoryAuthors().addAll(fetch2Episode.getStoryAuthors());
			episode.getDirectors().addAll(fetch2Episode.getDirectors());

			final Episode fetch3Episode = fetch3.getContent().get(0);
			episode.getStaff().addAll(fetch3Episode.getStaff());
			episode.getPerformers().addAll(fetch3Episode.getPerformers());
			episode.getStuntPerformers().addAll(fetch3Episode.getStuntPerformers());

			final Episode fetch4Episode = fetch4.getContent().get(0);
			episode.getStandInPerformers().addAll(fetch4Episode.getStandInPerformers());
			episode.getCharacters().addAll(fetch4Episode.getCharacters());
			return episodes;
		} else {
			return privateFindMatching(criteria, pageable, false, null, 0);
		}
	}

	public Page<Episode> privateFindMatching(EpisodeRequestDTO criteria, Pageable pageable, boolean doFetch, String uid, int part) {
		QueryBuilder<Episode> episodeQueryBuilder = episodeQueryBuilderFactory.createQueryBuilder(pageable);

		if (doFetch) {
			episodeQueryBuilder.equal(Episode_.uid, uid);
			if (part == 1) {
				episodeQueryBuilder.fetch(Episode_.series);
				episodeQueryBuilder.fetch(Episode_.series, Series_.productionCompany, true);
				episodeQueryBuilder.fetch(Episode_.series, Series_.originalBroadcaster, true);
				episodeQueryBuilder.fetch(Episode_.season, true);
				episodeQueryBuilder.fetch(Episode_.season, Season_.series, true);
			}
			if (part == 2) {
				episodeQueryBuilder.fetch(Episode_.writers, true);
				episodeQueryBuilder.fetch(Episode_.teleplayAuthors, true);
				episodeQueryBuilder.fetch(Episode_.storyAuthors, true);
				episodeQueryBuilder.fetch(Episode_.directors, true);
			}
			if (part == 3) {
				episodeQueryBuilder.fetch(Episode_.staff, true);
				episodeQueryBuilder.fetch(Episode_.performers, true);
				episodeQueryBuilder.fetch(Episode_.stuntPerformers, true);
			}
			if (part == 4) {
				episodeQueryBuilder.fetch(Episode_.standInPerformers, true);
				episodeQueryBuilder.fetch(Episode_.characters, true);
			}
		} else {
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
		}

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
