package com.cezarykluczynski.stapi.etl.common.processor.organization;


import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WikitextToOrganizationsProcessor implements ItemProcessor<String, Set<Organization>> {

	private final WikitextApi wikitextApi;

	private final OrganizationRepository organizationRepository;

	@Inject
	public WikitextToOrganizationsProcessor(WikitextApi wikitextApi, OrganizationRepository organizationRepository) {
		this.wikitextApi = wikitextApi;
		this.organizationRepository = organizationRepository;
	}

	@Override
	public Set<Organization> process(String item) throws Exception {
		List<String> pageLinkTitleList = wikitextApi.getPageTitlesFromWikitext(item);

		return pageLinkTitleList.stream()
				.map(pageLinkTitle -> organizationRepository.findByPageTitleAndPageMediaWikiSource(pageLinkTitle, MediaWikiSource.MEMORY_ALPHA_EN))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toSet());
	}

}
