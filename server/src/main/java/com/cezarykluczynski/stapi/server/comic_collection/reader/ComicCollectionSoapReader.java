package com.cezarykluczynski.stapi.server.comic_collection.reader;

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullResponse;
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection;
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comic_collection.mapper.ComicCollectionFullSoapMapper;
import com.cezarykluczynski.stapi.server.comic_collection.query.ComicCollectionSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ComicCollectionSoapReader implements BaseReader<ComicCollectionBaseRequest, ComicCollectionBaseResponse>,
		FullReader<ComicCollectionFullRequest, ComicCollectionFullResponse> {

	private final ComicCollectionSoapQuery comicCollectionSoapQuery;

	private final ComicCollectionBaseSoapMapper comicCollectionBaseSoapMapper;

	private final ComicCollectionFullSoapMapper comicCollectionFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public ComicCollectionSoapReader(ComicCollectionSoapQuery comicCollectionSoapQuery, ComicCollectionBaseSoapMapper comicCollectionBaseSoapMapper,
			ComicCollectionFullSoapMapper comicCollectionFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.comicCollectionSoapQuery = comicCollectionSoapQuery;
		this.comicCollectionBaseSoapMapper = comicCollectionBaseSoapMapper;
		this.comicCollectionFullSoapMapper = comicCollectionFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public ComicCollectionBaseResponse readBase(ComicCollectionBaseRequest input) {
		Page<ComicCollection> comicCollectionPage = comicCollectionSoapQuery.query(input);
		ComicCollectionBaseResponse comicCollectionResponse = new ComicCollectionBaseResponse();
		comicCollectionResponse.setPage(pageMapper.fromPageToSoapResponsePage(comicCollectionPage));
		comicCollectionResponse.setSort(sortMapper.map(input.getSort()));
		comicCollectionResponse.getComicCollections().addAll(comicCollectionBaseSoapMapper.mapBase(comicCollectionPage.getContent()));
		return comicCollectionResponse;
	}

	@Override
	public ComicCollectionFullResponse readFull(ComicCollectionFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<ComicCollection> comicCollectionPage = comicCollectionSoapQuery.query(input);
		ComicCollectionFullResponse comicCollectionFullResponse = new ComicCollectionFullResponse();
		comicCollectionFullResponse.setComicCollection(comicCollectionFullSoapMapper
				.mapFull(Iterables.getOnlyElement(comicCollectionPage.getContent(), null)));
		return comicCollectionFullResponse;
	}

}
