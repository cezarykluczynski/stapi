package com.cezarykluczynski.stapi.etl.technology.creation.processor;

import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.technology.creation.service.TechnologyPageFilter;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyPageProcessor implements ItemProcessor<Page, Technology> {

	private final TechnologyPageFilter technologyPageFilter;

	private final PageBindingService pageBindingService;

	private final UidGenerator uidGenerator;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public TechnologyPageProcessor(TechnologyPageFilter technologyPageFilter, PageBindingService pageBindingService, UidGenerator uidGenerator,
			CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.technologyPageFilter = technologyPageFilter;
		this.pageBindingService = pageBindingService;
		this.uidGenerator = uidGenerator;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	@SuppressWarnings("CyclomaticComplexity")
	public Technology process(Page item) throws Exception {
		if (technologyPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		Technology technology = new Technology();
		technology.setName(TitleUtil.getNameFromTitleIfBracketsEndsString(item.getTitle()));

		technology.setPage(pageBindingService.fromPageToPageEntity(item));
		technology.setUid(uidGenerator.generateFromPage(technology.getPage(), Technology.class));

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());

		technology.setBorgComponent(categoryTitleList.contains(CategoryTitle.BORG_COMPONENTS));
		technology.setBorgTechnology(technology.getBorgComponent() || categoryTitleList.contains(CategoryTitle.BORG_TECHNOLOGY));
		technology.setCommunicationsTechnology(categoryTitleList.contains(CategoryTitle.COMMUNICATIONS_TECHNOLOGY)
				|| categoryTitleList.contains(CategoryTitle.COMMUNICATIONS_TECHNOLOGY_RETCONNED));
		technology.setSubroutine(categoryTitleList.contains(CategoryTitle.SUBROUTINES));
		technology.setComputerProgramming(technology.getSubroutine() || categoryTitleList.contains(CategoryTitle.COMPUTER_PROGRAMMING));
		technology.setDatabase(categoryTitleList.contains(CategoryTitle.DATABASES));
		technology.setComputerTechnology(technology.getComputerProgramming() || technology.getDatabase()
				|| categoryTitleList.contains(CategoryTitle.COMPUTER_TECHNOLOGY));
		technology.setEnergyTechnology(categoryTitleList.contains(CategoryTitle.ENERGY_TECHNOLOGY)
				|| categoryTitleList.contains(CategoryTitle.ENERGY_TECHNOLOGY_RETCONNED));
		technology.setFictionalTechnology(categoryTitleList.contains(CategoryTitle.FICTIONAL_TECHNOLOGY));
		technology.setHolographicTechnology(categoryTitleList.contains(CategoryTitle.HOLOGRAPHIC_TECHNOLOGY));
		technology.setIdentificationTechnology(categoryTitleList.contains(CategoryTitle.IDENTIFICATION_TECHNOLOGY));
		technology.setLifeSupportTechnology(categoryTitleList.contains(CategoryTitle.LIFE_SUPPORT_TECHNOLOGY));
		technology.setSensorTechnology(categoryTitleList.contains(CategoryTitle.SENSOR_TECHNOLOGY)
				|| categoryTitleList.contains(CategoryTitle.SENSOR_TECHNOLOGY_RETCONNED));
		technology.setShieldTechnology(categoryTitleList.contains(CategoryTitle.SHIELD_TECHNOLOGY)
				|| categoryTitleList.contains(CategoryTitle.SHIELD_TECHNOLOGY_RETCONNED));
		technology.setSecurityTechnology(technology.getSensorTechnology() || technology.getShieldTechnology()
				|| technology.getIdentificationTechnology() || categoryTitleList.contains(CategoryTitle.SECURITY_TECHNOLOGY));
		technology.setWarpTechnology(categoryTitleList.contains(CategoryTitle.WARP));
		technology.setTranswarpTechnology(categoryTitleList.contains(CategoryTitle.TRANSWARP));
		technology.setTimeTravelTechnology(categoryTitleList.contains(CategoryTitle.TIME_TRAVEL_TECHNOLOGY));
		technology.setMilitaryTechnology(categoryTitleList.contains(CategoryTitle.MILITARY_TECHNOLOGY));
		technology.setPropulsionTechnology(technology.getWarpTechnology() || technology.getTranswarpTechnology()
				|| categoryTitleList.contains(CategoryTitle.PROPULSION_TECHNOLOGY)
				|| categoryTitleList.contains(CategoryTitle.PROPULSION_TECHNOLOGY_RETCONNED));
		technology.setSpacecraftComponent(technology.getPropulsionTechnology()
				|| categoryTitleList.contains(CategoryTitle.SPACECRAFT_COMPONENTS)
				|| categoryTitleList.contains(CategoryTitle.SPACECRAFT_COMPONENTS_RETCONNED));
		technology.setCulinaryTool(categoryTitleList.contains(CategoryTitle.CULINARY_TOOLS));
		technology.setVictualTechnology(technology.getCulinaryTool() || categoryTitleList.contains(CategoryTitle.VICTUAL_TECHNOLOGY));
		technology.setEngineeringTool(categoryTitleList.contains(CategoryTitle.ENGINEERING_TOOLS));
		technology.setHouseholdTool(categoryTitleList.contains(CategoryTitle.HOUSEHOLD_TOOLS));
		technology.setMedicalEquipment(categoryTitleList.contains(CategoryTitle.MEDICAL_EQUIPMENT));
		technology.setTool(technology.getCulinaryTool() || technology.getEngineeringTool() || technology.getHouseholdTool()
				|| technology.getMedicalEquipment() || categoryTitleList.contains(CategoryTitle.TOOLS));
		technology.setTransporterTechnology(categoryTitleList.contains(CategoryTitle.TRANSPORTER_TECHNOLOGY));
		technology.setTransportationTechnology(technology.getTransporterTechnology()
				|| categoryTitleList.contains(CategoryTitle.TRANSPORTATION_TECHNOLOGY));
		technology.setWeaponComponent(categoryTitleList.contains(CategoryTitle.WEAPON_COMPONENTS));
		technology.setArtificialLifeformComponent(categoryTitleList.contains(CategoryTitle.ARTIFICIAL_LIFEFORM_COMPONENTS));

		return technology;
	}

}
