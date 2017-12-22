package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.api.soap.Animal;
import com.cezarykluczynski.stapi.client.api.soap.ApiKeySupplier;
import com.cezarykluczynski.stapi.client.api.soap.AstronomicalObject;
import com.cezarykluczynski.stapi.client.api.soap.Book;
import com.cezarykluczynski.stapi.client.api.soap.BookCollection;
import com.cezarykluczynski.stapi.client.api.soap.BookSeries;
import com.cezarykluczynski.stapi.client.api.soap.Character;
import com.cezarykluczynski.stapi.client.api.soap.ComicCollection;
import com.cezarykluczynski.stapi.client.api.soap.ComicSeries;
import com.cezarykluczynski.stapi.client.api.soap.ComicStrip;
import com.cezarykluczynski.stapi.client.api.soap.Comics;
import com.cezarykluczynski.stapi.client.api.soap.Company;
import com.cezarykluczynski.stapi.client.api.soap.Conflict;
import com.cezarykluczynski.stapi.client.api.soap.Element;
import com.cezarykluczynski.stapi.client.api.soap.Episode;
import com.cezarykluczynski.stapi.client.api.soap.Food;
import com.cezarykluczynski.stapi.client.api.soap.Literature;
import com.cezarykluczynski.stapi.client.api.soap.Location;
import com.cezarykluczynski.stapi.client.api.soap.Magazine;
import com.cezarykluczynski.stapi.client.api.soap.MagazineSeries;
import com.cezarykluczynski.stapi.client.api.soap.Material;
import com.cezarykluczynski.stapi.client.api.soap.MedicalCondition;
import com.cezarykluczynski.stapi.client.api.soap.Movie;
import com.cezarykluczynski.stapi.client.api.soap.Occupation;
import com.cezarykluczynski.stapi.client.api.soap.Organization;
import com.cezarykluczynski.stapi.client.api.soap.Performer;
import com.cezarykluczynski.stapi.client.api.soap.Season;
import com.cezarykluczynski.stapi.client.api.soap.Series;
import com.cezarykluczynski.stapi.client.api.soap.Soundtrack;
import com.cezarykluczynski.stapi.client.api.soap.Spacecraft;
import com.cezarykluczynski.stapi.client.api.soap.SpacecraftClass;
import com.cezarykluczynski.stapi.client.api.soap.Species;
import com.cezarykluczynski.stapi.client.api.soap.Staff;
import com.cezarykluczynski.stapi.client.api.soap.Technology;
import com.cezarykluczynski.stapi.client.api.soap.Title;
import com.cezarykluczynski.stapi.client.api.soap.TradingCard;
import com.cezarykluczynski.stapi.client.api.soap.TradingCardDeck;
import com.cezarykluczynski.stapi.client.api.soap.TradingCardSet;
import com.cezarykluczynski.stapi.client.api.soap.VideoGame;
import com.cezarykluczynski.stapi.client.api.soap.VideoRelease;
import com.cezarykluczynski.stapi.client.api.soap.Weapon;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalPortType;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectPortType;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionPortType;
import com.cezarykluczynski.stapi.client.v1.soap.BookPortType;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsPortType;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ElementPortType;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodePortType;
import com.cezarykluczynski.stapi.client.v1.soap.FoodPortType;
import com.cezarykluczynski.stapi.client.v1.soap.LiteraturePortType;
import com.cezarykluczynski.stapi.client.v1.soap.LocationPortType;
import com.cezarykluczynski.stapi.client.v1.soap.MagazinePortType;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialPortType;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionPortType;
import com.cezarykluczynski.stapi.client.v1.soap.MoviePortType;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationPortType;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationPortType;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.StaffPortType;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyPortType;
import com.cezarykluczynski.stapi.client.v1.soap.TitlePortType;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckPortType;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardPortType;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetPortType;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGamePortType;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleasePortType;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponPortType;
import lombok.Getter;

public class StapiSoapClient extends AbstractStapiClient implements StapiClient {

	@Getter
	private String apiUrl;

	private StapiSoapPortTypesProvider stapiSoapPortTypesProvider;

	private ApiKeySupplier apiKeySupplier;

	@Getter
	private AnimalPortType animalPortType;

	@Getter
	private AstronomicalObjectPortType astronomicalObjectPortType;

	@Getter
	private BookPortType bookPortType;

	@Getter
	private BookCollectionPortType bookCollectionPortType;

	@Getter
	private BookSeriesPortType bookSeriesPortType;

	@Getter
	private CharacterPortType characterPortType;

	@Getter
	private ComicCollectionPortType comicCollectionPortType;

	@Getter
	private ComicsPortType comicsPortType;

	@Getter
	private ComicSeriesPortType comicSeriesPortType;

	@Getter
	private ComicStripPortType comicStripPortType;

	@Getter
	private CompanyPortType companyPortType;

	@Getter
	private ConflictPortType conflictPortType;

	@Getter
	private ElementPortType elementPortType;

	@Getter
	private EpisodePortType episodePortType;

	@Getter
	private FoodPortType foodPortType;

	@Getter
	private LiteraturePortType literaturePortType;

	@Getter
	private LocationPortType locationPortType;

	@Getter
	private MagazinePortType magazinePortType;

	@Getter
	private MagazineSeriesPortType magazineSeriesPortType;

	@Getter
	private MaterialPortType materialPortType;

	@Getter
	private MedicalConditionPortType medicalConditionPortType;

	@Getter
	private MoviePortType moviePortType;

	@Getter
	private OccupationPortType occupationPortType;

	@Getter
	private OrganizationPortType organizationPortType;

	@Getter
	private PerformerPortType performerPortType;

	@Getter
	private SeasonPortType seasonPortType;

	@Getter
	private SeriesPortType seriesPortType;

	@Getter
	private SoundtrackPortType soundtrackPortType;

	@Getter
	private SpacecraftPortType spacecraftPortType;

	@Getter
	private SpacecraftClassPortType spacecraftClassPortType;

	@Getter
	private SpeciesPortType speciesPortType;

	@Getter
	private StaffPortType staffPortType;

	@Getter
	private TechnologyPortType technologyPortType;

	@Getter
	private TitlePortType titlePortType;

	@Getter
	private TradingCardPortType tradingCardPortType;

	@Getter
	private TradingCardDeckPortType tradingCardDeckPortType;

	@Getter
	private TradingCardSetPortType tradingCardSetPortType;

	@Getter
	private VideoGamePortType videoGamePortType;

	@Getter
	private VideoReleasePortType videoReleasePortType;

	@Getter
	private WeaponPortType weaponPortType;

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

	public StapiSoapClient(String apiUrl, String apiKey) {
		this.apiUrl = validateUrl(defaultIfBlank(apiUrl, CANONICAL_API_URL));
		stapiSoapPortTypesProvider = new StapiSoapPortTypesProvider(this.apiUrl);
		apiKeySupplier = new ApiKeySupplier(apiKey);
		bindPortTypes();
		createApiKeyAwareProxies();
	}

	private void bindPortTypes() {
		animalPortType = stapiSoapPortTypesProvider.getAnimalPortType();
		astronomicalObjectPortType = stapiSoapPortTypesProvider.getAstronomicalObjectPortType();
		bookPortType = stapiSoapPortTypesProvider.getBookPortType();
		bookCollectionPortType = stapiSoapPortTypesProvider.getBookCollectionPortType();
		bookSeriesPortType = stapiSoapPortTypesProvider.getBookSeriesPortType();
		characterPortType = stapiSoapPortTypesProvider.getCharacterPortType();
		comicCollectionPortType = stapiSoapPortTypesProvider.getComicCollectionPortType();
		comicsPortType = stapiSoapPortTypesProvider.getComicsPortType();
		comicSeriesPortType = stapiSoapPortTypesProvider.getComicSeriesPortType();
		comicStripPortType = stapiSoapPortTypesProvider.getComicStripPortType();
		companyPortType = stapiSoapPortTypesProvider.getCompanyPortType();
		conflictPortType = stapiSoapPortTypesProvider.getConflictPortType();
		elementPortType = stapiSoapPortTypesProvider.getElementPortType();
		episodePortType = stapiSoapPortTypesProvider.getEpisodePortType();
		foodPortType = stapiSoapPortTypesProvider.getFoodPortType();
		literaturePortType = stapiSoapPortTypesProvider.getLiteraturePortType();
		locationPortType = stapiSoapPortTypesProvider.getLocationPortType();
		magazinePortType = stapiSoapPortTypesProvider.getMagazinePortType();
		magazineSeriesPortType = stapiSoapPortTypesProvider.getMagazineSeriesPortType();
		materialPortType = stapiSoapPortTypesProvider.getMaterialPortType();
		medicalConditionPortType = stapiSoapPortTypesProvider.getMedicalConditionPortType();
		moviePortType = stapiSoapPortTypesProvider.getMoviePortType();
		occupationPortType = stapiSoapPortTypesProvider.getOccupationPortType();
		organizationPortType = stapiSoapPortTypesProvider.getOrganizationPortType();
		performerPortType = stapiSoapPortTypesProvider.getPerformerPortType();
		seasonPortType = stapiSoapPortTypesProvider.getSeasonPortType();
		seriesPortType = stapiSoapPortTypesProvider.getSeriesPortType();
		soundtrackPortType = stapiSoapPortTypesProvider.getSoundtrackPortType();
		spacecraftPortType = stapiSoapPortTypesProvider.getSpacecraftPortType();
		spacecraftClassPortType = stapiSoapPortTypesProvider.getSpacecraftClassPortType();
		speciesPortType = stapiSoapPortTypesProvider.getSpeciesPortType();
		staffPortType = stapiSoapPortTypesProvider.getStaffPortType();
		technologyPortType = stapiSoapPortTypesProvider.getTechnologyPortType();
		titlePortType = stapiSoapPortTypesProvider.getTitlePortType();
		tradingCardPortType = stapiSoapPortTypesProvider.getTradingCardPortType();
		tradingCardDeckPortType = stapiSoapPortTypesProvider.getTradingCardDeckPortType();
		tradingCardSetPortType = stapiSoapPortTypesProvider.getTradingCardSetPortType();
		videoGamePortType = stapiSoapPortTypesProvider.getVideoGamePortType();
		videoReleasePortType = stapiSoapPortTypesProvider.getVideoReleasePortType();
		weaponPortType = stapiSoapPortTypesProvider.getWeaponPortType();
	}

	private void createApiKeyAwareProxies() {
		animal = new Animal(animalPortType, apiKeySupplier);
		astronomicalObject = new AstronomicalObject(astronomicalObjectPortType, apiKeySupplier);
		book = new Book(bookPortType, apiKeySupplier);
		bookCollection = new BookCollection(bookCollectionPortType, apiKeySupplier);
		bookSeries = new BookSeries(bookSeriesPortType, apiKeySupplier);
		character = new Character(characterPortType, apiKeySupplier);
		comicCollection = new ComicCollection(comicCollectionPortType, apiKeySupplier);
		comics = new Comics(comicsPortType, apiKeySupplier);
		comicSeries = new ComicSeries(comicSeriesPortType, apiKeySupplier);
		comicStrip = new ComicStrip(comicStripPortType, apiKeySupplier);
		company = new Company(companyPortType, apiKeySupplier);
		conflict = new Conflict(conflictPortType, apiKeySupplier);
		element = new Element(elementPortType, apiKeySupplier);
		episode = new Episode(episodePortType, apiKeySupplier);
		food = new Food(foodPortType, apiKeySupplier);
		literature = new Literature(literaturePortType, apiKeySupplier);
		location = new Location(locationPortType, apiKeySupplier);
		magazine = new Magazine(magazinePortType, apiKeySupplier);
		magazineSeries = new MagazineSeries(magazineSeriesPortType, apiKeySupplier);
		material = new Material(materialPortType, apiKeySupplier);
		medicalCondition = new MedicalCondition(medicalConditionPortType, apiKeySupplier);
		movie = new Movie(moviePortType, apiKeySupplier);
		occupation = new Occupation(occupationPortType, apiKeySupplier);
		organization = new Organization(organizationPortType, apiKeySupplier);
		performer = new Performer(performerPortType, apiKeySupplier);
		season = new Season(seasonPortType, apiKeySupplier);
		series = new Series(seriesPortType, apiKeySupplier);
		soundtrack = new Soundtrack(soundtrackPortType, apiKeySupplier);
		spacecraft = new Spacecraft(spacecraftPortType, apiKeySupplier);
		spacecraftClass = new SpacecraftClass(spacecraftClassPortType, apiKeySupplier);
		species = new Species(speciesPortType, apiKeySupplier);
		staff = new Staff(staffPortType, apiKeySupplier);
		technology = new Technology(technologyPortType, apiKeySupplier);
		title = new Title(titlePortType, apiKeySupplier);
		tradingCard = new TradingCard(tradingCardPortType, apiKeySupplier);
		tradingCardDeck = new TradingCardDeck(tradingCardDeckPortType, apiKeySupplier);
		tradingCardSet = new TradingCardSet(tradingCardSetPortType, apiKeySupplier);
		videoGame = new VideoGame(videoGamePortType, apiKeySupplier);
		videoRelease = new VideoRelease(videoReleasePortType, apiKeySupplier);
		weapon = new Weapon(weaponPortType, apiKeySupplier);
	}

}
