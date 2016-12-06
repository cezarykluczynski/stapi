package com.cezarykluczynski.stapi.etl.template.common.linker;

import com.cezarykluczynski.stapi.etl.common.processor.LinkingWorker;
import com.cezarykluczynski.stapi.etl.template.common.processor.AbstractTemplateProcessor;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateName;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.NonUniqueResultException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EpisodeStaffLinkingWorker extends AbstractTemplateProcessor implements LinkingWorker<Page, Episode> {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN;
	private static final String WS_WRITTEN_BY = "wswrittenby";
	private static final String WS_TELEPLAY_BY = "wsteleplayby";
	private static final String WS_STORY_BY = "wsstoryby";
	private static final String WS_DIRECTED_BY = "wsdirectedby";

	private WikitextApi wikitextApi;

	private PageApi pageApi;

	private StaffRepository staffRepository;

	@Inject
	public EpisodeStaffLinkingWorker(WikitextApi wikitextApi, PageApi pageApi, StaffRepository staffRepository) {
		this.wikitextApi = wikitextApi;
		this.pageApi = pageApi;
		this.staffRepository = staffRepository;
	}

	@Override
	public void link(Page source, Episode baseEntity) {
		Optional<Template> templateOptional = findTemplate(source, TemplateName.SIDEBAR_EPISODE);

		if (!templateOptional.isPresent()) {
			return;
		}

		Template template = templateOptional.get();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch(key) {
				case WS_WRITTEN_BY:
					baseEntity.setWriters(extractStaffFromWikitext(value));
					break;
				case WS_TELEPLAY_BY:
					baseEntity.setTeleplayAuthors(extractStaffFromWikitext(value));
					break;
				case WS_STORY_BY:
					baseEntity.setStoryAuthors(extractStaffFromWikitext(value));
					break;
				case WS_DIRECTED_BY:
					baseEntity.setDirectors(extractStaffFromWikitext(value));
					break;
			}
		}
	}

	private Set<Staff> extractStaffFromWikitext(String value) {
		return wikitextApi.getPageLinksFromWikitext(value).stream()
				.map(PageLink::getTitle)
				.map(this::getStaff)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toSet());
	}

	private Optional<Staff> getStaff(String staffName) {
		Optional<Staff> staffOptional;

		try {
			staffOptional = staffRepository.findByName(staffName);
		} catch (NonUniqueResultException e) {
			staffOptional = Optional.empty();
		}

		if (staffOptional.isPresent()) {
			return staffOptional;
		} else {
			Page page = pageApi.getPage(staffName, SOURCE);
			if (page != null) {
				return staffRepository.findByPagePageId(page.getPageId());
			}
		}

		return Optional.empty();
	}

}
