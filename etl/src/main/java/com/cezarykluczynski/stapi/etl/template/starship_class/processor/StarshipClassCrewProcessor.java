package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarshipClassCrewProcessor implements ItemProcessor<String, String> {

	private final WikitextApi wikitextApi;

	@Override
	public String process(String value) throws Exception {
		if (StringUtils.isNotBlank(value)) {
			if (value.contains("\"comment\"")) {
				return null;
			}
			String crew = StringUtils.replaceAll(value, "<br\\s?/?>", "; ");
			crew = wikitextApi.getWikitextWithoutLinks(crew);
			crew = crew.replaceAll("\'\'", "").replaceAll("&asymp;", "≈").replaceAll("&ndash;", "–");
			crew = StringUtils.normalizeSpace(crew);
			if (!crew.equals(value)) {
				log.info("Crew replacement: \"{}\" with \"{}\".", value, crew);
			}
			return crew;
		}
		return null;
	}

}
