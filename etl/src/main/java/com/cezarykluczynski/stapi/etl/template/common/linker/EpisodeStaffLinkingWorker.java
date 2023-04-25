package com.cezarykluczynski.stapi.etl.template.common.linker;

import com.cezarykluczynski.stapi.etl.common.processor.LinkingWorker;
import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService;
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EpisodeStaffLinkingWorker implements LinkingWorker<Page, Episode> {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN;

	private final WikitextApi wikitextApi;

	private final EntityLookupByNameService entityLookupByNameService;

	private final TemplateFinder templateFinder;

	public EpisodeStaffLinkingWorker(WikitextApi wikitextApi, EntityLookupByNameService entityLookupByNameService, TemplateFinder templateFinder) {
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

		String writerValue = null;
		String teleplayValue = null;
		String storyValue = null;
		String directorValue = null;
		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			if (key == null) {
				continue;
			}

			switch (key) {
				case EpisodeTemplateParameter.WRITER:
					writerValue = value;
					baseEntity.setWriters(extractStaffFromWikitext(value));
					break;
				case EpisodeTemplateParameter.TELEPLAY:
					teleplayValue = value;
					baseEntity.setTeleplayAuthors(extractStaffFromWikitext(value));
					break;
				case EpisodeTemplateParameter.STORY:
					storyValue = value;
					baseEntity.setStoryAuthors(extractStaffFromWikitext(value));
					break;
				case EpisodeTemplateParameter.DIRECTOR:
					directorValue = value;
					baseEntity.setDirectors(extractStaffFromWikitext(value));
					break;
				default:
					break;
			}
		}
		Set<Staff> all = Sets.newHashSet();
		all.addAll(baseEntity.getWriters());
		all.addAll(baseEntity.getTeleplayAuthors());
		all.addAll(baseEntity.getStoryAuthors());
		all.addAll(baseEntity.getDirectors());
		baseEntity.getWriters().addAll(getMissingValues(writerValue, all));
		baseEntity.getTeleplayAuthors().addAll(getMissingValues(teleplayValue, all));
		baseEntity.getStoryAuthors().addAll(getMissingValues(storyValue, all));
		baseEntity.getDirectors().addAll(getMissingValues(directorValue, all));
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


	private Set<Staff> getMissingValues(String value, Set<Staff> all) {
		if (value == null) {
			return Sets.newHashSet();
		}
		return all.stream()
				.filter(staff -> value.contains(staff.getName()))
				.collect(Collectors.toSet());
	}

}
