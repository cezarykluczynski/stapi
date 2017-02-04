package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectPortType;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyPortType;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodePortType;
import com.cezarykluczynski.stapi.client.v1.soap.MoviePortType;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.StaffPortType;
import lombok.Getter;

public class StapiSoapClient {

	private StapiSoapPortTypesProvider stapiSoapPortTypesProvider;

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

	public StapiSoapClient() {
		stapiSoapPortTypesProvider = new StapiSoapPortTypesProvider();
		bindPortTypes();
	}

	public StapiSoapClient(String apiUrl) {
		stapiSoapPortTypesProvider = new StapiSoapPortTypesProvider(apiUrl);
		bindPortTypes();
	}

	private void bindPortTypes() {
		seriesPortType = stapiSoapPortTypesProvider.getSeriesPortType();
		performerPortType = stapiSoapPortTypesProvider.getPerformerPortType();
		staffPortType = stapiSoapPortTypesProvider.getStaffPortType();
		characterPortType = stapiSoapPortTypesProvider.getCharacterPortType();
		episodePortType = stapiSoapPortTypesProvider.getEpisodePortType();
		moviePortType = stapiSoapPortTypesProvider.getMoviePortType();
		astronomicalObjectPortType = stapiSoapPortTypesProvider.getAstronomicalObjectPortType();
		companyPortType = stapiSoapPortTypesProvider.getCompanyPortType();
		comicSeriesPortType = stapiSoapPortTypesProvider.getComicSeriesPortType();
	}

}
