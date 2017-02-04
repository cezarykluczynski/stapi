package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectPortType;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectService;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterPortType;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterService;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesService;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyPortType;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyService;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodePortType;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeService;
import com.cezarykluczynski.stapi.client.v1.soap.MoviePortType;
import com.cezarykluczynski.stapi.client.v1.soap.MovieService;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerPortType;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerService;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesService;
import com.cezarykluczynski.stapi.client.v1.soap.StaffPortType;
import com.cezarykluczynski.stapi.client.v1.soap.StaffService;
import lombok.Getter;

import javax.xml.ws.BindingProvider;
import java.util.Map;

public class StapiSoapPortTypesProvider extends AbstractStapiClient implements StapiClient {

	private String apiUrl;

	@Getter
	private SeriesPortType seriesPortType;

	@Getter
	private PerformerPortType performerPortType;

	@Getter
	private StaffPortType staffPortType;

	@Getter
	private CharacterPortType characterPortType;

	@Getter
	private EpisodePortType episodePortType;

	@Getter
	private MoviePortType moviePortType;

	@Getter
	private AstronomicalObjectPortType astronomicalObjectPortType;

	@Getter
	private CompanyPortType companyPortType;

	@Getter
	private ComicSeriesPortType comicSeriesPortType;

	public StapiSoapPortTypesProvider() {
		seriesPortType = new SeriesService().getSeriesPortType();
		performerPortType = new PerformerService().getPerformerPortType();
		staffPortType = new StaffService().getStaffPortType();
		characterPortType = new CharacterService().getCharacterPortType();
		episodePortType = new EpisodeService().getEpisodePortType();
		moviePortType = new MovieService().getMoviePortType();
		astronomicalObjectPortType = new AstronomicalObjectService().getAstronomicalObjectPortType();
		companyPortType = new CompanyService().getCompanyPortType();
		comicSeriesPortType = new ComicSeriesService().getComicSeriesPortType();
	}

	public StapiSoapPortTypesProvider(String apiUrl) {
		this.apiUrl = apiUrl;
		seriesPortType = (SeriesPortType) changeUrl(new SeriesService().getSeriesPortType());
		performerPortType = (PerformerPortType) changeUrl(new PerformerService().getPerformerPortType());
		staffPortType = (StaffPortType) changeUrl(new StaffService().getStaffPortType());
		characterPortType = (CharacterPortType) changeUrl(new CharacterService().getCharacterPortType());
		episodePortType = (EpisodePortType) changeUrl(new EpisodeService().getEpisodePortType());
		moviePortType = (MoviePortType) changeUrl(new MovieService().getMoviePortType());
		astronomicalObjectPortType = (AstronomicalObjectPortType) changeUrl(new AstronomicalObjectService().getAstronomicalObjectPortType());
		companyPortType = (CompanyPortType) changeUrl(new CompanyService().getCompanyPortType());
		comicSeriesPortType = (ComicSeriesPortType) changeUrl(new ComicSeriesService().getComicSeriesPortType());
	}

	private Object changeUrl(Object service) {
		BindingProvider bindingProvider = (BindingProvider) service;
		Map<String, Object> requestContext = bindingProvider.getRequestContext();
		String newServiceUrl = changeBaseUrl(apiUrl,
				(String) requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newServiceUrl);
		return service;
	}

}
