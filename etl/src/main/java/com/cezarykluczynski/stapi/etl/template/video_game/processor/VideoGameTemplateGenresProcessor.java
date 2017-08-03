package com.cezarykluczynski.stapi.etl.template.video_game.processor;

import com.cezarykluczynski.stapi.etl.template.common.factory.GenreFactory;
import com.cezarykluczynski.stapi.model.genre.entity.Genre;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VideoGameTemplateGenresProcessor implements ItemProcessor<String, Set<Genre>> {

	private final GenreFactory genreFactory;

	public VideoGameTemplateGenresProcessor(GenreFactory genreFactory) {
		this.genreFactory = genreFactory;
	}

	@Override
	public Set<Genre> process(String item) throws Exception {
		return Lists.newArrayList(StringUtils.split(item, ","))
				.stream()
				.map(StringUtils::trimToNull)
				.filter(Objects::nonNull)
				.map(genreFactory::createForName)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
	}

}
