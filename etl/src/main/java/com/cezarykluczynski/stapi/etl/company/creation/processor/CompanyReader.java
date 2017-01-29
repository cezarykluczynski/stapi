package com.cezarykluczynski.stapi.etl.company.creation.processor;


import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class CompanyReader extends ListItemReader<PageHeader> {

	public CompanyReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of company list: {}", list.size());
	}

}
