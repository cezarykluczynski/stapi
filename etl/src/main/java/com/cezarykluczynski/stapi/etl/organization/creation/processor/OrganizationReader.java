package com.cezarykluczynski.stapi.etl.organization.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class OrganizationReader extends ListItemReader<PageHeader> {

	public OrganizationReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of organization list: {}", list.size());
	}

}
