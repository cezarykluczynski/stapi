package com.cezarykluczynski.stapi.etl.location.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.location.creation.service.LocationPageFilter;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class LocationPageProcessor implements ItemProcessor<Page, Location> {

	private LocationPageFilter locationPageFilter;

	private PageBindingService pageBindingService;

	private GuidGenerator guidGenerator;

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	private LocationNameFixedValueProvider locationNameFixedValueProvider;

	@Inject
	public LocationPageProcessor(LocationPageFilter locationPageFilter, PageBindingService pageBindingService, GuidGenerator guidGenerator,
			CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor, LocationNameFixedValueProvider locationNameFixedValueProvider) {
		this.locationPageFilter = locationPageFilter;
		this.pageBindingService = pageBindingService;
		this.guidGenerator = guidGenerator;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
		this.locationNameFixedValueProvider = locationNameFixedValueProvider;
	}

	@Override
	public Location process(Page item) throws Exception {
		if (locationPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		Location location = new Location();
		FixedValueHolder<String> titleFixedValueHolder = locationNameFixedValueProvider.getSearchedValue(item.getTitle());
		location.setName(titleFixedValueHolder.isFound() ? titleFixedValueHolder.getValue() : item.getTitle());

		location.setPage(pageBindingService.fromPageToPageEntity(item));
		location.setGuid(guidGenerator.generateFromPage(location.getPage(), Location.class));

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());

		location.setSchool(StringUtil.anyEndsWithIgnoreCase(categoryTitleList, CategoryTitle.SCHOOLS));
		location.setDs9Establishment(categoryTitleList.contains(CategoryTitle.DS9_ESTABLISHMENTS));
		location.setMedicalEstablishment(hasMedicalEstablishmentCategory(categoryTitleList));
		Boolean isEstablishment = location.getSchool() || location.getDs9Establishment() || location.getMedicalEstablishment();
		location.setEstablishment(isEstablishment || hasEstablishmentCategory(categoryTitleList));
		location.setColony(categoryTitleList.contains(CategoryTitle.COLONIES));
		location.setBajoranSettlement(categoryTitleList.contains(CategoryTitle.BAJORAN_SETTLEMENTS));
		location.setUsSettlement(categoryTitleList.contains(CategoryTitle.US_SETTLEMENTS)
				|| categoryTitleList.contains(CategoryTitle.US_SETTLEMENTS_RETCONNED));
		Boolean isSettlement = location.getBajoranSettlement() || location.getUsSettlement() || location.getColony();
		location.setSettlement(isSettlement || hasSettlementCategory(categoryTitleList));
		location.setCountry(hasCountryCategory(categoryTitleList));
		location.setGeographicalLocation(location.getCountry() || categoryTitleList.contains(CategoryTitle.GEOGRAPHY)
				|| categoryTitleList.contains(CategoryTitle.EARTH_GEOGRAPHY));
		location.setFictionalLocation(categoryTitleList.contains(CategoryTitle.FICTIONAL_LOCATIONS));
		location.setSubnationalEntity(location.getSettlement() || hasSubnationalEntityCategory(categoryTitleList));
		location.setEarthlyLocation(StringUtil.anyStartsWithIgnoreCase(categoryTitleList, PageTitle.EARTH) || location.getUsSettlement());
		location.setBodyOfWater(categoryTitleList.contains(CategoryTitle.BODIES_OF_WATER)
				|| categoryTitleList.contains(CategoryTitle.EARTH_BODIES_OF_WATER));
		location.setBuildingInterior(categoryTitleList.contains(CategoryTitle.BUILDING_INTERIORS));
		location.setLandform(categoryTitleList.contains(CategoryTitle.LANDFORMS));
		location.setLandmark(categoryTitleList.contains(CategoryTitle.LANDMARKS) || categoryTitleList.contains(CategoryTitle.EARTH_LANDMARKS));
		location.setReligiousLocation(categoryTitleList.contains(CategoryTitle.RELIGIOUS_LOCATIONS));
		location.setStructure(location.getBuildingInterior() || location.getLandmark() || categoryTitleList.contains(CategoryTitle.STRUCTURES));
		location.setRoad(categoryTitleList.contains(CategoryTitle.ROADS) || categoryTitleList.contains(CategoryTitle.EARTH_ROADS));
		location.setShipyard(categoryTitleList.contains(CategoryTitle.SHIPYARDS));
		location.setMirror(categoryTitleList.contains(CategoryTitle.MIRROR_UNIVERSE));
		location.setAlternateReality(categoryTitleList.contains(CategoryTitle.ALTERNATE_REALITY)
				|| categoryTitleList.contains(CategoryTitle.LOCATIONS_ALTERNATE_REALITY));

		return location;
	}

	private Boolean hasMedicalEstablishmentCategory(List<String> categoryTitleList) {
		return categoryTitleList.contains(CategoryTitle.MEDICAL_ESTABLISHMENTS) || categoryTitleList.contains(CategoryTitle.WARDS)
				|| categoryTitleList.contains(CategoryTitle.MEDICAL_ESTABLISHMENTS_RETCONNED);
	}

	private Boolean hasSettlementCategory(List<String> categoryTitleList) {
		return categoryTitleList.contains(CategoryTitle.SETTLEMENTS) || categoryTitleList.contains(CategoryTitle.SETTLEMENTS_RETCONNED)
				|| categoryTitleList.contains(CategoryTitle.EARTH_SETTLEMENTS);
	}

	private Boolean hasEstablishmentCategory(List<String> categoryTitleList) {
		return StringUtil.anyEndsWithIgnoreCase(categoryTitleList, CategoryTitle.ESTABLISHMENTS)
				|| categoryTitleList.contains(CategoryTitle.ESTABLISHMENTS_RETCONNED) || categoryTitleList.contains(CategoryTitle.WARDS)
				|| categoryTitleList.contains(CategoryTitle.MEDICAL_ESTABLISHMENTS_RETCONNED);
	}

	private Boolean hasSubnationalEntityCategory(List<String> categoryTitleList) {
		return categoryTitleList.contains(CategoryTitle.SUBNATIONAL_ENTITIES)
				|| categoryTitleList.contains(CategoryTitle.SUBNATIONAL_ENTITIES_RETCONNED)
				|| categoryTitleList.contains(CategoryTitle.EARTH_SUBNATIONAL_ENTITIES);
	}

	private Boolean hasCountryCategory(List<String> categoryTitleList) {
		return categoryTitleList.contains(CategoryTitle.COUNTRIES) || categoryTitleList.contains(CategoryTitle.EARTH_COUNTRIES)
				|| categoryTitleList.contains(CategoryTitle.EARTH_COUNTRIES);
	}

}
