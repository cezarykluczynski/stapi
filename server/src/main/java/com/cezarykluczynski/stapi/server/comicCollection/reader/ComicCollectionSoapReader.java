package com.cezarykluczynski.stapi.server.comicCollection.reader;

import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionResponse;
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection;
import com.cezarykluczynski.stapi.server.comicCollection.mapper.ComicCollectionSoapMapper;
import com.cezarykluczynski.stapi.server.comicCollection.query.ComicCollectionSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ComicCollectionSoapReader implements BaseReader<ComicCollectionRequest, ComicCollectionResponse> {

	private ComicCollectionSoapQuery comicCollectionSoapQuery;

	private ComicCollectionSoapMapper comicCollectionSoapMapper;

	private PageMapper pageMapper;

	public ComicCollectionSoapReader(ComicCollectionSoapQuery comicCollectionSoapQuery, ComicCollectionSoapMapper comicCollectionSoapMapper,
			PageMapper pageMapper) {
		this.comicCollectionSoapQuery = comicCollectionSoapQuery;
		this.comicCollectionSoapMapper = comicCollectionSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public ComicCollectionResponse readBase(ComicCollectionRequest input) {
		Page<ComicCollection> comicCollectionPage = comicCollectionSoapQuery.query(input);
		ComicCollectionResponse comicCollectionResponse = new ComicCollectionResponse();
		comicCollectionResponse.setPage(pageMapper.fromPageToSoapResponsePage(comicCollectionPage));
		comicCollectionResponse.getComicCollections().addAll(comicCollectionSoapMapper.map(comicCollectionPage.getContent()));
		return comicCollectionResponse;
	}

}
