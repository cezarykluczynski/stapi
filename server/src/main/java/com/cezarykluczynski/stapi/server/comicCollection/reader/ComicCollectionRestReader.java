package com.cezarykluczynski.stapi.server.comicCollection.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionFullResponse;
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection;
import com.cezarykluczynski.stapi.server.comicCollection.dto.ComicCollectionRestBeanParams;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionBaseRestMapper;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionFullRestMapper;
import com.cezarykluczynski.stapi.server.comicCollection.query.ComicCollectionRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicCollectionRestReader implements BaseReader<ComicCollectionRestBeanParams, ComicCollectionBaseResponse>,
		FullReader<String, ComicCollectionFullResponse> {

	private final ComicCollectionRestQuery comicCollectionRestQuery;

	private final ComicCollectionBaseRestMapper comicCollectionBaseRestMapper;

	private final ComicCollectionFullRestMapper comicCollectionFullRestMapper;

	private final PageMapper pageMapper;

	@Inject
	public ComicCollectionRestReader(ComicCollectionRestQuery comicCollectionRestQuery, ComicCollectionBaseRestMapper comicCollectionBaseRestMapper,
			ComicCollectionFullRestMapper comicCollectionFullRestMapper, PageMapper pageMapper) {
		this.comicCollectionRestQuery = comicCollectionRestQuery;
		this.comicCollectionBaseRestMapper = comicCollectionBaseRestMapper;
		this.comicCollectionFullRestMapper = comicCollectionFullRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public ComicCollectionBaseResponse readBase(ComicCollectionRestBeanParams comicCollectionRestBeanParams) {
		Page<ComicCollection> comicCollectionPage = comicCollectionRestQuery.query(comicCollectionRestBeanParams);
		ComicCollectionBaseResponse comicCollectionResponse = new ComicCollectionBaseResponse();
		comicCollectionResponse.setPage(pageMapper.fromPageToRestResponsePage(comicCollectionPage));
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
