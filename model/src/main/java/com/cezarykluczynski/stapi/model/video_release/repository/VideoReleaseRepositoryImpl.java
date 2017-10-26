package com.cezarykluczynski.stapi.model.video_release.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.cezarykluczynski.stapi.model.season.entity.Season_;
import com.cezarykluczynski.stapi.model.series.entity.Series_;
import com.cezarykluczynski.stapi.model.video_release.dto.VideoReleaseRequestDTO;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease_;
import com.cezarykluczynski.stapi.model.video_release.query.VideoReleaseQueryBuilderFactory;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class VideoReleaseRepositoryImpl extends AbstractRepositoryImpl<VideoRelease> implements VideoReleaseRepositoryCustom {

	private final VideoReleaseQueryBuilderFactory videoReleaseQueryBuilderFactory;

	public VideoReleaseRepositoryImpl(VideoReleaseQueryBuilderFactory videoReleaseQueryBuilderFactory) {
		this.videoReleaseQueryBuilderFactory = videoReleaseQueryBuilderFactory;
	}

	@Override
	public Page<VideoRelease> findMatching(VideoReleaseRequestDTO criteria, Pageable pageable) {
		QueryBuilder<VideoRelease> videoReleaseQueryBuilder = videoReleaseQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		videoReleaseQueryBuilder.equal(VideoRelease_.uid, uid);
		videoReleaseQueryBuilder.like(VideoRelease_.title, criteria.getTitle());
		videoReleaseQueryBuilder.between(VideoRelease_.yearFrom, criteria.getYearFrom(), null);
		videoReleaseQueryBuilder.between(VideoRelease_.yearTo, null, criteria.getYearTo());
		videoReleaseQueryBuilder.between(VideoRelease_.runTime, criteria.getRunTimeFrom(), criteria.getRunTimeTo());
		videoReleaseQueryBuilder.setSort(criteria.getSort());
		videoReleaseQueryBuilder.fetch(VideoRelease_.series);
		videoReleaseQueryBuilder.fetch(VideoRelease_.series, Series_.productionCompany, true);
		videoReleaseQueryBuilder.fetch(VideoRelease_.series, Series_.originalBroadcaster, true);
		videoReleaseQueryBuilder.fetch(VideoRelease_.season);
		videoReleaseQueryBuilder.fetch(VideoRelease_.season, Season_.series, true);
		videoReleaseQueryBuilder.fetch(VideoRelease_.references, doFetch);
		videoReleaseQueryBuilder.fetch(VideoRelease_.ratings, doFetch);
		videoReleaseQueryBuilder.fetch(VideoRelease_.languages, doFetch);
		videoReleaseQueryBuilder.fetch(VideoRelease_.languagesSubtitles, doFetch);
		videoReleaseQueryBuilder.fetch(VideoRelease_.languagesDubbed, doFetch);

		Page<VideoRelease> videoReleasePage = videoReleaseQueryBuilder.findPage();
		clearProxies(videoReleasePage, !doFetch);
		return videoReleasePage;
	}

	@Override
	protected void clearProxies(Page<VideoRelease> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(videoRelease -> {
			videoRelease.setReferences(Sets.newHashSet());
			videoRelease.setRatings(Sets.newHashSet());
			videoRelease.setLanguages(Sets.newHashSet());
			videoRelease.setLanguagesSubtitles(Sets.newHashSet());
			videoRelease.setLanguagesDubbed(Sets.newHashSet());
		});
	}

}
