package com.cezarykluczynski.stapi.etl.staff.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class StaffReader extends ListItemReader<PageHeader> {

	public StaffReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of staff list: {}", list.size());
	}

}
