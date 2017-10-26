package com.cezarykluczynski.stapi.model.video_release.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class VideoReleaseQueryBuilderFactory extends AbstractQueryBuilderFactory<VideoRelease> {

	public VideoReleaseQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, VideoRelease.class);
	}

}
