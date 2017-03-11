package com.cezarykluczynski.stapi.server.episode.configuration;

import com.cezarykluczynski.stapi.server.episode.endpoint.EpisodeRestEndpoint;
import com.cezarykluczynski.stapi.server.episode.endpoint.EpisodeSoapEndpoint;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeRestMapper;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeSoapMapper;
import com.cezarykluczynski.stapi.server.episode.reader.EpisodeRestReader;
import com.cezarykluczynski.stapi.server.episode.reader.EpisodeSoapReader;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.mapstruct.factory.Mappers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class EpisodeConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Bean
	public Endpoint episodeSoapEndpoint() {
		Bus bus = applicationContext.getBean(SpringBus.class);
		Object implementor = new EpisodeSoapEndpoint(applicationContext.getBean(EpisodeSoapReader.class));
		EndpointImpl endpoint = new EndpointImpl(bus, implementor);
		endpoint.publish("/v1/soap/episode");
		return endpoint;
	}

	@Bean
	public EpisodeRestEndpoint episodeRestEndpoint() {
		return new EpisodeRestEndpoint(applicationContext.getBean(EpisodeRestReader.class));
	}

	@Bean
	public EpisodeSoapMapper episodeSoapMapper() {
		return Mappers.getMapper(EpisodeSoapMapper.class);
	}

	@Bean
	public EpisodeRestMapper episodeRestMapper() {
		return Mappers.getMapper(EpisodeRestMapper.class);
	}

}
