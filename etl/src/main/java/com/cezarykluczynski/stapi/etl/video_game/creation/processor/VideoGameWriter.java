package com.cezarykluczynski.stapi.etl.video_game.creation.processor;

import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import com.cezarykluczynski.stapi.model.video_game.repository.VideoGameRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class VideoGameWriter implements ItemWriter<VideoGame> {

	private final VideoGameRepository videoGameRepository;

	public VideoGameWriter(VideoGameRepository videoGameRepository) {
		this.videoGameRepository = videoGameRepository;
	}

	@Override
	public void write(Chunk<? extends VideoGame> items) throws Exception {
		videoGameRepository.saveAll(items.getItems());
	}

}
