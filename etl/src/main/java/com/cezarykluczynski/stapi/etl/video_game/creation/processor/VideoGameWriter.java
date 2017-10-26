package com.cezarykluczynski.stapi.etl.video_game.creation.processor;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import com.cezarykluczynski.stapi.model.video_game.repository.VideoGameRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoGameWriter implements ItemWriter<VideoGame> {

	private final VideoGameRepository videoGameRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public VideoGameWriter(VideoGameRepository videoGameRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.videoGameRepository = videoGameRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends VideoGame> items) throws Exception {
		videoGameRepository.save(process(items));
	}

	private List<VideoGame> process(List<? extends VideoGame> videoGameList) {
		List<VideoGame> comicsListWithoutExtends = fromExtendsListToVideoGameList(videoGameList);
		return filterDuplicates(comicsListWithoutExtends);
	}

	private List<VideoGame> fromExtendsListToVideoGameList(List<? extends VideoGame> videoGames) {
		return videoGames
				.stream()
				.map(pageAware -> (VideoGame) pageAware)
				.collect(Collectors.toList());
	}

	private List<VideoGame> filterDuplicates(List<VideoGame> videoGameList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(videoGameList.stream()
				.map(videoGame -> (PageAware) videoGame)
				.collect(Collectors.toList()), VideoGame.class).stream()
				.map(pageAware -> (VideoGame) pageAware)
				.collect(Collectors.toList());
	}

}
