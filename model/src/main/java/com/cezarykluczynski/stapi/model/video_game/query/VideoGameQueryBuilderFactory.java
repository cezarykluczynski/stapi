package com.cezarykluczynski.stapi.model.video_game.query;

import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class VideoGameQueryBuilderFactory extends AbstractQueryBuilderFactory<VideoGame> {

	public VideoGameQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, VideoGame.class);
	}

}
