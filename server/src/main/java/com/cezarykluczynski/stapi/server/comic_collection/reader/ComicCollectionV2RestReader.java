package com.cezarykluczynski.stapi.server.comic_collection.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionV2FullResponse;
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection;
import com.cezarykluczynski.stapi.server.comic_collection.dto.ComicCollectionRestBeanParams;
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionFullRestMapper;
import com.cezarykluczynski.stapi.server.comic_collection.query.ComicCollectionRestQuery;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ComicCollectionV2RestReader implements FullReader<ComicCollectionV2FullResponse> {

	private final ComicCollectionRestQuery comicCollectionRestQuery;

	private final ComicCollectionFullRestMapper comicCollectionFullRestMapper;

	public ComicCollectionV2RestReader(ComicCollectionRestQuery comicCollectionRestQuery,
			ComicCollectionFullRestMapper comicCollectionFullRestMapper) {
		this.comicCollectionRestQuery = comicCollectionRestQuery;
		this.comicCollectionFullRestMapper = comicCollectionFullRestMapper;
	}

	@Override
	public ComicCollectionV2FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		ComicCollectionRestBeanParams comicCollectionRestBeanParams = new ComicCollectionRestBeanParams();
		comicCollectionRestBeanParams.setUid(uid);
		Page<ComicCollection> comicCollectionPage = comicCollectionRestQuery.query(comicCollectionRestBeanParams);
		ComicCollectionV2FullResponse comicCollectionV2Response = new ComicCollectionV2FullResponse();
		comicCollectionV2Response.setComicCollection(comicCollectionFullRestMapper
				.mapV2Full(Iterables.getOnlyElement(comicCollectionPage.getContent(), null)));
		return comicCollectionV2Response;
	}
}
