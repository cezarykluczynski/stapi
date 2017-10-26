package com.cezarykluczynski.stapi.etl.title.creation.processor.list;

import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.page.repository.PageRepository;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.model.title.repository.TitleRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class TitleListSectionProcessor {

	private static final String MIRROR = "(mirror)";
	private static final Set<String> PAGE_SECTIONS_TO_FILTER_OUT = Sets.newHashSet("Enlisted personnel", "Background information", "External link",
			"Appendices", "See also", "Appendix", "Rank insignia", "Romulan military", "Tal Shiar");

	private final PageBindingService pageBindingService;

	private final PageRepository pageRepository;

	private final UidGenerator uidGenerator;

	private final TitleRepository titleRepository;

	TitleListSectionProcessor(PageBindingService pageBindingService, PageRepository pageRepository, UidGenerator uidGenerator,
			TitleRepository titleRepository) {
		this.pageBindingService = pageBindingService;
		this.pageRepository = pageRepository;
		this.uidGenerator = uidGenerator;
		this.titleRepository = titleRepository;
	}

	public void process(Page page, PageSection pageSection, String organization, Integer index) {
		String organizationInTitle = organization;
		String text = pageSection.getText();
		boolean isMirror = StringUtils.contains(page.getTitle(), MIRROR);

		if (isMirror) {
			organizationInTitle = organizationInTitle + ", mirror";
		}

		com.cezarykluczynski.stapi.model.page.entity.Page modelPage;
		if (index == 0) {
			modelPage = pageBindingService.fromPageToPageEntity(page);
		} else {
			modelPage = pageRepository.findByPageId(page.getPageId()).orElse(null);
		}

		if (PAGE_SECTIONS_TO_FILTER_OUT.contains(text)) {
			return;
		}

		Title title = new Title();
		title.setName(Jsoup.parse(pageSection.getText()).text() + " (" + organizationInTitle + ")");
		title.setPage(modelPage);
		title.setUid(uidGenerator.generateForTitleListItem(title.getPage(), index));
		title.setMilitaryRank(true);
		title.setReligiousTitle(false);
		title.setPosition(false);
		title.setFleetRank(false);
		title.setMirror(isMirror);
		titleRepository.save(title);
	}

}
