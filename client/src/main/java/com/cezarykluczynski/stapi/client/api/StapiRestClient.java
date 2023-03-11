package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.api.rest.Animal;
import com.cezarykluczynski.stapi.client.api.rest.AstronomicalObject;
import com.cezarykluczynski.stapi.client.api.rest.Book;
import com.cezarykluczynski.stapi.client.api.rest.BookCollection;
import com.cezarykluczynski.stapi.client.api.rest.BookSeries;
import com.cezarykluczynski.stapi.client.api.rest.Character;
import com.cezarykluczynski.stapi.client.api.rest.ComicCollection;
import com.cezarykluczynski.stapi.client.api.rest.ComicSeries;
import com.cezarykluczynski.stapi.client.api.rest.ComicStrip;
import com.cezarykluczynski.stapi.client.api.rest.Comics;
import com.cezarykluczynski.stapi.client.api.rest.Company;
import com.cezarykluczynski.stapi.client.api.rest.Conflict;
import com.cezarykluczynski.stapi.client.api.rest.DataVersion;
import com.cezarykluczynski.stapi.client.api.rest.Element;
import com.cezarykluczynski.stapi.client.api.rest.Episode;
import com.cezarykluczynski.stapi.client.api.rest.Food;
import com.cezarykluczynski.stapi.client.api.rest.Literature;
import com.cezarykluczynski.stapi.client.api.rest.Location;
import com.cezarykluczynski.stapi.client.api.rest.Magazine;
import com.cezarykluczynski.stapi.client.api.rest.MagazineSeries;
import com.cezarykluczynski.stapi.client.api.rest.Material;
import com.cezarykluczynski.stapi.client.api.rest.MedicalCondition;
import com.cezarykluczynski.stapi.client.api.rest.Movie;
import com.cezarykluczynski.stapi.client.api.rest.Occupation;
import com.cezarykluczynski.stapi.client.api.rest.Organization;
import com.cezarykluczynski.stapi.client.api.rest.Performer;
import com.cezarykluczynski.stapi.client.api.rest.Season;
import com.cezarykluczynski.stapi.client.api.rest.Series;
import com.cezarykluczynski.stapi.client.api.rest.Soundtrack;
import com.cezarykluczynski.stapi.client.api.rest.Spacecraft;
import com.cezarykluczynski.stapi.client.api.rest.SpacecraftClass;
import com.cezarykluczynski.stapi.client.api.rest.Species;
import com.cezarykluczynski.stapi.client.api.rest.Staff;
import com.cezarykluczynski.stapi.client.api.rest.Technology;
import com.cezarykluczynski.stapi.client.api.rest.Title;
import com.cezarykluczynski.stapi.client.api.rest.TradingCard;
import com.cezarykluczynski.stapi.client.api.rest.TradingCardDeck;
import com.cezarykluczynski.stapi.client.api.rest.TradingCardSet;
import com.cezarykluczynski.stapi.client.api.rest.VideoGame;
import com.cezarykluczynski.stapi.client.api.rest.VideoRelease;
import com.cezarykluczynski.stapi.client.api.rest.Weapon;
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

public class StapiRestClient extends AbstractStapiClient implements StapiClient {

	@Getter
	private String apiUrl;

	private ApiClient apiClient;

	@Getter
	private AnimalApi animalApi;

	@Getter
	private AstronomicalObjectApi astronomicalObjectApi;

	@Getter
	private BookApi bookApi;

	@Getter
	private BookCollectionApi bookCollectionApi;

	@Getter
	private BookSeriesApi bookSeriesApi;

	@Getter
	private CharacterApi characterApi;

	@Getter
	private ComicCollectionApi comicCollectionApi;

	@Getter
	private ComicsApi comicsApi;

	@Getter
	private ComicSeriesApi comicSeriesApi;

	@Getter
	private ComicStripApi comicStripApi;

	@Getter
	private CompanyApi companyApi;

	@Getter
	private ConflictApi conflictApi;

	@Getter
	private DataVersionApi dataVersionApi;

	@Getter
	private ElementApi elementApi;

	@Getter
	private EpisodeApi episodeApi;

	@Getter
	private FoodApi foodApi;

	@Getter
	private LiteratureApi literatureApi;

	@Getter
	private LocationApi locationApi;

	@Getter
	private MagazineApi magazineApi;

	@Getter
	private MagazineSeriesApi magazineSeriesApi;

	@Getter
	private MaterialApi materialApi;

	@Getter
	private MedicalConditionApi medicalConditionApi;

	@Getter
	private MovieApi movieApi;

	@Getter
	private OccupationApi occupationApi;

	@Getter
	private OrganizationApi organizationApi;

	@Getter
	private PerformerApi performerApi;

	@Getter
	private SeasonApi seasonApi;

	@Getter
	private SeriesApi seriesApi;

	@Getter
	private SoundtrackApi soundtrackApi;

	@Getter
	private SpacecraftApi spacecraftApi;

	@Getter
	private SpacecraftClassApi spacecraftClassApi;

	@Getter
	private SpeciesApi speciesApi;

	@Getter
	private StaffApi staffApi;

	@Getter
	private TechnologyApi technologyApi;

	@Getter
	private TitleApi titleApi;

	@Getter
	private TradingCardApi tradingCardApi;

	@Getter
	private TradingCardDeckApi tradingCardDeckApi;

	@Getter
	private TradingCardSetApi tradingCardSetApi;

	@Getter
	private VideoGameApi videoGameApi;

	@Getter
	private VideoReleaseApi videoReleaseApi;

	@Getter
	private WeaponApi weaponApi;

	@Getter
	private Animal animal;

	@Getter
	private AstronomicalObject astronomicalObject;

	@Getter
	private Book book;

	@Getter
	private BookCollection bookCollection;

	@Getter
	private BookSeries bookSeries;

	@Getter
	private Character character;

	@Getter
	private ComicCollection comicCollection;

	@Getter
	private Comics comics;

	@Getter
	private ComicSeries comicSeries;

	@Getter
	private ComicStrip comicStrip;

	@Getter
	private Company company;

	@Getter
	private Conflict conflict;

	@Getter
	private DataVersion dataVersion;

	@Getter
	private Element element;

	@Getter
	private Episode episode;

	@Getter
	private Food food;

	@Getter
	private Literature literature;

	@Getter
	private Location location;

	@Getter
	private Magazine magazine;

	@Getter
	private MagazineSeries magazineSeries;

	@Getter
	private Material material;

	@Getter
	private MedicalCondition medicalCondition;

	@Getter
	private Movie movie;

	@Getter
	private Occupation occupation;

	@Getter
	private Organization organization;

	@Getter
	private Performer performer;

	@Getter
	private Season season;

	@Getter
	private Series series;

	@Getter
	private Soundtrack soundtrack;

	@Getter
	private Spacecraft spacecraft;

	@Getter
	private SpacecraftClass spacecraftClass;

	@Getter
	private Species species;

	@Getter
	private Staff staff;

	@Getter
	private Technology technology;

	@Getter
	private Title title;

	@Getter
	private TradingCard tradingCard;

	@Getter
	private TradingCardDeck tradingCardDeck;

	@Getter
	private TradingCardSet tradingCardSet;

	@Getter
	private VideoGame videoGame;

	@Getter
	private VideoRelease videoRelease;

	@Getter
	private Weapon weapon;

	public StapiRestClient() {
		this(null);
	}

	public StapiRestClient(String apiUrl) {
		this.apiUrl = validateUrl(defaultIfBlank(apiUrl, CANONICAL_API_HTTPS_URL));
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

}
