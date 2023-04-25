package com.cezarykluczynski.stapi.etl.location.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.location.creation.service.LocationPageFilter;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationPageProcessor implements ItemProcessor<Page, Location> {

	private final LocationPageFilter locationPageFilter;

	private final PageBindingService pageBindingService;

	private final UidGenerator uidGenerator;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	private final LocationNameFixedValueProvider locationNameFixedValueProvider;

	public LocationPageProcessor(LocationPageFilter locationPageFilter, PageBindingService pageBindingService, UidGenerator uidGenerator,
			CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor, LocationNameFixedValueProvider locationNameFixedValueProvider) {
		this.locationPageFilter = locationPageFilter;
		this.pageBindingService = pageBindingService;
		this.uidGenerator = uidGenerator;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
		this.locationNameFixedValueProvider = locationNameFixedValueProvider;
	}

	@Override
	public Location process(Page item) throws Exception {
		if (locationPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		Location location = new Location();
		FixedValueHolder<String> nameFixedValueHolder = locationNameFixedValueProvider.getSearchedValue(item.getTitle());
		location.setName(nameFixedValueHolder.isFound() ? nameFixedValueHolder.getValue() : item.getTitle());

		location.setPage(pageBindingService.fromPageToPageEntity(item));
		location.setUid(uidGenerator.generateFromPage(location.getPage(), Location.class));

		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(item.getCategories());

		location.setSchool(StringUtil.anyEndsWithIgnoreCase(categoryTitleList, CategoryTitle.SCHOOLS));
		location.setDs9Establishment(categoryTitleList.contains(CategoryTitle.DS9_ESTABLISHMENTS));
		location.setMedicalEstablishment(hasMedicalEstablishmentCategory(categoryTitleList));
		location.setRestaurant(categoryTitleList.contains(CategoryTitle.RESTAURANTS));
		location.setEstablishment(isEstablishment(location, categoryTitleList));
		location.setColony(categoryTitleList.contains(CategoryTitle.COLONIES));
		location.setBajoranSettlement(categoryTitleList.contains(CategoryTitle.BAJORAN_SETTLEMENTS));
		location.setUsSettlement(hasUsSettlementCategory(categoryTitleList));
		location.setCountry(hasCountryCategory(categoryTitleList));
		location.setSettlement(isSettlement(location, categoryTitleList));
		location.setBodyOfWater(categoryTitleList.contains(CategoryTitle.BODIES_OF_WATER)
				|| categoryTitleList.contains(CategoryTitle.EARTH_BODIES_OF_WATER));
		location.setGeographicalLocation(isGeographicalLocation(location, categoryTitleList));
		location.setFictionalLocation(categoryTitleList.contains(CategoryTitle.FICTIONAL_LOCATIONS));
		location.setMythologicalLocation(categoryTitleList.contains(CategoryTitle.MYTHOLOGICAL_LOCATIONS));
		location.setSubnationalEntity(location.getSettlement() || hasSubnationalEntityCategory(categoryTitleList));
		location.setEarthlyLocation(StringUtil.anyStartsWithIgnoreCase(categoryTitleList, PageTitle.EARTH) || location.getUsSettlement());
		location.setQonosLocation(categoryTitleList.contains(CategoryTitle.QONOS_LOCATIONS)
				|| categoryTitleList.contains(CategoryTitle.QONOS_SETTLEMENTS));
		location.setBuildingInterior(categoryTitleList.contains(CategoryTitle.BUILDING_INTERIORS));
		location.setLandform(categoryTitleList.contains(CategoryTitle.LANDFORMS));
		location.setLandmark(false);
		location.setReligiousLocation(categoryTitleList.contains(CategoryTitle.RELIGIOUS_LOCATIONS));
		location.setRoad(categoryTitleList.contains(CategoryTitle.ROADS) || categoryTitleList.contains(CategoryTitle.EARTH_ROADS));
		location.setShipyard(categoryTitleList.contains(CategoryTitle.SHIPYARDS));
		location.setResidence(categoryTitleList.contains(CategoryTitle.RESIDENCES));
		location.setStructure(isStructure(location, categoryTitleList));
		location.setMirror(categoryTitleList.contains(CategoryTitle.MIRROR_UNIVERSE));
		location.setAlternateReality(categoryTitleList.contains(CategoryTitle.ALTERNATE_REALITY)
				|| categoryTitleList.contains(CategoryTitle.LOCATIONS_ALTERNATE_REALITY));

		return location;
	}

	private boolean isGeographicalLocation(Location location, List<String> categoryTitleList) {
		return location.getCountry() || location.getSettlement() || location.getBodyOfWater()
				|| categoryTitleList.contains(CategoryTitle.GEOGRAPHY)
				|| categoryTitleList.contains(CategoryTitle.EARTH_GEOGRAPHY);
	}

	@SuppressWarnings({"BooleanExpressionComplexity"})
	private Boolean isEstablishment(Location location, List<String> categoryTitleList) {
		return location.getSchool() || location.getDs9Establishment() || location.getMedicalEstablishment() || location.getRestaurant()
				|| StringUtil.anyEndsWithIgnoreCase(categoryTitleList, CategoryTitle.ESTABLISHMENTS)
				|| categoryTitleList.contains(CategoryTitle.ESTABLISHMENTS_RETCONNED) || categoryTitleList.contains(CategoryTitle.WARDS)
				|| categoryTitleList.contains(CategoryTitle.MEDICAL_ESTABLISHMENTS_RETCONNED);
	}

	private boolean hasUsSettlementCategory(List<String> categoryTitleList) {
		return categoryTitleList.contains(CategoryTitle.US_SETTLEMENTS)
				|| categoryTitleList.contains(CategoryTitle.US_SETTLEMENTS_RETCONNED);
	}

	@SuppressWarnings({"BooleanExpressionComplexity"})
	private Boolean isSettlement(Location location, List<String> categoryTitleList) {
		return location.getBajoranSettlement() || location.getUsSettlement() || location.getColony()
				|| categoryTitleList.contains(CategoryTitle.SETTLEMENTS) || categoryTitleList.contains(CategoryTitle.SETTLEMENTS_RETCONNED)
				|| categoryTitleList.contains(CategoryTitle.EARTH_SETTLEMENTS)
				|| categoryTitleList.contains(CategoryTitle.QONOS_SETTLEMENTS);
	}

	private Boolean hasMedicalEstablishmentCategory(List<String> categoryTitleList) {
		return categoryTitleList.contains(CategoryTitle.MEDICAL_ESTABLISHMENTS) || categoryTitleList.contains(CategoryTitle.WARDS)
				|| categoryTitleList.contains(CategoryTitle.MEDICAL_ESTABLISHMENTS_RETCONNED);
	}

	private Boolean hasSubnationalEntityCategory(List<String> categoryTitleList) {
		return categoryTitleList.contains(CategoryTitle.SUBNATIONAL_ENTITIES)
				|| categoryTitleList.contains(CategoryTitle.SUBNATIONAL_ENTITIES_RETCONNED)
				|| categoryTitleList.contains(CategoryTitle.EARTH_SUBNATIONAL_ENTITIES);
	}

	private Boolean hasCountryCategory(List<String> categoryTitleList) {
		return categoryTitleList.contains(CategoryTitle.COUNTRIES) || categoryTitleList.contains(CategoryTitle.EARTH_COUNTRIES);
	}

	private boolean isStructure(Location location, List<String> categoryTitleList) {
		return location.getBuildingInterior() || location.getResidence() || location.getRoad() || location.getShipyard()
				|| categoryTitleList.contains(CategoryTitle.STRUCTURES) || categoryTitleList.contains(CategoryTitle.EARTH_STRUCTURES);
	}

}
