package com.cezarykluczynski.stapi.etl.video_game.creation.processor;

import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplate;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class VideoGameTemplateProcessor implements ItemProcessor<VideoGameTemplate, VideoGame> {

	private final UidGenerator uidGenerator;

	public VideoGameTemplateProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public VideoGame process(VideoGameTemplate item) throws Exception {
		VideoGame videoGame = new VideoGame();

		videoGame.setUid(uidGenerator.generateFromPage(item.getPage(), VideoGame.class));
		videoGame.setPage(item.getPage());
		videoGame.setTitle(item.getTitle());
		videoGame.setReleaseDate(item.getReleaseDate());
		videoGame.setStardateFrom(item.getStardateFrom());
		videoGame.setStardateTo(item.getStardateTo());
		videoGame.setYearFrom(item.getYearFrom());
		videoGame.setYearTo(item.getYearTo());
		videoGame.setSystemRequirements(item.getSystemRequirements());
		videoGame.getPublishers().addAll(item.getPublishers());
		videoGame.getDevelopers().addAll(item.getDevelopers());
		videoGame.getPlatforms().addAll(item.getPlatforms());
		videoGame.getGenres().addAll(item.getGenres());
		videoGame.getRatings().addAll(item.getRatings());
		videoGame.getReferences().addAll(item.getReferences());

		return videoGame;
	}

}
