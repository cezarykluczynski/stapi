package com.cezarykluczynski.stapi.server.configuration

import com.cezarykluczynski.stapi.server.astronomicalObject.endpoint.AstronomicalObjectRestEndpoint
import com.cezarykluczynski.stapi.server.character.endpoint.CharacterRestEndpoint
import com.cezarykluczynski.stapi.server.comicCollection.endpoint.ComicCollectionRestEndpoint
import com.cezarykluczynski.stapi.server.comicSeries.endpoint.ComicSeriesRestEndpoint
import com.cezarykluczynski.stapi.server.comicStrip.endpoint.ComicStripRestEndpoint
import com.cezarykluczynski.stapi.server.comics.endpoint.ComicsRestEndpoint
import com.cezarykluczynski.stapi.server.company.endpoint.CompanyRestEndpoint
import com.cezarykluczynski.stapi.server.episode.endpoint.EpisodeRestEndpoint
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
		applicationContextMock = Mock(ApplicationContext)
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

		when:
		Server server = cxfConfiguration.cxfServer()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> new SpringBus()
		1 * applicationContextMock.getBean(AstronomicalObjectRestEndpoint) >> Mock(AstronomicalObjectRestEndpoint)
		1 * applicationContextMock.getBean(CharacterRestEndpoint) >> Mock(CharacterRestEndpoint)
		1 * applicationContextMock.getBean(ComicCollectionRestEndpoint) >> Mock(ComicCollectionRestEndpoint)
		1 * applicationContextMock.getBean(ComicsRestEndpoint) >> Mock(ComicsRestEndpoint)
		1 * applicationContextMock.getBean(ComicSeriesRestEndpoint) >> Mock(ComicSeriesRestEndpoint)
		1 * applicationContextMock.getBean(ComicStripRestEndpoint) >> Mock(ComicStripRestEndpoint)
		1 * applicationContextMock.getBean(CompanyRestEndpoint) >> Mock(CompanyRestEndpoint)
		1 * applicationContextMock.getBean(EpisodeRestEndpoint) >> Mock(EpisodeRestEndpoint)
		1 * applicationContextMock.getBean(MovieRestEndpoint) >> Mock(MovieRestEndpoint)
		1 * applicationContextMock.getBean(PerformerRestEndpoint) >> Mock(PerformerRestEndpoint)
		1 * applicationContextMock.getBean(SeriesRestEndpoint) >> Mock(SeriesRestEndpoint)
		1 * applicationContextMock.getBean(SpeciesRestEndpoint) >> Mock(SpeciesRestEndpoint)
		1 * applicationContextMock.getBean(StaffRestEndpoint) >> Mock(StaffRestEndpoint)
		1 * applicationContextMock.getBean(OrganizationRestEndpoint) >> Mock(OrganizationRestEndpoint)
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
