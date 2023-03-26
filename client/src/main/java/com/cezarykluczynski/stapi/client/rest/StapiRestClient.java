package com.cezarykluczynski.stapi.client.rest;

import com.cezarykluczynski.stapi.client.rest.facade.Animal;
import com.cezarykluczynski.stapi.client.rest.facade.AstronomicalObject;
import com.cezarykluczynski.stapi.client.rest.facade.Book;
import com.cezarykluczynski.stapi.client.rest.facade.BookCollection;
import com.cezarykluczynski.stapi.client.rest.facade.BookSeries;
import com.cezarykluczynski.stapi.client.rest.facade.Character;
import com.cezarykluczynski.stapi.client.rest.facade.ComicCollection;
import com.cezarykluczynski.stapi.client.rest.facade.ComicSeries;
import com.cezarykluczynski.stapi.client.rest.facade.ComicStrip;
import com.cezarykluczynski.stapi.client.rest.facade.Comics;
import com.cezarykluczynski.stapi.client.rest.facade.Company;
import com.cezarykluczynski.stapi.client.rest.facade.Conflict;
import com.cezarykluczynski.stapi.client.rest.facade.DataVersion;
import com.cezarykluczynski.stapi.client.rest.facade.Element;
import com.cezarykluczynski.stapi.client.rest.facade.Episode;
import com.cezarykluczynski.stapi.client.rest.facade.Food;
import com.cezarykluczynski.stapi.client.rest.facade.Literature;
import com.cezarykluczynski.stapi.client.rest.facade.Location;
import com.cezarykluczynski.stapi.client.rest.facade.Magazine;
import com.cezarykluczynski.stapi.client.rest.facade.MagazineSeries;
import com.cezarykluczynski.stapi.client.rest.facade.Material;
import com.cezarykluczynski.stapi.client.rest.facade.MedicalCondition;
import com.cezarykluczynski.stapi.client.rest.facade.Movie;
import com.cezarykluczynski.stapi.client.rest.facade.Occupation;
import com.cezarykluczynski.stapi.client.rest.facade.Organization;
import com.cezarykluczynski.stapi.client.rest.facade.Performer;
import com.cezarykluczynski.stapi.client.rest.facade.Season;
import com.cezarykluczynski.stapi.client.rest.facade.Series;
import com.cezarykluczynski.stapi.client.rest.facade.Soundtrack;
import com.cezarykluczynski.stapi.client.rest.facade.Spacecraft;
import com.cezarykluczynski.stapi.client.rest.facade.SpacecraftClass;
import com.cezarykluczynski.stapi.client.rest.facade.Species;
import com.cezarykluczynski.stapi.client.rest.facade.Staff;
import com.cezarykluczynski.stapi.client.rest.facade.Technology;
import com.cezarykluczynski.stapi.client.rest.facade.Title;
import com.cezarykluczynski.stapi.client.rest.facade.TradingCard;
import com.cezarykluczynski.stapi.client.rest.facade.TradingCardDeck;
import com.cezarykluczynski.stapi.client.rest.facade.TradingCardSet;
import com.cezarykluczynski.stapi.client.rest.facade.VideoGame;
import com.cezarykluczynski.stapi.client.rest.facade.VideoRelease;
import com.cezarykluczynski.stapi.client.rest.facade.Weapon;
import com.cezarykluczynski.stapi.client.rest.api.AnimalApi;
import com.cezarykluczynski.stapi.client.rest.api.AstronomicalObjectApi;
import com.cezarykluczynski.stapi.client.rest.api.BookApi;
import com.cezarykluczynski.stapi.client.rest.api.BookCollectionApi;
import com.cezarykluczynski.stapi.client.rest.api.BookSeriesApi;
import com.cezarykluczynski.stapi.client.rest.api.CharacterApi;
import com.cezarykluczynski.stapi.client.rest.api.ComicCollectionApi;
import com.cezarykluczynski.stapi.client.rest.api.ComicSeriesApi;
import com.cezarykluczynski.stapi.client.rest.api.ComicStripApi;
import com.cezarykluczynski.stapi.client.rest.api.ComicsApi;
import com.cezarykluczynski.stapi.client.rest.api.CompanyApi;
import com.cezarykluczynski.stapi.client.rest.api.ConflictApi;
import com.cezarykluczynski.stapi.client.rest.api.DataVersionApi;
import com.cezarykluczynski.stapi.client.rest.api.ElementApi;
import com.cezarykluczynski.stapi.client.rest.api.EpisodeApi;
import com.cezarykluczynski.stapi.client.rest.api.FoodApi;
import com.cezarykluczynski.stapi.client.rest.api.LiteratureApi;
import com.cezarykluczynski.stapi.client.rest.api.LocationApi;
import com.cezarykluczynski.stapi.client.rest.api.MagazineApi;
import com.cezarykluczynski.stapi.client.rest.api.MagazineSeriesApi;
import com.cezarykluczynski.stapi.client.rest.api.MaterialApi;
import com.cezarykluczynski.stapi.client.rest.api.MedicalConditionApi;
import com.cezarykluczynski.stapi.client.rest.api.MovieApi;
import com.cezarykluczynski.stapi.client.rest.api.OccupationApi;
import com.cezarykluczynski.stapi.client.rest.api.OrganizationApi;
import com.cezarykluczynski.stapi.client.rest.api.PerformerApi;
import com.cezarykluczynski.stapi.client.rest.api.SeasonApi;
import com.cezarykluczynski.stapi.client.rest.api.SeriesApi;
import com.cezarykluczynski.stapi.client.rest.api.SoundtrackApi;
import com.cezarykluczynski.stapi.client.rest.api.SpacecraftApi;
import com.cezarykluczynski.stapi.client.rest.api.SpacecraftClassApi;
import com.cezarykluczynski.stapi.client.rest.api.SpeciesApi;
import com.cezarykluczynski.stapi.client.rest.api.StaffApi;
import com.cezarykluczynski.stapi.client.rest.api.TechnologyApi;
import com.cezarykluczynski.stapi.client.rest.api.TitleApi;
import com.cezarykluczynski.stapi.client.rest.api.TradingCardApi;
import com.cezarykluczynski.stapi.client.rest.api.TradingCardDeckApi;
import com.cezarykluczynski.stapi.client.rest.api.TradingCardSetApi;
import com.cezarykluczynski.stapi.client.rest.api.VideoGameApi;
import com.cezarykluczynski.stapi.client.rest.api.VideoReleaseApi;
import com.cezarykluczynski.stapi.client.rest.api.WeaponApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiClient;
import lombok.Getter;

public class StapiRestClient {

	public static String CANONICAL_API_HTTPS_URL = "https://stapi.co/";

	@Getter
	private final String apiUrl;

	@Getter
	private final AnimalApi animalApi;

	@Getter
	private final AstronomicalObjectApi astronomicalObjectApi;

	@Getter
	private final BookApi bookApi;

	@Getter
	private final BookCollectionApi bookCollectionApi;

	@Getter
	private final BookSeriesApi bookSeriesApi;

	@Getter
	private final CharacterApi characterApi;

	@Getter
	private final ComicCollectionApi comicCollectionApi;

	@Getter
	private final ComicsApi comicsApi;

	@Getter
	private final ComicSeriesApi comicSeriesApi;

	@Getter
	private final ComicStripApi comicStripApi;

	@Getter
	private final CompanyApi companyApi;

	@Getter
	private final ConflictApi conflictApi;

	@Getter
	private final DataVersionApi dataVersionApi;

	@Getter
	private final ElementApi elementApi;

	@Getter
	private final EpisodeApi episodeApi;

	@Getter
	private final FoodApi foodApi;

	@Getter
	private final LiteratureApi literatureApi;

	@Getter
	private final LocationApi locationApi;

	@Getter
	private final MagazineApi magazineApi;

	@Getter
	private final MagazineSeriesApi magazineSeriesApi;

	@Getter
	private final MaterialApi materialApi;

	@Getter
	private final MedicalConditionApi medicalConditionApi;

	@Getter
	private final MovieApi movieApi;

	@Getter
	private final OccupationApi occupationApi;

	@Getter
	private final OrganizationApi organizationApi;

	@Getter
	private final PerformerApi performerApi;

	@Getter
	private final SeasonApi seasonApi;

	@Getter
	private final SeriesApi seriesApi;

	@Getter
	private final SoundtrackApi soundtrackApi;

	@Getter
	private final SpacecraftApi spacecraftApi;

	@Getter
	private final SpacecraftClassApi spacecraftClassApi;

	@Getter
	private final SpeciesApi speciesApi;

	@Getter
	private final StaffApi staffApi;

	@Getter
	private final TechnologyApi technologyApi;

	@Getter
	private final TitleApi titleApi;

	@Getter
	private final TradingCardApi tradingCardApi;

	@Getter
	private final TradingCardDeckApi tradingCardDeckApi;

	@Getter
	private final TradingCardSetApi tradingCardSetApi;

	@Getter
	private final VideoGameApi videoGameApi;

	@Getter
	private final VideoReleaseApi videoReleaseApi;

	@Getter
	private final WeaponApi weaponApi;

	@Getter
	private final Animal animal;

	@Getter
	private final AstronomicalObject astronomicalObject;

	@Getter
	private final Book book;

	@Getter
	private final BookCollection bookCollection;

	@Getter
	private final BookSeries bookSeries;

	@Getter
	private final Character character;

	@Getter
	private final ComicCollection comicCollection;

	@Getter
	private final Comics comics;

	@Getter
	private final ComicSeries comicSeries;

	@Getter
	private final ComicStrip comicStrip;

	@Getter
	private final Company company;

	@Getter
	private final Conflict conflict;

	@Getter
	private final DataVersion dataVersion;

	@Getter
	private final Element element;

	@Getter
	private final Episode episode;

	@Getter
	private final Food food;

	@Getter
	private final Literature literature;

	@Getter
	private final Location location;

	@Getter
	private final Magazine magazine;

	@Getter
	private final MagazineSeries magazineSeries;

	@Getter
	private final Material material;

	@Getter
	private final MedicalCondition medicalCondition;

	@Getter
	private final Movie movie;

	@Getter
	private final Occupation occupation;

	@Getter
	private final Organization organization;

	@Getter
	private final Performer performer;

	@Getter
	private final Season season;

	@Getter
	private final Series series;

	@Getter
	private final Soundtrack soundtrack;

	@Getter
	private final Spacecraft spacecraft;

	@Getter
	private final SpacecraftClass spacecraftClass;

	@Getter
	private final Species species;

	@Getter
	private final Staff staff;

	@Getter
	private final Technology technology;

	@Getter
	private final Title title;

	@Getter
	private final TradingCard tradingCard;

	@Getter
	private final TradingCardDeck tradingCardDeck;

	@Getter
	private final TradingCardSet tradingCardSet;

	@Getter
	private final VideoGame videoGame;

	@Getter
	private final VideoRelease videoRelease;

	@Getter
	private final Weapon weapon;

	private ApiClient apiClient;

	public StapiRestClient() {
		this(null);
	}

	public StapiRestClient(String apiUrl) {
		this.apiUrl = validateUrl(defaultIfBlank(apiUrl));
		createApiClient();
		astronomicalObjectApi = new AstronomicalObjectApi(apiClient);
		animalApi = new AnimalApi(apiClient);
		bookApi = new BookApi(apiClient);
		bookCollectionApi = new BookCollectionApi(apiClient);
		bookSeriesApi = new BookSeriesApi(apiClient);
		characterApi = new CharacterApi(apiClient);
		comicCollectionApi = new ComicCollectionApi(apiClient);
		comicsApi = new ComicsApi(apiClient);
		comicSeriesApi = new ComicSeriesApi(apiClient);
		comicStripApi = new ComicStripApi(apiClient);
		companyApi = new CompanyApi(apiClient);
		conflictApi = new ConflictApi(apiClient);
		dataVersionApi = new DataVersionApi(apiClient);
		elementApi = new ElementApi(apiClient);
		episodeApi = new EpisodeApi(apiClient);
		foodApi = new FoodApi(apiClient);
		literatureApi = new LiteratureApi(apiClient);
		locationApi = new LocationApi(apiClient);
		magazineApi = new MagazineApi(apiClient);
		magazineSeriesApi = new MagazineSeriesApi(apiClient);
		materialApi = new MaterialApi(apiClient);
		medicalConditionApi = new MedicalConditionApi(apiClient);
		movieApi = new MovieApi(apiClient);
		occupationApi = new OccupationApi(apiClient);
		organizationApi = new OrganizationApi(apiClient);
		performerApi = new PerformerApi(apiClient);
		seasonApi = new SeasonApi(apiClient);
		seriesApi = new SeriesApi(apiClient);
		soundtrackApi = new SoundtrackApi(apiClient);
		spacecraftApi = new SpacecraftApi(apiClient);
		spacecraftClassApi = new SpacecraftClassApi(apiClient);
		speciesApi = new SpeciesApi(apiClient);
		staffApi = new StaffApi(apiClient);
		technologyApi = new TechnologyApi(apiClient);
		titleApi = new TitleApi(apiClient);
		tradingCardApi = new TradingCardApi(apiClient);
		tradingCardDeckApi = new TradingCardDeckApi(apiClient);
		tradingCardSetApi = new TradingCardSetApi(apiClient);
		videoGameApi = new VideoGameApi(apiClient);
		videoReleaseApi = new VideoReleaseApi(apiClient);
		weaponApi = new WeaponApi(apiClient);
		animal = new Animal(animalApi);
		astronomicalObject = new AstronomicalObject(astronomicalObjectApi);
		book = new Book(bookApi);
		bookCollection = new BookCollection(bookCollectionApi);
		bookSeries = new BookSeries(bookSeriesApi);
		character = new Character(characterApi);
		comicCollection = new ComicCollection(comicCollectionApi);
		comics = new Comics(comicsApi);
		comicSeries = new ComicSeries(comicSeriesApi);
		comicStrip = new ComicStrip(comicStripApi);
		company = new Company(companyApi);
		conflict = new Conflict(conflictApi);
		dataVersion = new DataVersion(dataVersionApi);
		element = new Element(elementApi);
		episode = new Episode(episodeApi);
		food = new Food(foodApi);
		literature = new Literature(literatureApi);
		location = new Location(locationApi);
		magazine = new Magazine(magazineApi);
		magazineSeries = new MagazineSeries(magazineSeriesApi);
		material = new Material(materialApi);
		medicalCondition = new MedicalCondition(medicalConditionApi);
		movie = new Movie(movieApi);
		occupation = new Occupation(occupationApi);
		organization = new Organization(organizationApi);
		performer = new Performer(performerApi);
		season = new Season(seasonApi);
		series = new Series(seriesApi);
		soundtrack = new Soundtrack(soundtrackApi);
		spacecraft = new Spacecraft(spacecraftApi);
		spacecraftClass = new SpacecraftClass(spacecraftClassApi);
		species = new Species(speciesApi);
		staff = new Staff(staffApi);
		technology = new Technology(technologyApi);
		title = new Title(titleApi);
		tradingCard = new TradingCard(tradingCardApi);
		tradingCardDeck = new TradingCardDeck(tradingCardDeckApi);
		tradingCardSet = new TradingCardSet(tradingCardSetApi);
		videoGame = new VideoGame(videoGameApi);
		videoRelease = new VideoRelease(videoReleaseApi);
		weapon = new Weapon(weaponApi);
	}

	private void createApiClient() {
		apiClient = new ApiClient();
		if (apiUrl != null) {
			apiClient.setBasePath(changeBaseHttpsUrl(apiUrl, apiClient.getBasePath()));
		}
		apiClient.setConnectTimeout(10000);
	}

	private String changeBaseHttpsUrl(String baseUrl, String serviceUrl) {
		return serviceUrl.replace(CANONICAL_API_HTTPS_URL, baseUrl);
	}

	private String defaultIfBlank(String string) {
		return string != null && !string.isEmpty() ? string : CANONICAL_API_HTTPS_URL;
	}

	private String validateUrl(String url) {
		if (!url.startsWith("http")) {
			throw new IllegalArgumentException(String.format("URL %s must start with \"http\" or \"https\"", url));
		}

		if (!url.endsWith("/")) {
			throw new IllegalArgumentException(String.format("URL %s must end with slash", url));
		}

		return url;
	}

}
