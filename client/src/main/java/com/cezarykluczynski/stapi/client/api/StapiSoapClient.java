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

	private AnimalPortType animalPortType;

	private AstronomicalObjectPortType astronomicalObjectPortType;

	private BookPortType bookPortType;

	private BookCollectionPortType bookCollectionPortType;

	private BookSeriesPortType bookSeriesPortType;

	private CharacterPortType characterPortType;

	private ComicCollectionPortType comicCollectionPortType;

	private ComicsPortType comicsPortType;

	private ComicSeriesPortType comicSeriesPortType;

	private ComicStripPortType comicStripPortType;

	private CompanyPortType companyPortType;

	private ConflictPortType conflictPortType;

	private ElementPortType elementPortType;

	private EpisodePortType episodePortType;

	private FoodPortType foodPortType;

	private LiteraturePortType literaturePortType;

	private LocationPortType locationPortType;

	private MagazinePortType magazinePortType;

	private MagazineSeriesPortType magazineSeriesPortType;

	private MaterialPortType materialPortType;

	private MedicalConditionPortType medicalConditionPortType;

	private MoviePortType moviePortType;

	private OccupationPortType occupationPortType;

	private OrganizationPortType organizationPortType;

	private PerformerPortType performerPortType;

	private SeasonPortType seasonPortType;

	private SeriesPortType seriesPortType;

	private SoundtrackPortType soundtrackPortType;

	private SpacecraftPortType spacecraftPortType;

	private SpacecraftClassPortType spacecraftClassPortType;

	private SpeciesPortType speciesPortType;

	private StaffPortType staffPortType;

	private TechnologyPortType technologyPortType;

	private TitlePortType titlePortType;

	private TradingCardPortType tradingCardPortType;

	private TradingCardDeckPortType tradingCardDeckPortType;

	private TradingCardSetPortType tradingCardSetPortType;

	private VideoGamePortType videoGamePortType;

	private VideoReleasePortType videoReleasePortType;

	private WeaponPortType weaponPortType;

	private Animal animal;

	private AstronomicalObject astronomicalObject;

	private Book book;

	private BookCollection bookCollection;

	private BookSeries bookSeries;

	private Character character;

	private ComicCollection comicCollection;

	private Comics comics;

	private ComicSeries comicSeries;

	private ComicStrip comicStrip;

	private Company company;

	private Conflict conflict;

	private Element element;

	private Episode episode;

	private Food food;

	private Literature literature;

	private Location location;

	private Magazine magazine;

	private MagazineSeries magazineSeries;

	private Material material;

	private MedicalCondition medicalCondition;

	private Movie movie;

	private Occupation occupation;

	private Organization organization;

	private Performer performer;

	private Season season;

	private Series series;

	private Soundtrack soundtrack;

	private Spacecraft spacecraft;

	private SpacecraftClass spacecraftClass;

	private Species species;

	private Staff staff;

	private Technology technology;

	private Title title;

	private TradingCard tradingCard;

	private TradingCardDeck tradingCardDeck;

	private TradingCardSet tradingCardSet;

	private VideoGame videoGame;

	private VideoRelease videoRelease;

	private Weapon weapon;

	public StapiSoapClient(String apiUrl, String apiKey) {
		this.apiUrl = validateUrl(defaultIfBlank(apiUrl, CANONICAL_API_URL));
		stapiSoapPortTypesProvider = new StapiSoapPortTypesProvider(this.apiUrl);
		apiKeySupplier = new ApiKeySupplier(apiKey);
	}

	public synchronized Animal getAnimal() {
		if (animal == null) {
			animal = new Animal(getAnimalPortType(), apiKeySupplier);
		}
		return animal;
	}

	public synchronized AnimalPortType getAnimalPortType() {
		if (animalPortType == null) {
			animalPortType = stapiSoapPortTypesProvider.getAnimalPortType();
		}

		return animalPortType;
	}

	public synchronized AstronomicalObject getAstronomicalObject() {
		if (astronomicalObject == null) {
			astronomicalObject = new AstronomicalObject(getAstronomicalObjectPortType(), apiKeySupplier);
		}
		return astronomicalObject;
	}

	public synchronized AstronomicalObjectPortType getAstronomicalObjectPortType() {
		if (astronomicalObjectPortType == null) {
			astronomicalObjectPortType = stapiSoapPortTypesProvider.getAstronomicalObjectPortType();
		}
		return astronomicalObjectPortType;
	}

	public synchronized Book getBook() {
		if (book == null) {
			book = new Book(getBookPortType(), apiKeySupplier);
		}
		return book;
	}

	public synchronized BookPortType getBookPortType() {
		if (bookPortType == null) {
			bookPortType = stapiSoapPortTypesProvider.getBookPortType();
		}
		return bookPortType;
	}

	public synchronized BookCollection getBookCollection() {
		if (bookCollection == null) {
			bookCollection = new BookCollection(getBookCollectionPortType(), apiKeySupplier);
		}
		return bookCollection;
	}

	public synchronized BookCollectionPortType getBookCollectionPortType() {
		if (bookCollectionPortType == null) {
			bookCollectionPortType = stapiSoapPortTypesProvider.getBookCollectionPortType();
		}
		return bookCollectionPortType;
	}

	public synchronized BookSeries getBookSeries() {
		if (bookSeries == null) {
			bookSeries = new BookSeries(getBookSeriesPortType(), apiKeySupplier);
		}
		return bookSeries;
	}

	public synchronized BookSeriesPortType getBookSeriesPortType() {
		if (bookSeriesPortType == null) {
			bookSeriesPortType = stapiSoapPortTypesProvider.getBookSeriesPortType();
		}
		return bookSeriesPortType;
	}

	public synchronized Character getCharacter() {
		if (character == null) {
			character = new Character(getCharacterPortType(), apiKeySupplier);
		}
		return character;
	}

	public synchronized CharacterPortType getCharacterPortType() {
		if (characterPortType == null) {
			characterPortType = stapiSoapPortTypesProvider.getCharacterPortType();
		}
		return characterPortType;
	}

	public synchronized ComicCollection getComicCollection() {
		if (comicCollection == null) {
			comicCollection = new ComicCollection(getComicCollectionPortType(), apiKeySupplier);
		}
		return comicCollection;
	}

	public synchronized ComicCollectionPortType getComicCollectionPortType() {
		if (comicCollectionPortType == null) {
			comicCollectionPortType = stapiSoapPortTypesProvider.getComicCollectionPortType();
		}
		return comicCollectionPortType;
	}

	public synchronized Comics getComics() {
		if (comics == null) {
			comics = new Comics(getComicsPortType(), apiKeySupplier);
		}
		return comics;
	}

	public synchronized ComicsPortType getComicsPortType() {
		if (comicsPortType == null) {
			comicsPortType = stapiSoapPortTypesProvider.getComicsPortType();
		}
		return comicsPortType;
	}

	public synchronized ComicSeries getComicSeries() {
		if (comicSeries == null) {
			comicSeries = new ComicSeries(getComicSeriesPortType(), apiKeySupplier);
		}
		return comicSeries;
	}

	public synchronized ComicSeriesPortType getComicSeriesPortType() {
		if (comicSeriesPortType == null) {
			comicSeriesPortType = stapiSoapPortTypesProvider.getComicSeriesPortType();
		}
		return comicSeriesPortType;
	}

	public synchronized ComicStrip getComicStrip() {
		if (comicStrip == null) {
			comicStrip = new ComicStrip(getComicStripPortType(), apiKeySupplier);
		}
		return comicStrip;
	}

	public synchronized ComicStripPortType getComicStripPortType() {
		if (comicStripPortType == null) {
			comicStripPortType = stapiSoapPortTypesProvider.getComicStripPortType();
		}
		return comicStripPortType;
	}

	public synchronized Company getCompany() {
		if (company == null) {
			company = new Company(getCompanyPortType(), apiKeySupplier);
		}
		return company;
	}

	public synchronized CompanyPortType getCompanyPortType() {
		if (companyPortType == null) {
			companyPortType = stapiSoapPortTypesProvider.getCompanyPortType();
		}
		return companyPortType;
	}

	public synchronized Conflict getConflict() {
		if (conflict == null) {
			conflict = new Conflict(getConflictPortType(), apiKeySupplier);
		}
		return conflict;
	}

	public synchronized ConflictPortType getConflictPortType() {
		if (conflictPortType == null) {
			conflictPortType = stapiSoapPortTypesProvider.getConflictPortType();
		}
		return conflictPortType;
	}

	public synchronized Element getElement() {
		if (element == null) {
			element = new Element(getElementPortType(), apiKeySupplier);
		}
		return element;
	}

	public synchronized ElementPortType getElementPortType() {
		if (elementPortType == null) {
			elementPortType = stapiSoapPortTypesProvider.getElementPortType();
		}
		return elementPortType;
	}

	public synchronized Episode getEpisode() {
		if (episode == null) {
			episode = new Episode(getEpisodePortType(), apiKeySupplier);
		}
		return episode;
	}

	public synchronized EpisodePortType getEpisodePortType() {
		if (episodePortType == null) {
			episodePortType = stapiSoapPortTypesProvider.getEpisodePortType();
		}
		return episodePortType;
	}

	public synchronized Food getFood() {
		if (food == null) {
			food = new Food(getFoodPortType(), apiKeySupplier);
		}
		return food;
	}

	public synchronized FoodPortType getFoodPortType() {
		if (foodPortType == null) {
			foodPortType = stapiSoapPortTypesProvider.getFoodPortType();
		}
		return foodPortType;
	}

	public synchronized Literature getLiterature() {
		if (literature == null) {
			literature = new Literature(getLiteraturePortType(), apiKeySupplier);
		}
		return literature;
	}

	public synchronized LiteraturePortType getLiteraturePortType() {
		if (literaturePortType == null) {
			literaturePortType = stapiSoapPortTypesProvider.getLiteraturePortType();
		}
		return literaturePortType;
	}

	public synchronized Location getLocation() {
		if (location == null) {
			location = new Location(getLocationPortType(), apiKeySupplier);
		}
		return location;
	}

	public synchronized LocationPortType getLocationPortType() {
		if (locationPortType == null) {
			locationPortType = stapiSoapPortTypesProvider.getLocationPortType();
		}
		return locationPortType;
	}

	public synchronized Magazine getMagazine() {
		if (magazine == null) {
			magazine = new Magazine(getMagazinePortType(), apiKeySupplier);
		}
		return magazine;
	}

	public synchronized MagazinePortType getMagazinePortType() {
		if (magazinePortType == null) {
			magazinePortType = stapiSoapPortTypesProvider.getMagazinePortType();
		}
		return magazinePortType;
	}

	public synchronized MagazineSeries getMagazineSeries() {
		if (magazineSeries == null) {
			magazineSeries = new MagazineSeries(getMagazineSeriesPortType(), apiKeySupplier);
		}
		return magazineSeries;
	}

	public synchronized MagazineSeriesPortType getMagazineSeriesPortType() {
		if (magazineSeriesPortType == null) {
			magazineSeriesPortType = stapiSoapPortTypesProvider.getMagazineSeriesPortType();
		}
		return magazineSeriesPortType;
	}

	public synchronized Material getMaterial() {
		if (material == null) {
			material = new Material(getMaterialPortType(), apiKeySupplier);
		}
		return material;
	}

	public synchronized MaterialPortType getMaterialPortType() {
		if (materialPortType == null) {
			materialPortType = stapiSoapPortTypesProvider.getMaterialPortType();
		}
		return materialPortType;
	}

	public synchronized MedicalCondition getMedicalCondition() {
		if (medicalCondition == null) {
			medicalCondition = new MedicalCondition(getMedicalConditionPortType(), apiKeySupplier);
		}
		return medicalCondition;
	}

	public synchronized MedicalConditionPortType getMedicalConditionPortType() {
		if (medicalConditionPortType == null) {
			medicalConditionPortType = stapiSoapPortTypesProvider.getMedicalConditionPortType();
		}
		return medicalConditionPortType;
	}

	public synchronized Movie getMovie() {
		if (movie == null) {
			movie = new Movie(getMoviePortType(), apiKeySupplier);
		}
		return movie;
	}

	public synchronized MoviePortType getMoviePortType() {
		if (moviePortType == null) {
			moviePortType = stapiSoapPortTypesProvider.getMoviePortType();
		}
		return moviePortType;
	}

	public synchronized Occupation getOccupation() {
		if (occupation == null) {
			occupation = new Occupation(getOccupationPortType(), apiKeySupplier);
		}
		return occupation;
	}

	public synchronized OccupationPortType getOccupationPortType() {
		if (occupationPortType == null) {
			occupationPortType = stapiSoapPortTypesProvider.getOccupationPortType();
		}
		return occupationPortType;
	}

	public synchronized Organization getOrganization() {
		if (organization == null) {
			organization = new Organization(getOrganizationPortType(), apiKeySupplier);
		}
		return organization;
	}

	public synchronized OrganizationPortType getOrganizationPortType() {
		if (organizationPortType == null) {
			organizationPortType = stapiSoapPortTypesProvider.getOrganizationPortType();
		}
		return organizationPortType;
	}

	public synchronized Performer getPerformer() {
		if (performer == null) {
			performer = new Performer(getPerformerPortType(), apiKeySupplier);
		}
		return performer;
	}

	public synchronized PerformerPortType getPerformerPortType() {
		if (performerPortType == null) {
			performerPortType = stapiSoapPortTypesProvider.getPerformerPortType();
		}
		return performerPortType;
	}

	public synchronized Season getSeason() {
		if (season == null) {
			season = new Season(getSeasonPortType(), apiKeySupplier);
		}
		return season;
	}

	public synchronized SeasonPortType getSeasonPortType() {
		if (seasonPortType == null) {
			seasonPortType = stapiSoapPortTypesProvider.getSeasonPortType();
		}
		return seasonPortType;
	}

	public synchronized Series getSeries() {
		if (series == null) {
			series = new Series(getSeriesPortType(), apiKeySupplier);
		}
		return series;
	}

	public synchronized SeriesPortType getSeriesPortType() {
		if (seriesPortType == null) {
			seriesPortType = stapiSoapPortTypesProvider.getSeriesPortType();
		}
		return seriesPortType;
	}

	public synchronized Soundtrack getSoundtrack() {
		if (soundtrack == null) {
			soundtrack = new Soundtrack(getSoundtrackPortType(), apiKeySupplier);
		}
		return soundtrack;
	}

	public synchronized SoundtrackPortType getSoundtrackPortType() {
		if (soundtrackPortType == null) {
			soundtrackPortType = stapiSoapPortTypesProvider.getSoundtrackPortType();
		}
		return soundtrackPortType;
	}

	public synchronized Spacecraft getSpacecraft() {
		if (spacecraft == null) {
			spacecraft = new Spacecraft(getSpacecraftPortType(), apiKeySupplier);
		}
		return spacecraft;
	}

	public synchronized SpacecraftPortType getSpacecraftPortType() {
		if (spacecraftPortType == null) {
			spacecraftPortType = stapiSoapPortTypesProvider.getSpacecraftPortType();
		}
		return spacecraftPortType;
	}

	public synchronized SpacecraftClass getSpacecraftClass() {
		if (spacecraftClass == null) {
			spacecraftClass = new SpacecraftClass(getSpacecraftClassPortType(), apiKeySupplier);
		}
		return spacecraftClass;
	}

	public synchronized SpacecraftClassPortType getSpacecraftClassPortType() {
		if (spacecraftClassPortType == null) {
			spacecraftClassPortType = stapiSoapPortTypesProvider.getSpacecraftClassPortType();
		}
		return spacecraftClassPortType;
	}

	public synchronized Species getSpecies() {
		if (species == null) {
			species = new Species(getSpeciesPortType(), apiKeySupplier);
		}
		return species;
	}

	public synchronized SpeciesPortType getSpeciesPortType() {
		if (speciesPortType == null) {
			speciesPortType = stapiSoapPortTypesProvider.getSpeciesPortType();
		}
		return speciesPortType;
	}

	public synchronized Staff getStaff() {
		if (staff == null) {
			staff = new Staff(getStaffPortType(), apiKeySupplier);
		}
		return staff;
	}

	public synchronized StaffPortType getStaffPortType() {
		if (staffPortType == null) {
			staffPortType = stapiSoapPortTypesProvider.getStaffPortType();
		}
		return staffPortType;
	}

	public synchronized Technology getTechnology() {
		if (technology == null) {
			technology = new Technology(getTechnologyPortType(), apiKeySupplier);
		}
		return technology;
	}

	public synchronized TechnologyPortType getTechnologyPortType() {
		if (technologyPortType == null) {
			technologyPortType = stapiSoapPortTypesProvider.getTechnologyPortType();
		}
		return technologyPortType;
	}

	public synchronized Title getTitle() {
		if (title == null) {
			title = new Title(getTitlePortType(), apiKeySupplier);
		}
		return title;
	}

	public synchronized TitlePortType getTitlePortType() {
		if (titlePortType == null) {
			titlePortType = stapiSoapPortTypesProvider.getTitlePortType();
		}
		return titlePortType;
	}

	public synchronized TradingCard getTradingCard() {
		if (tradingCard == null) {
			tradingCard = new TradingCard(getTradingCardPortType(), apiKeySupplier);
		}
		return tradingCard;
	}

	public synchronized TradingCardPortType getTradingCardPortType() {
		if (tradingCardPortType == null) {
			tradingCardPortType = stapiSoapPortTypesProvider.getTradingCardPortType();
		}
		return tradingCardPortType;
	}

	public synchronized TradingCardDeck getTradingCardDeck() {
		if (tradingCardDeck == null) {
			tradingCardDeck = new TradingCardDeck(getTradingCardDeckPortType(), apiKeySupplier);
		}
		return tradingCardDeck;
	}

	public synchronized TradingCardDeckPortType getTradingCardDeckPortType() {
		if (tradingCardDeckPortType == null) {
			tradingCardDeckPortType = stapiSoapPortTypesProvider.getTradingCardDeckPortType();
		}
		return tradingCardDeckPortType;
	}

	public synchronized TradingCardSet getTradingCardSet() {
		if (tradingCardSet == null) {
			tradingCardSet = new TradingCardSet(getTradingCardSetPortType(), apiKeySupplier);
		}
		return tradingCardSet;
	}

	public synchronized TradingCardSetPortType getTradingCardSetPortType() {
		if (tradingCardSetPortType == null) {
			tradingCardSetPortType = stapiSoapPortTypesProvider.getTradingCardSetPortType();
		}
		return tradingCardSetPortType;
	}

	public synchronized VideoGame getVideoGame() {
		if (videoGame == null) {
			videoGame = new VideoGame(getVideoGamePortType(), apiKeySupplier);
		}
		return videoGame;
	}

	public synchronized VideoGamePortType getVideoGamePortType() {
		if (videoGamePortType == null) {
			videoGamePortType = stapiSoapPortTypesProvider.getVideoGamePortType();
		}
		return videoGamePortType;
	}

	public synchronized VideoRelease getVideoRelease() {
		if (videoRelease == null) {
			videoRelease = new VideoRelease(getVideoReleasePortType(), apiKeySupplier);
		}
		return videoRelease;
	}

	public synchronized VideoReleasePortType getVideoReleasePortType() {
		if (videoReleasePortType == null) {
			videoReleasePortType = stapiSoapPortTypesProvider.getVideoReleasePortType();
		}
		return videoReleasePortType;
	}

	public synchronized Weapon getWeapon() {
		if (weapon == null) {
			weapon = new Weapon(getWeaponPortType(), apiKeySupplier);
		}
		return weapon;
	}

	public synchronized WeaponPortType getWeaponPortType() {
		if (weaponPortType == null) {
			weaponPortType = stapiSoapPortTypesProvider.getWeaponPortType();
		}
		return weaponPortType;
	}

}
