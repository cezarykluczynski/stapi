package com.cezarykluczynski.stapi.etl.template.common.linker;

import com.cezarykluczynski.stapi.etl.common.processor.LinkingWorker;
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EpisodeStaffLinkingWorker implements LinkingWorker<Page, Episode> {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN;
	private static final String WS_WRITTEN_BY = "wswrittenby";
	private static final String WS_TELEPLAY_BY = "wsteleplayby";
	private static final String WS_STORY_BY = "wsstoryby";
	private static final String WS_DIRECTED_BY = "wsdirectedby";

	private final WikitextApi wikitextApi;

	private final EntityLookupByNameService entityLookupByNameService;

	private final TemplateFinder templateFinder;

	public EpisodeStaffLinkingWorker(WikitextApi wikitextApi, EntityLookupByNameService entityLookupByNameService,
			TemplateFinder templateFinder) {
		this.wikitextApi = wikitextApi;
		this.entityLookupByNameService = entityLookupByNameService;
		this.templateFinder = templateFinder;
	}

	@Override
	public void link(Page source, Episode baseEntity) {
		Optional<Template> templateOptional = templateFinder.findTemplate(source, TemplateTitle.SIDEBAR_EPISODE);

		if (!templateOptional.isPresent()) {
			return;
		}

		Template template = templateOptional.get();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			if (key == null) {
				continue;
			}

			switch (key) {
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
				default:
					break;
			}
		}
	}

	private Set<Staff> extractStaffFromWikitext(String value) {
		return wikitextApi.getPageLinksFromWikitext(value).stream()
				.map(PageLink::getTitle)
				.map(this::findStaffByName)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toSet());
	}

	private Optional<Staff> findStaffByName(String staffName) {
		return entityLookupByNameService.findStaffByName(staffName, SOURCE);
	}

}
