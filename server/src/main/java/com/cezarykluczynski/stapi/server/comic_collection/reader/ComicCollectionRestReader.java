package com.cezarykluczynski.stapi.server.comic_collection.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionFullResponse;
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection;
import com.cezarykluczynski.stapi.server.comic_collection.dto.ComicCollectionRestBeanParams;
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionBaseRestMapper;
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionFullRestMapper;
import com.cezarykluczynski.stapi.server.comic_collection.query.ComicCollectionRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ComicCollectionRestReader implements BaseReader<ComicCollectionRestBeanParams, ComicCollectionBaseResponse>,
		FullReader<String, ComicCollectionFullResponse> {

	private final ComicCollectionRestQuery comicCollectionRestQuery;

	private final ComicCollectionBaseRestMapper comicCollectionBaseRestMapper;

	private final ComicCollectionFullRestMapper comicCollectionFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public ComicCollectionRestReader(ComicCollectionRestQuery comicCollectionRestQuery, ComicCollectionBaseRestMapper comicCollectionBaseRestMapper,
			ComicCollectionFullRestMapper comicCollectionFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.comicCollectionRestQuery = comicCollectionRestQuery;
		this.comicCollectionBaseRestMapper = comicCollectionBaseRestMapper;
		this.comicCollectionFullRestMapper = comicCollectionFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public ComicCollectionBaseResponse readBase(ComicCollectionRestBeanParams input) {
		Page<ComicCollection> comicCollectionPage = comicCollectionRestQuery.query(input);
		ComicCollectionBaseResponse comicCollectionResponse = new ComicCollectionBaseResponse();
		comicCollectionResponse.setPage(pageMapper.fromPageToRestResponsePage(comicCollectionPage));
		comicCollectionResponse.setSort(sortMapper.map(input.getSort()));
		comicCollectionResponse.getComicCollections().addAll(comicCollectionBaseRestMapper.mapBase(comicCollectionPage.getContent()));
		return comicCollectionResponse;
	}

	@Override
	public ComicCollectionFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		ComicCollectionRestBeanParams comicCollectionRestBeanParams = new ComicCollectionRestBeanParams();
		comicCollectionRestBeanParams.setUid(uid);
		Page<ComicCollection> comicCollectionPage = comicCollectionRestQuery.query(comicCollectionRestBeanParams);
		ComicCollectionFullResponse comicCollectionResponse = new ComicCollectionFullResponse();
		comicCollectionResponse.setComicCollection(comicCollectionFullRestMapper
				.mapFull(Iterables.getOnlyElement(comicCollectionPage.getContent(), null)));
		return comicCollectionResponse;
	}

}
