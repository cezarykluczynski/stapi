package com.cezarykluczynski.stapi.etl.season.creation.processor;

import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SeasonProcessor implements ItemProcessor<PageHeader, Season> {

	@Override
	public Season process(PageHeader item) throws Exception {
		// TODO
		return null;
	}

}
