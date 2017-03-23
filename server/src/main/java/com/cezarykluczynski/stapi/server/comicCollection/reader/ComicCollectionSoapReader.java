package com.cezarykluczynski.stapi.server.comicCollection.reader;

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionFullResponse;
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionFullSoapMapper;
import com.cezarykluczynski.stapi.server.comicCollection.query.ComicCollectionSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ComicCollectionSoapReader implements BaseReader<ComicCollectionBaseRequest, ComicCollectionBaseResponse>,
		FullReader<ComicCollectionFullRequest, ComicCollectionFullResponse> {

	private ComicCollectionSoapQuery comicCollectionSoapQuery;

	private ComicCollectionBaseSoapMapper comicCollectionBaseSoapMapper;

	private ComicCollectionFullSoapMapper comicCollectionFullSoapMapper;

	private PageMapper pageMapper;

	public ComicCollectionSoapReader(ComicCollectionSoapQuery comicCollectionSoapQuery, ComicCollectionBaseSoapMapper comicCollectionBaseSoapMapper,
			ComicCollectionFullSoapMapper comicCollectionFullSoapMapper, PageMapper pageMapper) {
		this.comicCollectionSoapQuery = comicCollectionSoapQuery;
		this.comicCollectionBaseSoapMapper = comicCollectionBaseSoapMapper;
		this.comicCollectionFullSoapMapper = comicCollectionFullSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public ComicCollectionBaseResponse readBase(ComicCollectionBaseRequest input) {
		Page<ComicCollection> comicCollectionPage = comicCollectionSoapQuery.query(input);
		ComicCollectionBaseResponse comicCollectionResponse = new ComicCollectionBaseResponse();
		comicCollectionResponse.setPage(pageMapper.fromPageToSoapResponsePage(comicCollectionPage));
		comicCollectionResponse.getComicCollections().addAll(comicCollectionBaseSoapMapper.mapBase(comicCollectionPage.getContent()));
		return comicCollectionResponse;
	}

	@Override
	public ComicCollectionFullResponse readFull(ComicCollectionFullRequest input) {
		Page<ComicCollection> comicCollectionPage = comicCollectionSoapQuery.query(input);
		ComicCollectionFullResponse comicCollectionFullResponse = new ComicCollectionFullResponse();
		comicCollectionFullResponse.setComicCollection(comicCollectionFullSoapMapper
				.mapFull(Iterables.getOnlyElement(comicCollectionPage.getContent(), null)));
		return comicCollectionFullResponse;
	}

}
