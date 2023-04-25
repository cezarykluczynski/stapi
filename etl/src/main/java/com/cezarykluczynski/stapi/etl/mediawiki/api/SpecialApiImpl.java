package com.cezarykluczynski.stapi.etl.mediawiki.api;

import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.connector.bliki.BlikiConnector;
import com.cezarykluczynski.stapi.etl.mediawiki.converter.PageHeaderConverter;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecialApiImpl implements SpecialApi {

	private final BlikiConnector blikiConnector;

	private final ObjectMapper objectMapper;

	private final PageHeaderConverter pageHeaderConverter;

	@Override
	@SneakyThrows
	public List<PageHeader> getPagesTranscludingTemplate(String templateName, MediaWikiSource mediaWikiSource) {
		TranscludedInResponse transcludedInResponse;
		List<PageHeader> pageHeaders = Lists.newArrayList();
		String ticontinue = "";
		do {
			final String templateTransclusions = blikiConnector.getRawTemplateTransclusions(templateName, ticontinue, mediaWikiSource);
			transcludedInResponse = objectMapper.readValue(templateTransclusions, TranscludedInResponse.class);
			pageHeaders.addAll(getPageHeaders(transcludedInResponse, mediaWikiSource));
			ticontinue = transcludedInResponse.continuePart != null ? transcludedInResponse.continuePart.ticontinue : null;
		} while (ticontinue != null);

		return pageHeaders;
	}

	private List<PageHeader> getPageHeaders(TranscludedInResponse transcludedInResponse, MediaWikiSource mediaWikiSource) {
		final Query query = transcludedInResponse.getQuery();
		if (query != null && !query.getPages().isEmpty()) {
			return query.getPages().values().stream()
					.map(Page::getTranscludedin)
					.flatMap(Collection::stream)
					.filter(page -> page.getNs() == 0)
					.map(page -> pageHeaderConverter.fromTranscludedInPage(page, mediaWikiSource))
					.collect(Collectors.toList());

		}

		return Lists.newArrayList();
	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static final class TranscludedInResponse {

		@JsonProperty("continue")
		Continue continuePart;
		String batchcomplete;
		Query query;

	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static final class Continue {

		String ticontinue;

	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static final class Query {

		Map<String, Page> pages;

	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static final class Page {

		Long pageid;
		String title;
		Integer ns;
		List<Page> transcludedin;

	}

}
