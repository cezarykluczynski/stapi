package com.cezarykluczynski.stapi.etl.material.creation.service;

import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MaterialsAndSubstancesDetectorService {

	private static final String ALLOYS_AND_COMPOSITES = "Alloys and composites";
	private static final String FUELS = "Fuels";
	private static final String EXPLOSIVES = "Explosives";
	private static final String PRECIOUS_MATERIALS = "Precious materials";

	private final PageApi pageApi;

	private final WikitextApi wikitextApi;

	private final Set<String> alloysAndComposites = Sets.newHashSet();
	private final Set<String> fuels = Sets.newHashSet();
	private final Set<String> explosives = Sets.newHashSet();
	private final Set<String> preciousMaterials = Sets.newHashSet();

	public MaterialsAndSubstancesDetectorService(PageApi pageApi, WikitextApi wikitextApi) {
		this.pageApi = pageApi;
		this.wikitextApi = wikitextApi;
	}

	@PostConstruct
	public void postConstruct() {
		Page page = pageApi.getPage(PageTitle.MATERIALS_AND_SUBSTANCES, MediaWikiSource.MEMORY_ALPHA_EN);

		if (page == null) {
			return;
		}

		List<PageSection> pageSectionList = page.getSections();

		alloysAndComposites.addAll(extractPageSection(pageSectionList, ALLOYS_AND_COMPOSITES));
		fuels.addAll(extractPageSection(pageSectionList, FUELS));
		explosives.addAll(extractPageSection(pageSectionList, EXPLOSIVES));
		preciousMaterials.addAll(extractPageSection(pageSectionList, PRECIOUS_MATERIALS));
	}

	private Set<String> extractPageSection(List<PageSection> pageSectionList, String sectionName) {
		return pageSectionList.stream().filter(pageSection -> sectionName.equals(pageSection.getText()))
				.map(PageSection::getWikitext)
				.map(wikitextApi::getPageTitlesFromWikitext)
				.flatMap(List::stream)
				.collect(Collectors.toSet());
	}

	public boolean isAlloyOrComposite(String materialName) {
		return StringUtil.containsIgnoreCase(alloysAndComposites, materialName);
	}

	public boolean isFuel(String materialName) {
		return StringUtil.containsIgnoreCase(fuels, materialName);
	}

	public boolean isExplosive(String materialName) {
		return StringUtil.containsIgnoreCase(explosives, materialName);
	}

	public boolean isPreciousMaterial(String materialName) {
		return StringUtil.containsIgnoreCase(preciousMaterials, materialName);
	}

}
