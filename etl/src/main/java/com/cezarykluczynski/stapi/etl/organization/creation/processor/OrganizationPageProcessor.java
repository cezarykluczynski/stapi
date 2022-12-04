package com.cezarykluczynski.stapi.etl.organization.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.organization.creation.service.OrganizationPageFilter;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationPageProcessor implements ItemProcessor<Page, Organization> {

	private final OrganizationPageFilter organizationPageFilter;

	private final PageBindingService pageBindingService;

	private final UidGenerator uidGenerator;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	private final OrganizationNameFixedValueProvider organizationNameFixedValueProvider;

	public OrganizationPageProcessor(OrganizationPageFilter organizationPageFilter, PageBindingService pageBindingService,
			UidGenerator uidGenerator, CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor,
			OrganizationNameFixedValueProvider organizationNameFixedValueProvider) {
		this.organizationPageFilter = organizationPageFilter;
		this.pageBindingService = pageBindingService;
		this.uidGenerator = uidGenerator;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
		this.organizationNameFixedValueProvider = organizationNameFixedValueProvider;
	}

	@Override
	public Organization process(Page item) throws Exception {
		if (organizationPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		Organization organization = new Organization();
		FixedValueHolder<String> titleFixedValueHolder = organizationNameFixedValueProvider.getSearchedValue(item.getTitle());
		organization.setName(titleFixedValueHolder.isFound() ? titleFixedValueHolder.getValue() : item.getTitle());

		organization.setPage(pageBindingService.fromPageToPageEntity(item));
		organization.setUid(uidGenerator.generateFromPage(organization.getPage(), Organization.class));

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());

		organization.setGovernment(categoryTitleList.contains(CategoryTitle.GOVERNMENTS));
		organization.setIntergovernmentalOrganization(categoryTitleList.contains(CategoryTitle.INTERGOVERNMENTAL_ORGANIZATIONS)
				|| categoryTitleList.contains(CategoryTitle.EARTH_INTERGOVERNMENTAL_ORGANIZATIONS));
		organization.setResearchOrganization(categoryTitleList.contains(CategoryTitle.RESEARCH_ORGANIZATIONS));
		organization.setSportOrganization(categoryTitleList.contains(CategoryTitle.SPORTS_ORGANIZATIONS));
		organization.setMedicalOrganization(categoryTitleList.contains(CategoryTitle.MEDICAL_ORGANIZATIONS)
				|| categoryTitleList.contains(CategoryTitle.MEDICAL_ESTABLISHMENTS) || categoryTitleList.contains(CategoryTitle.WARDS)
				|| categoryTitleList.contains(CategoryTitle.MEDICAL_ESTABLISHMENTS_RETCONNED));
		organization.setMilitaryUnit(categoryTitleList.contains(CategoryTitle.MILITARY_UNITS));
		organization.setMilitaryOrganization(organization.getMilitaryUnit() || categoryTitleList.contains(CategoryTitle.MILITARY_ORGANIZATIONS)
				|| categoryTitleList.contains(CategoryTitle.EARTH_MILITARY_ORGANIZATIONS));
		organization.setGovernmentAgency(StringUtil.anyEndsWithIgnoreCase(categoryTitleList, CategoryTitle.AGENCIES));
		organization.setLawEnforcementAgency(categoryTitleList.contains(CategoryTitle.LAW_ENFORCEMENT_AGENCIES));
		organization.setPrisonOrPenalColony(categoryTitleList.contains(CategoryTitle.PRISONS_AND_PENAL_COLONIES));
		organization.setMirror(categoryTitleList.contains(CategoryTitle.MIRROR_UNIVERSE));
		organization.setAlternateReality(categoryTitleList.contains(CategoryTitle.ALTERNATE_REALITY));

		return organization;
	}

}
