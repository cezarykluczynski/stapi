package com.cezarykluczynski.stapi.server.literature.reader

import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBase
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFull
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.literature.entity.Literature
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureBaseSoapMapper
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureFullSoapMapper
import com.cezarykluczynski.stapi.server.literature.query.LiteratureSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class LiteratureSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private LiteratureSoapQuery literatureSoapQueryBuilderMock

	private LiteratureBaseSoapMapper literatureBaseSoapMapperMock

	private LiteratureFullSoapMapper literatureFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private LiteratureSoapReader literatureSoapReader

	void setup() {
		literatureSoapQueryBuilderMock = Mock()
		literatureBaseSoapMapperMock = Mock()
		literatureFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		literatureSoapReader = new LiteratureSoapReader(literatureSoapQueryBuilderMock, literatureBaseSoapMapperMock,
				literatureFullSoapMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Literature> literatureList = Lists.newArrayList()
		Page<Literature> literaturePage = Mock()
		List<LiteratureBase> soapLiteratureList = Lists.newArrayList(new LiteratureBase(uid: UID))
		LiteratureBaseRequest literatureBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		LiteratureBaseResponse literatureResponse = literatureSoapReader.readBase(literatureBaseRequest)

		then:
		1 * literatureSoapQueryBuilderMock.query(literatureBaseRequest) >> literaturePage
		1 * literaturePage.content >> literatureList
		1 * pageMapperMock.fromPageToSoapResponsePage(literaturePage) >> responsePage
		1 * literatureBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * literatureBaseSoapMapperMock.mapBase(literatureList) >> soapLiteratureList
		0 * _
		literatureResponse.literature[0].uid == UID
		literatureResponse.page == responsePage
		literatureResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		LiteratureFull literatureFull = new LiteratureFull(uid: UID)
		Literature literature = Mock()
		Page<Literature> literaturePage = Mock()
		LiteratureFullRequest literatureFullRequest = new LiteratureFullRequest(uid: UID)

		when:
		LiteratureFullResponse literatureFullResponse = literatureSoapReader.readFull(literatureFullRequest)

		then:
		1 * literatureSoapQueryBuilderMock.query(literatureFullRequest) >> literaturePage
		1 * literaturePage.content >> Lists.newArrayList(literature)
		1 * literatureFullSoapMapperMock.mapFull(literature) >> literatureFull
		0 * _
		literatureFullResponse.literature.uid == UID
	}

	void "requires UID in full request"() {
		given:
		LiteratureFullRequest literatureFullRequest = Mock()

		when:
		literatureSoapReader.readFull(literatureFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
