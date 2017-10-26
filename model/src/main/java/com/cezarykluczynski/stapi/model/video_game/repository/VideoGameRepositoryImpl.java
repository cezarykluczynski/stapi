package com.cezarykluczynski.stapi.model.video_game.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.cezarykluczynski.stapi.model.video_game.dto.VideoGameRequestDTO;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame_;
import com.cezarykluczynski.stapi.model.video_game.query.VideoGameQueryBuilderFactory;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class VideoGameRepositoryImpl extends AbstractRepositoryImpl<VideoGame> implements VideoGameRepositoryCustom {

	private final VideoGameQueryBuilderFactory videoGameQueryBuilderFactory;

	public VideoGameRepositoryImpl(VideoGameQueryBuilderFactory videoGameQueryBuilderFactory) {
		this.videoGameQueryBuilderFactory = videoGameQueryBuilderFactory;
	}

	@Override
	public Page<VideoGame> findMatching(VideoGameRequestDTO criteria, Pageable pageable) {
		QueryBuilder<VideoGame> videoGameQueryBuilder = videoGameQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		videoGameQueryBuilder.equal(VideoGame_.uid, uid);
		videoGameQueryBuilder.like(VideoGame_.title, criteria.getTitle());
		videoGameQueryBuilder.between(VideoGame_.releaseDate, criteria.getReleaseDateFrom(), criteria.getReleaseDateTo());
		videoGameQueryBuilder.setSort(criteria.getSort());
		videoGameQueryBuilder.fetch(VideoGame_.publishers, doFetch);
		videoGameQueryBuilder.fetch(VideoGame_.developers, doFetch);
		videoGameQueryBuilder.fetch(VideoGame_.platforms, doFetch);
		videoGameQueryBuilder.fetch(VideoGame_.genres, doFetch);
		videoGameQueryBuilder.fetch(VideoGame_.ratings, doFetch);
		videoGameQueryBuilder.fetch(VideoGame_.references, doFetch);

		Page<VideoGame> videoGamePage = videoGameQueryBuilder.findPage();
		clearProxies(videoGamePage, !doFetch);
		return videoGamePage;
	}

	@Override
	protected void clearProxies(Page<VideoGame> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(videoGame -> {
			videoGame.setPublishers(Sets.newHashSet());
			videoGame.setDevelopers(Sets.newHashSet());
			videoGame.setPlatforms(Sets.newHashSet());
			videoGame.setGenres(Sets.newHashSet());
			videoGame.setRatings(Sets.newHashSet());
			videoGame.setReferences(Sets.newHashSet());
		});
	}

}
