package com.cezarykluczynski.stapi.etl.template.video.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class VideoTemplateTitleEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, VideoTemplate>> {

	private static final Pattern YEAR_IN_BRACKETS = Pattern.compile(".*\\s\\(\\d{4}\\)$");

	@Override
	public void enrich(EnrichablePair<Page, VideoTemplate> enrichablePair) throws Exception {
		final Page page = enrichablePair.getInput();
		final VideoTemplate videoTemplate = enrichablePair.getOutput();
		final String pageTitle = page.getTitle();
		if (YEAR_IN_BRACKETS.matcher(pageTitle).matches()) {
			videoTemplate.setTitle(pageTitle);
			return;
		}
		videoTemplate.setTitle(TitleUtil.getNameFromTitle(pageTitle));
	}
}
