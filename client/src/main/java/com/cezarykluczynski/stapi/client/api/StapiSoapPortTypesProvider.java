package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.v1.soap.AnimalPortType;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalService;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectPortType;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectService;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionPortType;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionService;
import com.cezarykluczynski.stapi.client.v1.soap.BookPortType;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesService;
import com.cezarykluczynski.stapi.client.v1.soap.BookService;
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
import com.cezarykluczynski.stapi.client.v1.soap.ConflictPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictService;
import com.cezarykluczynski.stapi.client.v1.soap.ElementPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ElementService;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodePortType;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeService;
import com.cezarykluczynski.stapi.client.v1.soap.FoodPortType;
import com.cezarykluczynski.stapi.client.v1.soap.FoodService;
import com.cezarykluczynski.stapi.client.v1.soap.LiteraturePortType;
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureService;
import com.cezarykluczynski.stapi.client.v1.soap.LocationPortType;
import com.cezarykluczynski.stapi.client.v1.soap.LocationService;
import com.cezarykluczynski.stapi.client.v1.soap.MagazinePortType;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesService;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineService;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialPortType;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialService;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionPortType;
import com.cezarykluczynski.stapi.client.v1.soap.MedicalConditionService;
import com.cezarykluczynski.stapi.client.v1.soap.MoviePortType;
import com.cezarykluczynski.stapi.client.v1.soap.MovieService;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationPortType;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationService;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationPortType;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationService;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerPortType;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerService;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonService;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesService;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackService;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassService;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftService;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesService;
import com.cezarykluczynski.stapi.client.v1.soap.StaffPortType;
import com.cezarykluczynski.stapi.client.v1.soap.StaffService;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyPortType;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyService;
import com.cezarykluczynski.stapi.client.v1.soap.TitlePortType;
import com.cezarykluczynski.stapi.client.v1.soap.TitleService;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckPortType;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckService;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardPortType;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardService;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetPortType;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetService;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGamePortType;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameService;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleasePortType;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseService;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponPortType;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponService;

import javax.xml.ws.BindingProvider;
import java.util.Map;

public class StapiSoapPortTypesProvider extends AbstractStapiClient implements StapiClient {

	private String apiUrl;

	private SeriesPortType seriesPortType;

	private PerformerPortType performerPortType;

	private StaffPortType staffPortType;

	private CharacterPortType characterPortType;

	private EpisodePortType episodePortType;

	private MoviePortType moviePortType;

	private AstronomicalObjectPortType astronomicalObjectPortType;

	private CompanyPortType companyPortType;

	private ComicSeriesPortType comicSeriesPortType;

	private ComicsPortType comicsPortType;

	private ComicStripPortType comicStripPortType;

	private ComicCollectionPortType comicCollectionPortType;

	private SpeciesPortType speciesPortType;

	private OrganizationPortType organizationPortType;

	private FoodPortType foodPortType;

	private LocationPortType locationPortType;

	private BookSeriesPortType bookSeriesPortType;

	private BookPortType bookPortType;

	private BookCollectionPortType bookCollectionPortType;

	private MagazinePortType magazinePortType;

	private MagazineSeriesPortType magazineSeriesPortType;

	private LiteraturePortType literaturePortType;

	private SeasonPortType seasonPortType;

	private VideoReleasePortType videoReleasePortType;

	private TradingCardSetPortType tradingCardSetPortType;

	private TradingCardDeckPortType tradingCardDeckPortType;

	private TradingCardPortType tradingCardPortType;

	private VideoGamePortType videoGamePortType;

	private SoundtrackPortType soundtrackPortType;

	private WeaponPortType weaponPortType;

	private SpacecraftClassPortType spacecraftClassPortType;

	private SpacecraftPortType spacecraftPortType;

	private TitlePortType titlePortType;

	private MaterialPortType materialPortType;

	private ConflictPortType conflictPortType;

	private AnimalPortType animalPortType;

	private ElementPortType elementPortType;

	private MedicalConditionPortType medicalConditionPortType;

	private TechnologyPortType technologyPortType;

	private OccupationPortType occupationPortType;

	public StapiSoapPortTypesProvider(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public synchronized SeriesPortType getSeriesPortType() {
		if (seriesPortType == null) {
			seriesPortType = changeUrl(new SeriesService().getSeriesPortType());
		}
		return seriesPortType;
	}

	public synchronized PerformerPortType getPerformerPortType() {
		if (performerPortType == null) {
			performerPortType = changeUrl(new PerformerService().getPerformerPortType());
		}
		return performerPortType;
	}

	public synchronized StaffPortType getStaffPortType() {
		if (staffPortType == null) {
			staffPortType = changeUrl(new StaffService().getStaffPortType());
		}
		return staffPortType;
	}

	public synchronized CharacterPortType getCharacterPortType() {
		if (characterPortType == null) {
			characterPortType = changeUrl(new CharacterService().getCharacterPortType());
		}
		return characterPortType;
	}

	public synchronized EpisodePortType getEpisodePortType() {
		if (episodePortType == null) {
			episodePortType = changeUrl(new EpisodeService().getEpisodePortType());
		}
		return episodePortType;
	}

	public synchronized MoviePortType getMoviePortType() {
		if (moviePortType == null) {
			moviePortType = changeUrl(new MovieService().getMoviePortType());
		}
		return moviePortType;
	}

	public synchronized AstronomicalObjectPortType getAstronomicalObjectPortType() {
		if (astronomicalObjectPortType == null) {
			astronomicalObjectPortType = changeUrl(new AstronomicalObjectService().getAstronomicalObjectPortType());
		}
		return astronomicalObjectPortType;
	}

	public synchronized CompanyPortType getCompanyPortType() {
		if (companyPortType == null) {
			companyPortType = changeUrl(new CompanyService().getCompanyPortType());
		}
		return companyPortType;
	}

	public synchronized ComicSeriesPortType getComicSeriesPortType() {
		if (comicSeriesPortType == null) {
			comicSeriesPortType = changeUrl(new ComicSeriesService().getComicSeriesPortType());
		}
		return comicSeriesPortType;
	}

	public synchronized ComicsPortType getComicsPortType() {
		if (comicsPortType == null) {
			comicsPortType = changeUrl(new ComicsService().getComicsPortType());
		}
		return comicsPortType;
	}

	public synchronized ComicStripPortType getComicStripPortType() {
		if (comicStripPortType == null) {
			comicStripPortType = changeUrl(new ComicStripService().getComicStripPortType());
		}
		return comicStripPortType;
	}

	public synchronized ComicCollectionPortType getComicCollectionPortType() {
		if (comicCollectionPortType == null) {
			comicCollectionPortType = changeUrl(new ComicCollectionService().getComicCollectionPortType());
		}
		return comicCollectionPortType;
	}

	public synchronized SpeciesPortType getSpeciesPortType() {
		if (speciesPortType == null) {
			speciesPortType = changeUrl(new SpeciesService().getSpeciesPortType());
		}
		return speciesPortType;
	}

	public synchronized OrganizationPortType getOrganizationPortType() {
		if (organizationPortType == null) {
			organizationPortType = changeUrl(new OrganizationService().getOrganizationPortType());
		}
		return organizationPortType;
	}

	public synchronized FoodPortType getFoodPortType() {
		if (foodPortType == null) {
			foodPortType = changeUrl(new FoodService().getFoodPortType());
		}
		return foodPortType;
	}

	public synchronized LocationPortType getLocationPortType() {
		if (locationPortType == null) {
			locationPortType = changeUrl(new LocationService().getLocationPortType());
		}
		return locationPortType;
	}

	public synchronized BookSeriesPortType getBookSeriesPortType() {
		if (bookSeriesPortType == null) {
			bookSeriesPortType = changeUrl(new BookSeriesService().getBookSeriesPortType());
		}
		return bookSeriesPortType;
	}

	public synchronized BookPortType getBookPortType() {
		if (bookPortType == null) {
			bookPortType = changeUrl(new BookService().getBookPortType());
		}
		return bookPortType;
	}

	public synchronized BookCollectionPortType getBookCollectionPortType() {
		if (bookCollectionPortType == null) {
			bookCollectionPortType = changeUrl(new BookCollectionService().getBookCollectionPortType());
		}
		return bookCollectionPortType;
	}

	public synchronized MagazinePortType getMagazinePortType() {
		if (magazinePortType == null) {
			magazinePortType = changeUrl(new MagazineService().getMagazinePortType());
		}
		return magazinePortType;
	}

	public synchronized MagazineSeriesPortType getMagazineSeriesPortType() {
		if (magazineSeriesPortType == null) {
			magazineSeriesPortType = changeUrl(new MagazineSeriesService().getMagazineSeriesPortType());
		}
		return magazineSeriesPortType;
	}

	public synchronized LiteraturePortType getLiteraturePortType() {
		if (literaturePortType == null) {
			literaturePortType = changeUrl(new LiteratureService().getLiteraturePortType());
		}
		return literaturePortType;
	}

	public synchronized SeasonPortType getSeasonPortType() {
		if (seasonPortType == null) {
			seasonPortType = changeUrl(new SeasonService().getSeasonPortType());
		}
		return seasonPortType;
	}

	public synchronized VideoReleasePortType getVideoReleasePortType() {
		if (videoReleasePortType == null) {
			videoReleasePortType = changeUrl(new VideoReleaseService().getVideoReleasePortType());
		}
		return videoReleasePortType;
	}

	public synchronized TradingCardSetPortType getTradingCardSetPortType() {
		if (tradingCardSetPortType == null) {
			tradingCardSetPortType = changeUrl(new TradingCardSetService().getTradingCardSetPortType());
		}
		return tradingCardSetPortType;
	}

	public synchronized TradingCardDeckPortType getTradingCardDeckPortType() {
		if (tradingCardDeckPortType == null) {
			tradingCardDeckPortType = changeUrl(new TradingCardDeckService().getTradingCardDeckPortType());
		}
		return tradingCardDeckPortType;
	}

	public synchronized TradingCardPortType getTradingCardPortType() {
		if (tradingCardPortType == null) {
			tradingCardPortType = changeUrl(new TradingCardService().getTradingCardPortType());
		}
		return tradingCardPortType;
	}

	public synchronized VideoGamePortType getVideoGamePortType() {
		if (videoGamePortType == null) {
			videoGamePortType = changeUrl(new VideoGameService().getVideoGamePortType());
		}
		return videoGamePortType;
	}

	public synchronized SoundtrackPortType getSoundtrackPortType() {
		if (soundtrackPortType == null) {
			soundtrackPortType = changeUrl(new SoundtrackService().getSoundtrackPortType());
		}
		return soundtrackPortType;
	}

	public synchronized WeaponPortType getWeaponPortType() {
		if (weaponPortType == null) {
			weaponPortType = changeUrl(new WeaponService().getWeaponPortType());
		}
		return weaponPortType;
	}

	public synchronized SpacecraftClassPortType getSpacecraftClassPortType() {
		if (spacecraftClassPortType == null) {
			spacecraftClassPortType = changeUrl(new SpacecraftClassService().getSpacecraftClassPortType());
		}
		return spacecraftClassPortType;
	}

	public synchronized SpacecraftPortType getSpacecraftPortType() {
		if (spacecraftPortType == null) {
			spacecraftPortType = changeUrl(new SpacecraftService().getSpacecraftPortType());
		}
		return spacecraftPortType;
	}

	public synchronized TitlePortType getTitlePortType() {
		if (titlePortType == null) {
			titlePortType = changeUrl(new TitleService().getTitlePortType());
		}
		return titlePortType;
	}

	public synchronized MaterialPortType getMaterialPortType() {
		if (materialPortType == null) {
			materialPortType = changeUrl(new MaterialService().getMaterialPortType());
		}
		return materialPortType;
	}

	public synchronized ConflictPortType getConflictPortType() {
		if (conflictPortType == null) {
			conflictPortType = changeUrl(new ConflictService().getConflictPortType());
		}
		return conflictPortType;
	}

	public synchronized AnimalPortType getAnimalPortType() {
		if (animalPortType == null) {
			animalPortType = changeUrl(new AnimalService().getAnimalPortType());
		}
		return animalPortType;
	}

	public synchronized ElementPortType getElementPortType() {
		if (elementPortType == null) {
			elementPortType = changeUrl(new ElementService().getElementPortType());
		}
		return elementPortType;
	}

	public synchronized MedicalConditionPortType getMedicalConditionPortType() {
		if (medicalConditionPortType == null) {
			medicalConditionPortType = changeUrl(new MedicalConditionService().getMedicalConditionPortType());
		}
		return medicalConditionPortType;
	}

	public synchronized TechnologyPortType getTechnologyPortType() {
		if (technologyPortType == null) {
			technologyPortType = changeUrl(new TechnologyService().getTechnologyPortType());
		}
		return technologyPortType;
	}

	public synchronized OccupationPortType getOccupationPortType() {
		if (occupationPortType == null) {
			occupationPortType = changeUrl(new OccupationService().getOccupationPortType());
		}
		return occupationPortType;
	}

	private <T> T changeUrl(T service) {
		if (apiUrl == null || CANONICAL_API_HTTP_URL.equals(apiUrl)) {
			return service;
		}

		BindingProvider bindingProvider = (BindingProvider) service;
		Map<String, Object> requestContext = bindingProvider.getRequestContext();
		String newServiceUrl = changeBaseHttpUrl(apiUrl, (String) requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newServiceUrl);
		return service;
	}

}
