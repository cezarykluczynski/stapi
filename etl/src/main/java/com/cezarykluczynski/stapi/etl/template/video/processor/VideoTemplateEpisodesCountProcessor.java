package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.template.video.dto.EpisodesCountDTO;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VideoTemplateEpisodesCountProcessor implements ItemProcessor<String, EpisodesCountDTO> {

	private static final String FEATURE = "feature";
	private static final String BR = "<br";
	private static final String BRACKET_LEFT = "(";
	private static final String SPACE_BRACKET_LEFT = " " + BRACKET_LEFT;

	@Override
	public EpisodesCountDTO process(String item) throws Exception {
		EpisodesCountDTO episodesCountDTO = new EpisodesCountDTO();

		if (StringUtils.isEmpty(item)) {
			return episodesCountDTO;
		}

		String subject = StringUtils.substringBefore(item, BR);
		String leftSide = StringUtils.substringBefore(subject, FEATURE);
		boolean hasBracket = leftSide.contains(BRACKET_LEFT);
		boolean hasFeatureLength = subject.contains(FEATURE);
		List<String> numbers = Lists.newArrayList(StringUtils.split(leftSide, SPACE_BRACKET_LEFT))
				.stream()
				.map(StringUtils::trim)
				.collect(Collectors.toList());

		if (numbers.size() == 1 && !hasBracket && hasFeatureLength) {
			episodesCountDTO.setNumberOfFeatureLengthEpisodes(Ints.tryParse(numbers.get(0)));
			episodesCountDTO.setNumberOfEpisodes(episodesCountDTO.getNumberOfFeatureLengthEpisodes());
		} else if (numbers.size() == 1 && hasBracket || numbers.size() == 1 && !hasBracket && !hasFeatureLength) {
			episodesCountDTO.setNumberOfEpisodes(Ints.tryParse(numbers.get(0)));
		} else if (numbers.size() >= 2) {
			episodesCountDTO.setNumberOfEpisodes(Ints.tryParse(numbers.get(0)));

			if (hasFeatureLength) {
				episodesCountDTO.setNumberOfFeatureLengthEpisodes(Ints.tryParse(numbers.get(1)));
			}
		}

		if (episodesCountDTO.getNumberOfEpisodes() != null && episodesCountDTO.getNumberOfFeatureLengthEpisodes() == null) {
			episodesCountDTO.setNumberOfFeatureLengthEpisodes(0);
		}

		if (episodesCountDTO.getNumberOfEpisodes() == null && episodesCountDTO.getNumberOfFeatureLengthEpisodes() == null) {
			log.info("EpisodesCountDTO candidate not parsed: \"{}\"", item);
		}

		return episodesCountDTO;
	}

}
