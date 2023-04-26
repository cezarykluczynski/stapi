package com.cezarykluczynski.stapi.model.video_game.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.video_game.dto.VideoGameRequestDTO;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame_;
import com.cezarykluczynski.stapi.model.video_game.query.VideoGameQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class VideoGameRepositoryImpl implements VideoGameRepositoryCustom {

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
		videoGameQueryBuilder.divideQueries();
		videoGameQueryBuilder.fetch(VideoGame_.platforms, doFetch);
		videoGameQueryBuilder.fetch(VideoGame_.genres, doFetch);
		videoGameQueryBuilder.divideQueries();
		videoGameQueryBuilder.fetch(VideoGame_.ratings, doFetch);
		videoGameQueryBuilder.fetch(VideoGame_.references, doFetch);

		return videoGameQueryBuilder.findPage();
	}

}
