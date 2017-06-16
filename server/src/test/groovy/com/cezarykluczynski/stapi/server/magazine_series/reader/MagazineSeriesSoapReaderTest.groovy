package com.cezarykluczynski.stapi.server.magazine_series.reader

import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBase
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFull
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesBaseSoapMapper
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesFullSoapMapper
import com.cezarykluczynski.stapi.server.magazine_series.query.MagazineSeriesSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class MagazineSeriesSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private MagazineSeriesSoapQuery magazineSeriesSoapQueryBuilderMock

	private MagazineSeriesBaseSoapMapper magazineSeriesBaseSoapMapperMock

	private MagazineSeriesFullSoapMapper magazineSeriesFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private MagazineSeriesSoapReader magazineSeriesSoapReader

	void setup() {
		magazineSeriesSoapQueryBuilderMock = Mock()
		magazineSeriesBaseSoapMapperMock = Mock()
		magazineSeriesFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		magazineSeriesSoapReader = new MagazineSeriesSoapReader(magazineSeriesSoapQueryBuilderMock, magazineSeriesBaseSoapMapperMock,
				magazineSeriesFullSoapMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<MagazineSeries> magazineSeriesList = Lists.newArrayList()
		Page<MagazineSeries> magazineSeriesPage = Mock()
		List<MagazineSeriesBase> soapMagazineSeriesList = Lists.newArrayList(new MagazineSeriesBase(uid: UID))
		MagazineSeriesBaseRequest magazineSeriesBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		MagazineSeriesBaseResponse magazineSeriesResponse = magazineSeriesSoapReader.readBase(magazineSeriesBaseRequest)

		then:
		1 * magazineSeriesSoapQueryBuilderMock.query(magazineSeriesBaseRequest) >> magazineSeriesPage
		1 * magazineSeriesPage.content >> magazineSeriesList
		1 * pageMapperMock.fromPageToSoapResponsePage(magazineSeriesPage) >> responsePage
		1 * magazineSeriesBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * magazineSeriesBaseSoapMapperMock.mapBase(magazineSeriesList) >> soapMagazineSeriesList
		0 * _
		magazineSeriesResponse.magazineSeries[0].uid == UID
		magazineSeriesResponse.page == responsePage
		magazineSeriesResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		MagazineSeriesFull magazineSeriesFull = new MagazineSeriesFull(uid: UID)
		MagazineSeries magazineSeries = Mock()
		Page<MagazineSeries> magazineSeriesPage = Mock()
		MagazineSeriesFullRequest magazineSeriesFullRequest = new MagazineSeriesFullRequest(uid: UID)

		when:
		MagazineSeriesFullResponse magazineSeriesFullResponse = magazineSeriesSoapReader.readFull(magazineSeriesFullRequest)

		then:
		1 * magazineSeriesSoapQueryBuilderMock.query(magazineSeriesFullRequest) >> magazineSeriesPage
		1 * magazineSeriesPage.content >> Lists.newArrayList(magazineSeries)
		1 * magazineSeriesFullSoapMapperMock.mapFull(magazineSeries) >> magazineSeriesFull
		0 * _
		magazineSeriesFullResponse.magazineSeries.uid == UID
	}

	void "requires UID in full request"() {
		given:
		MagazineSeriesFullRequest magazineSeriesFullRequest = Mock()

		when:
		magazineSeriesSoapReader.readFull(magazineSeriesFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
