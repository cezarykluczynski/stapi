package com.cezarykluczynski.stapi.server.configuration

import com.cezarykluczynski.stapi.server.astronomicalObject.endpoint.AstronomicalObjectRestEndpoint
import com.cezarykluczynski.stapi.server.book.endpoint.BookRestEndpoint
import com.cezarykluczynski.stapi.server.bookSeries.endpoint.BookSeriesRestEndpoint
import com.cezarykluczynski.stapi.server.character.endpoint.CharacterRestEndpoint
import com.cezarykluczynski.stapi.server.comicCollection.endpoint.ComicCollectionRestEndpoint
import com.cezarykluczynski.stapi.server.comicSeries.endpoint.ComicSeriesRestEndpoint
import com.cezarykluczynski.stapi.server.comicStrip.endpoint.ComicStripRestEndpoint
import com.cezarykluczynski.stapi.server.comics.endpoint.ComicsRestEndpoint
import com.cezarykluczynski.stapi.server.company.endpoint.CompanyRestEndpoint
import com.cezarykluczynski.stapi.server.configuration.interceptor.ApiThrottlingInterceptor
import com.cezarykluczynski.stapi.server.episode.endpoint.EpisodeRestEndpoint
import com.cezarykluczynski.stapi.server.food.endpoint.FoodRestEndpoint
import com.cezarykluczynski.stapi.server.location.endpoint.LocationRestEndpoint
import com.cezarykluczynski.stapi.server.movie.endpoint.MovieRestEndpoint
import com.cezarykluczynski.stapi.server.organization.endpoint.OrganizationRestEndpoint
import com.cezarykluczynski.stapi.server.performer.endpoint.PerformerRestEndpoint
import com.cezarykluczynski.stapi.server.series.endpoint.SeriesRestEndpoint
import com.cezarykluczynski.stapi.server.species.endpoint.SpeciesRestEndpoint
import com.cezarykluczynski.stapi.server.staff.endpoint.StaffRestEndpoint
import com.google.common.collect.Maps
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.endpoint.Server
import org.apache.cxf.endpoint.ServerImpl
import org.apache.cxf.transport.servlet.CXFServlet
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class CxfConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private CxfConfiguration cxfConfiguration

	void setup() {
		applicationContextMock = Mock()
		cxfConfiguration = new CxfConfiguration()
	}

	void "ServletRegistrationBean is created"() {
		when:
		ServletRegistrationBean servletRegistrationBean = cxfConfiguration.cxfServletRegistrationBean()

		then:
		servletRegistrationBean.servlet instanceof CXFServlet
		servletRegistrationBean.urlMappings.contains('/api/*')
	}

	@SuppressWarnings(['EmptyCatchBlock', 'CatchException'])
	void "CxfServer is created"() {
		given:
		Map<String, Object> serviceBeans = Maps.newHashMap()
		serviceBeans.put('SeriesRestEndpoint', new SeriesRestEndpoint(null))
		cxfConfiguration.applicationContext = applicationContextMock
		ApiThrottlingInterceptor apiThrottlingInterceptor = Mock()
		AstronomicalObjectRestEndpoint astronomicalObjectRestEndpoint = Mock()
		CharacterRestEndpoint characterRestEndpoint = Mock()
		ComicCollectionRestEndpoint comicCollectionRestEndpoint = Mock()
		ComicsRestEndpoint comicsRestEndpoint = Mock()
		ComicSeriesRestEndpoint comicSeriesRestEndpoint = Mock()
		ComicStripRestEndpoint comicStripRestEndpoint = Mock()
		CompanyRestEndpoint companyRestEndpoint = Mock()
		EpisodeRestEndpoint episodeRestEndpoint = Mock()
		MovieRestEndpoint movieRestEndpoint = Mock()
		PerformerRestEndpoint performerRestEndpoint = Mock()
		SeriesRestEndpoint seriesRestEndpoint = Mock()
		SpeciesRestEndpoint speciesRestEndpoint = Mock()
		StaffRestEndpoint staffRestEndpoint = Mock()
		OrganizationRestEndpoint organizationRestEndpoint = Mock()
		FoodRestEndpoint foodRestEndpoint = Mock()
		LocationRestEndpoint locationRestEndpoint = Mock()
		BookSeriesRestEndpoint bookSeriesRestEndpoint = Mock()
		BookRestEndpoint bookRestEndpoint = Mock()

		when:
		Server server = cxfConfiguration.cxfServer()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> new SpringBus()
		1 * applicationContextMock.getBean(ApiThrottlingInterceptor) >> apiThrottlingInterceptor
		1 * applicationContextMock.getBean(AstronomicalObjectRestEndpoint) >> astronomicalObjectRestEndpoint
		1 * applicationContextMock.getBean(CharacterRestEndpoint) >> characterRestEndpoint
		1 * applicationContextMock.getBean(ComicCollectionRestEndpoint) >> comicCollectionRestEndpoint
		1 * applicationContextMock.getBean(ComicsRestEndpoint) >> comicsRestEndpoint
		1 * applicationContextMock.getBean(ComicSeriesRestEndpoint) >> comicSeriesRestEndpoint
		1 * applicationContextMock.getBean(ComicStripRestEndpoint) >> comicStripRestEndpoint
		1 * applicationContextMock.getBean(CompanyRestEndpoint) >> companyRestEndpoint
		1 * applicationContextMock.getBean(EpisodeRestEndpoint) >> episodeRestEndpoint
		1 * applicationContextMock.getBean(MovieRestEndpoint) >> movieRestEndpoint
		1 * applicationContextMock.getBean(PerformerRestEndpoint) >> performerRestEndpoint
		1 * applicationContextMock.getBean(SeriesRestEndpoint) >> seriesRestEndpoint
		1 * applicationContextMock.getBean(SpeciesRestEndpoint) >> speciesRestEndpoint
		1 * applicationContextMock.getBean(StaffRestEndpoint) >> staffRestEndpoint
		1 * applicationContextMock.getBean(OrganizationRestEndpoint) >> organizationRestEndpoint
		1 * applicationContextMock.getBean(FoodRestEndpoint) >> foodRestEndpoint
		1 * applicationContextMock.getBean(LocationRestEndpoint) >> locationRestEndpoint
		1 * applicationContextMock.getBean(BookSeriesRestEndpoint) >> bookSeriesRestEndpoint
		1 * applicationContextMock.getBean(BookRestEndpoint) >> bookRestEndpoint
		0 * _
		server instanceof ServerImpl
		server.started

		cleanup:
		try {
			server.destroy()
		} catch (Exception e) {
		}
	}

}
