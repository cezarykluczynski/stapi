package com.cezarykluczynski.stapi.etl.common.configuration

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import org.springframework.batch.item.support.ListItemReader
import spock.lang.Specification

abstract class AbstractCreationConfigurationTest extends Specification {

	protected static List<PageHeader> createListWithPageHeaderTitle(String title) {
		Lists.newArrayList(PageHeader.builder().title(title).build())
	}

	protected static List<String> readerToList(ListItemReader<PageHeader> listItemReader) {
		List<String> pageHeaderList = Lists.newArrayList()

		while (true) {
			PageHeader pageHeader = listItemReader.read()

			if (pageHeader == null) {
				break
			}

			pageHeaderList.add pageHeader.title
		}

		pageHeaderList
	}

}
