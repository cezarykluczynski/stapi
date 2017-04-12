package com.cezarykluczynski.stapi.etl.location.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class LocationProcessor extends CompositeItemProcessor<PageHeader, Location> {

	@Inject
	public LocationProcessor(PageHeaderProcessor pageHeaderProcessor, LocationPageProcessor locationPageProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, locationPageProcessor));
	}

}
