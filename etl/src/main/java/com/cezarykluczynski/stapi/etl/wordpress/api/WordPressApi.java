package com.cezarykluczynski.stapi.etl.wordpress.api;

import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;
import com.cezarykluczynski.stapi.etl.wordpress.api.enums.WordPressSource;
import com.cezarykluczynski.stapi.etl.wordpress.connector.afrozaar.WordPressAfrozaarConnector;
import com.cezarykluczynski.stapi.etl.wordpress.connector.afrozaar.WordPressPageMapper;
import com.cezarykluczynski.stapi.etl.wordpress.dto.Page;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordPressApi {

	private final WordPressAfrozaarConnector wordPressAfrozaarConnector;

	private final WordPressPageMapper wordPressPageMapper;

	public WordPressApi(WordPressAfrozaarConnector wordPressAfrozaarConnector, WordPressPageMapper wordPressPageMapper) {
		this.wordPressAfrozaarConnector = wordPressAfrozaarConnector;
		this.wordPressPageMapper = wordPressPageMapper;
	}

	public List<Page> getAllPagesUnderPage(String pageId, WordPressSource wordPressSource) {
		List<com.afrozaar.wordpress.wpapi.v2.model.Page> pageList = Lists.newArrayList();
		int pageNumber = 1;

		while (true) {
			PagedResponse<com.afrozaar.wordpress.wpapi.v2.model.Page> pagedResponse = wordPressAfrozaarConnector
					.getPagesUnderPage(pageId, pageNumber, wordPressSource);

			pageList.addAll(pagedResponse.getList());

			if (!pagedResponse.hasNext() || pagedResponse.getList().isEmpty()) {
				break;
			}

			pageNumber++;
		}

		return pageList.stream()
				.map(wordPressPageMapper::map)
				.collect(Collectors.toList());
	}

}
