package com.cezarykluczynski.stapi.etl.title.creation.processor.list;

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TitleListPageProcessor {

	private static final String RANKS = " ranks";
	private static final String MACOS = "MACOs";
	private static final Set<String> PAGE_SECTIONS_TO_FILTER_OUT = Sets.newHashSet("Enlisted personnel", "Background information", "External link",
			"Appendices", "See also", "Appendix", "Rank insignia", "Romulan military", "Tal Shiar");

	private final TitleListSectionProcessor titleListSectionProcessor;

	private final TitleListMacoMirrorProcessor titleListMacoMirrorProcessor;

	public TitleListPageProcessor(TitleListSectionProcessor titleListSectionProcessor, TitleListMacoMirrorProcessor titleListMacoMirrorProcessor) {
		this.titleListSectionProcessor = titleListSectionProcessor;
		this.titleListMacoMirrorProcessor = titleListMacoMirrorProcessor;
	}

	public void process(Page item) {
		String organization = StringUtils.substringBefore(item.getTitle(), RANKS);

		int maxNesting = item.getSections()
				.stream()
				.filter(this::shouldNotBeenFilteredOut)
				.map(PageSection::getLevel)
				.filter(Objects::nonNull)
				.mapToInt(value -> value)
				.max().orElse(2);

		List<PageSection> pageSectionList = item.getSections()
				.stream()
				.filter(this::shouldNotBeenFilteredOut)
				.filter(pageSection -> pageSection.getLevel().equals(maxNesting))
				.collect(Collectors.toList());

		for (int i = 0; i < pageSectionList.size(); i++) {
			PageSection pageSection = pageSectionList.get(i);

			if (MACOS.equals(pageSection.getText())) {
				titleListMacoMirrorProcessor.produceAll(item, i);
			} else {
				titleListSectionProcessor.process(item, pageSection, organization, i);
			}
		}
	}

	private boolean shouldNotBeenFilteredOut(PageSection pageSection) {
		return !PAGE_SECTIONS_TO_FILTER_OUT.contains(pageSection.getText());
	}

}
