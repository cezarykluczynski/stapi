package com.cezarykluczynski.stapi.etl.location.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.location.creation.service.LocationPageFilter;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
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

		location.setEarthlyLocation(categoryTitleList.contains(CategoryTitle.EARTH_ESTABLISHMENTS));
		location.setSchool(StringUtil.anyEndsWithIgnoreCase(categoryTitleList, CategoryTitle.SCHOOLS));
		location.setDs9Establishment(categoryTitleList.contains(CategoryTitle.DS9_ESTABLISHMENTS));
		location.setMedicalEstablishment(categoryTitleList.contains(CategoryTitle.MEDICAL_ESTABLISHMENTS)
				|| categoryTitleList.contains(CategoryTitle.WARDS) || categoryTitleList.contains(CategoryTitle.MEDICAL_ESTABLISHMENTS_RETCONNED));
		Boolean isEstablishment = location.getSchool() || location.getDs9Establishment() || location.getMedicalEstablishment();
		location.setEstablishment(isEstablishment || StringUtil.anyEndsWithIgnoreCase(categoryTitleList, CategoryTitle.ESTABLISHMENTS)
				|| categoryTitleList.contains(CategoryTitle.ESTABLISHMENTS_RETCONNED) || categoryTitleList.contains(CategoryTitle.WARDS)
				|| categoryTitleList.contains(CategoryTitle.MEDICAL_ESTABLISHMENTS_RETCONNED));
		location.setMirror(categoryTitleList.contains(CategoryTitle.MIRROR_UNIVERSE));
		location.setAlternateReality(categoryTitleList.contains(CategoryTitle.ALTERNATE_REALITY));

		return location;
	}

}
