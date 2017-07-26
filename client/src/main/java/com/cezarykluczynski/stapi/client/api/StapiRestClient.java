package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.v1.rest.api.AstronomicalObjectApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.BookApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.BookSeriesApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.CharacterApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.ComicCollectionApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.ComicSeriesApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.ComicStripApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.ComicsApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.CompanyApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.EpisodeApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.FoodApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.LiteratureApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.LocationApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.MagazineApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.MagazineSeriesApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.MovieApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.OrganizationApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.PerformerApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.SeasonApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.SeriesApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.SpeciesApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.StaffApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.TradingCardDeckApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.TradingCardSetApi;
import com.cezarykluczynski.stapi.client.v1.rest.api.VideoReleaseApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiClient;
import lombok.Getter;

public class StapiRestClient extends AbstractStapiClient implements StapiClient {

	private String apiUrl;

	private ApiClient apiClient;

	@Getter
	private SeriesApi seriesApi;

	@Getter
	private PerformerApi performerApi;

	@Getter
	private StaffApi staffApi;

	@Getter
	private EpisodeApi episodeApi;

	@Getter
	private CharacterApi characterApi;

	@Getter
	private MovieApi movieApi;

	@Getter
	private AstronomicalObjectApi astronomicalObjectApi;

	@Getter
	private CompanyApi companyApi;

	@Getter
	private ComicSeriesApi comicSeriesApi;

	@Getter
	private ComicsApi comicsApi;

	@Getter
	private ComicStripApi comicStripApi;

	@Getter
	private ComicCollectionApi comicCollectionApi;

	@Getter
	private SpeciesApi speciesApi;

	@Getter
	private OrganizationApi organizationApi;

	@Getter
	private FoodApi foodApi;

	@Getter
	private LocationApi locationApi;

	@Getter
	private BookSeriesApi bookSeriesApi;

	@Getter
	private BookApi bookApi;

	@Getter
	private MagazineApi magazineApi;

	@Getter
	private MagazineSeriesApi magazineSeriesApi;

	@Getter
	private LiteratureApi literatureApi;

	@Getter
	private SeasonApi seasonApi;

	@Getter
	private VideoReleaseApi videoReleaseApi;

	@Getter
	private TradingCardSetApi tradingCardSetApi;

	@Getter
	private TradingCardDeckApi tradingCardDeckApi;

	public StapiRestClient() {
		seriesApi = new SeriesApi();
		performerApi = new PerformerApi();
		staffApi = new StaffApi();
		characterApi = new CharacterApi();
		episodeApi = new EpisodeApi();
		movieApi = new MovieApi();
		astronomicalObjectApi = new AstronomicalObjectApi();
		companyApi = new CompanyApi();
		comicSeriesApi = new ComicSeriesApi();
		comicsApi = new ComicsApi();
		comicStripApi = new ComicStripApi();
		comicCollectionApi = new ComicCollectionApi();
		speciesApi = new SpeciesApi();
		organizationApi = new OrganizationApi();
		foodApi = new FoodApi();
		locationApi = new LocationApi();
		bookSeriesApi = new BookSeriesApi();
		bookApi = new BookApi();
		magazineApi = new MagazineApi();
		magazineSeriesApi = new MagazineSeriesApi();
		literatureApi = new LiteratureApi();
		seasonApi = new SeasonApi();
		videoReleaseApi = new VideoReleaseApi();
		tradingCardSetApi = new TradingCardSetApi();
		tradingCardDeckApi = new TradingCardDeckApi();
	}

	public StapiRestClient(String apiUrl) {
		this.apiUrl = apiUrl;
		createApiClient();
		seriesApi = new SeriesApi(apiClient);
		performerApi = new PerformerApi(apiClient);
		staffApi = new StaffApi(apiClient);
		characterApi = new CharacterApi(apiClient);
		episodeApi = new EpisodeApi(apiClient);
		movieApi = new MovieApi(apiClient);
		astronomicalObjectApi = new AstronomicalObjectApi(apiClient);
		companyApi = new CompanyApi(apiClient);
		comicSeriesApi = new ComicSeriesApi(apiClient);
		comicsApi = new ComicsApi(apiClient);
		comicStripApi = new ComicStripApi(apiClient);
		comicCollectionApi = new ComicCollectionApi(apiClient);
		speciesApi = new SpeciesApi(apiClient);
		organizationApi = new OrganizationApi(apiClient);
		foodApi = new FoodApi(apiClient);
		locationApi = new LocationApi(apiClient);
		bookSeriesApi = new BookSeriesApi(apiClient);
		bookApi = new BookApi(apiClient);
		magazineApi = new MagazineApi(apiClient);
		magazineSeriesApi = new MagazineSeriesApi(apiClient);
		literatureApi = new LiteratureApi(apiClient);
		seasonApi = new SeasonApi(apiClient);
		videoReleaseApi = new VideoReleaseApi(apiClient);
		tradingCardSetApi = new TradingCardSetApi(apiClient);
		tradingCardDeckApi = new TradingCardDeckApi(apiClient);
	}

	private void createApiClient() {
		apiClient = new ApiClient();
		apiClient.setBasePath(changeBaseUrl(apiUrl, apiClient.getBasePath()));
		apiClient.setConnectTimeout(10000);
	}

}
