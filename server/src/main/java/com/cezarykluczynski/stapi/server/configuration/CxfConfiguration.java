package com.cezarykluczynski.stapi.server.configuration;

import com.cezarykluczynski.stapi.server.astronomicalObject.endpoint.AstronomicalObjectRestEndpoint;
import com.cezarykluczynski.stapi.server.character.endpoint.CharacterRestEndpoint;
import com.cezarykluczynski.stapi.server.comicCollection.endpoint.ComicCollectionRestEndpoint;
import com.cezarykluczynski.stapi.server.comicSeries.endpoint.ComicSeriesRestEndpoint;
import com.cezarykluczynski.stapi.server.comicStrip.endpoint.ComicStripRestEndpoint;
import com.cezarykluczynski.stapi.server.comics.endpoint.ComicsRestEndpoint;
import com.cezarykluczynski.stapi.server.common.converter.LocalDateRestParamConverterProvider;
import com.cezarykluczynski.stapi.server.common.throttle.rest.RestExceptionMapper;
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDExceptionMapper;
import com.cezarykluczynski.stapi.server.company.endpoint.CompanyRestEndpoint;
import com.cezarykluczynski.stapi.server.configuration.interceptor.ApiThrottlingInterceptor;
import com.cezarykluczynski.stapi.server.episode.endpoint.EpisodeRestEndpoint;
import com.cezarykluczynski.stapi.server.food.endpoint.FoodRestEndpoint;
import com.cezarykluczynski.stapi.server.location.endpoint.LocationRestEndpoint;
import com.cezarykluczynski.stapi.server.movie.endpoint.MovieRestEndpoint;
import com.cezarykluczynski.stapi.server.organization.endpoint.OrganizationRestEndpoint;
import com.cezarykluczynski.stapi.server.performer.endpoint.PerformerRestEndpoint;
import com.cezarykluczynski.stapi.server.series.endpoint.SeriesRestEndpoint;
import com.cezarykluczynski.stapi.server.species.endpoint.SpeciesRestEndpoint;
import com.cezarykluczynski.stapi.server.staff.endpoint.StaffRestEndpoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.collect.Lists;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.inject.Inject;

@Configuration
@ImportResource({
		"classpath:META-INF/cxf/cxf.xml",
		"classpath:META-INF/cxf/cxf-servlet.xml"
})
public class CxfConfiguration extends SpringBootServletInitializer {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public ServletRegistrationBean cxfServletRegistrationBean() {
		return new ServletRegistrationBean(new CXFServlet(), "/api/*");
	}

	@Bean
	public Server cxfServer() {
		JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
		factory.setBus(applicationContext.getBean(SpringBus.class));
		factory.setProviders(Lists.newArrayList(
				new JacksonJsonProvider(getObjectMapper()),
				new CxfRestPrettyPrintContainerResponseFilter(),
				new LocalDateRestParamConverterProvider(),
				new RestExceptionMapper(),
				new MissingUIDExceptionMapper()));
		factory.setInInterceptors(Lists.newArrayList(applicationContext.getBean(ApiThrottlingInterceptor.class)));
		factory.setServiceBeans(Lists.newArrayList(
				applicationContext.getBean(AstronomicalObjectRestEndpoint.class),
				applicationContext.getBean(CharacterRestEndpoint.class),
				applicationContext.getBean(ComicCollectionRestEndpoint.class),
				applicationContext.getBean(ComicsRestEndpoint.class),
				applicationContext.getBean(ComicSeriesRestEndpoint.class),
				applicationContext.getBean(ComicStripRestEndpoint.class),
				applicationContext.getBean(CompanyRestEndpoint.class),
				applicationContext.getBean(EpisodeRestEndpoint.class),
				applicationContext.getBean(MovieRestEndpoint.class),
				applicationContext.getBean(PerformerRestEndpoint.class),
				applicationContext.getBean(SeriesRestEndpoint.class),
				applicationContext.getBean(SpeciesRestEndpoint.class),
				applicationContext.getBean(StaffRestEndpoint.class),
				applicationContext.getBean(OrganizationRestEndpoint.class),
				applicationContext.getBean(FoodRestEndpoint.class),
				applicationContext.getBean(LocationRestEndpoint.class)
		));
		return factory.create();
	}

	private ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		return mapper;
	}

}
