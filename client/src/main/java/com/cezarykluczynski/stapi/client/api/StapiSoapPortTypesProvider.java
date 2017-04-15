package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectPortType;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectService;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterPortType;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterService;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionService;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesService;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripService;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsService;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyPortType;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyService;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodePortType;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeService;
import com.cezarykluczynski.stapi.client.v1.soap.FoodPortType;
import com.cezarykluczynski.stapi.client.v1.soap.FoodService;
import com.cezarykluczynski.stapi.client.v1.soap.LocationPortType;
import com.cezarykluczynski.stapi.client.v1.soap.LocationService;
import com.cezarykluczynski.stapi.client.v1.soap.MoviePortType;
import com.cezarykluczynski.stapi.client.v1.soap.MovieService;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationPortType;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationService;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerPortType;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerService;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesService;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesService;
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

	@Getter
	private ComicsPortType comicsPortType;

	@Getter
	private ComicStripPortType comicStripPortType;

	@Getter
	private ComicCollectionPortType comicCollectionPortType;

	@Getter
	private SpeciesPortType speciesPortType;

	@Getter
	private OrganizationPortType organizationPortType;

	@Getter
	private FoodPortType foodPortType;

	@Getter
	private LocationPortType locationPortType;

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
		comicsPortType = new ComicsService().getComicsPortType();
		comicStripPortType = new ComicStripService().getComicStripPortType();
		comicCollectionPortType = new ComicCollectionService().getComicCollectionPortType();
		speciesPortType = new SpeciesService().getSpeciesPortType();
		organizationPortType = new OrganizationService().getOrganizationPortType();
		foodPortType = new FoodService().getFoodPortType();
		locationPortType = new LocationService().getLocationPortType();
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
		comicsPortType = (ComicsPortType) changeUrl(new ComicsService().getComicsPortType());
		comicStripPortType = (ComicStripPortType) changeUrl(new ComicStripService().getComicStripPortType());
		comicCollectionPortType = (ComicCollectionPortType) changeUrl(new ComicCollectionService().getComicCollectionPortType());
		speciesPortType = (SpeciesPortType) changeUrl(new SpeciesService().getSpeciesPortType());
		organizationPortType = (OrganizationPortType) changeUrl(new OrganizationService().getOrganizationPortType());
		foodPortType = (FoodPortType) changeUrl(new FoodService().getFoodPortType());
		locationPortType = (LocationPortType) changeUrl(new LocationService().getLocationPortType());
	}

	private Object changeUrl(Object service) {
		BindingProvider bindingProvider = (BindingProvider) service;
		Map<String, Object> requestContext = bindingProvider.getRequestContext();
		String newServiceUrl = changeBaseUrl(apiUrl, (String) requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newServiceUrl);
		return service;
	}

}
