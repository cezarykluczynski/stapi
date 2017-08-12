package com.cezarykluczynski.stapi.etl.weapon.creation.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.ListItemReader;

import java.util.List;

@Slf4j
public class WeaponReader extends ListItemReader<PageHeader> {

	public WeaponReader(List<PageHeader> list) {
		super(list);
		log.info("Initial size of weapon list: {}", list.size());
	}

}
