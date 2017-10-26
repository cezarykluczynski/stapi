package com.cezarykluczynski.stapi.etl.template.hologram.processor;

import com.cezarykluczynski.stapi.etl.template.common.processor.DateStatusProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class HologramActivationDateProcessor implements ItemProcessor<String, String> {

	private final WikitextApi wikitextApi;

	private final DateStatusProcessor dateStatusProcessor;

	public HologramActivationDateProcessor(WikitextApi wikitextApi, DateStatusProcessor dateStatusProcessor) {
		this.wikitextApi = wikitextApi;
		this.dateStatusProcessor = dateStatusProcessor;
	}

	@Override
	public String process(String item) throws Exception {
		return wikitextApi.getPageTitlesFromWikitext(item)
				.stream()
				.map(this::processDateStatus)
				.filter(Objects::nonNull)
				.findFirst().orElse(null);
	}

	private String processDateStatus(String s) {
		try {
			return dateStatusProcessor.process(s);
		} catch (Exception e) {
			log.warn("Exception thrown by DateStatusProcessor", e);
			return null;
		}
	}

}
