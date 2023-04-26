package com.cezarykluczynski.stapi.model.video_release.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.video_release.dto.VideoReleaseRequestDTO;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease_;
import com.cezarykluczynski.stapi.model.video_release.query.VideoReleaseQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class VideoReleaseRepositoryImpl implements VideoReleaseRepositoryCustom {

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
		videoReleaseQueryBuilder.equal(VideoRelease_.documentary, criteria.getDocumentary());
		videoReleaseQueryBuilder.equal(VideoRelease_.specialFeatures, criteria.getSpecialFeatures());
		videoReleaseQueryBuilder.setSort(criteria.getSort());
		videoReleaseQueryBuilder.fetch(VideoRelease_.series, doFetch);
		videoReleaseQueryBuilder.fetch(VideoRelease_.seasons, doFetch);
		videoReleaseQueryBuilder.fetch(VideoRelease_.movies, doFetch);
		videoReleaseQueryBuilder.divideQueries();
		videoReleaseQueryBuilder.fetch(VideoRelease_.references, doFetch);
		videoReleaseQueryBuilder.fetch(VideoRelease_.ratings, doFetch);
		videoReleaseQueryBuilder.divideQueries();
		videoReleaseQueryBuilder.fetch(VideoRelease_.languages, doFetch);
		videoReleaseQueryBuilder.fetch(VideoRelease_.languagesSubtitles, doFetch);
		videoReleaseQueryBuilder.fetch(VideoRelease_.languagesDubbed, doFetch);

		return videoReleaseQueryBuilder.findPage();
	}

}
