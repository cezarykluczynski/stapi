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
import com.cezarykluczynski.stapi.client.v1.soap.MoviePortType;
import com.cezarykluczynski.stapi.client.v1.soap.MovieService;
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

	@Getter
	private BookSeriesPortType bookSeriesPortType;

	@Getter
	private BookPortType bookPortType;

	@Getter
	private BookCollectionPortType bookCollectionPortType;

	@Getter
	private MagazinePortType magazinePortType;

	@Getter
	private MagazineSeriesPortType magazineSeriesPortType;

	@Getter
	private LiteraturePortType literaturePortType;

	@Getter
	private SeasonPortType seasonPortType;

	@Getter
	private VideoReleasePortType videoReleasePortType;

	@Getter
	private TradingCardSetPortType tradingCardSetPortType;

	@Getter
	private TradingCardDeckPortType tradingCardDeckPortType;

	@Getter
	private TradingCardPortType tradingCardPortType;

	@Getter
	private VideoGamePortType videoGamePortType;

	@Getter
	private SoundtrackPortType soundtrackPortType;

	@Getter
	private WeaponPortType weaponPortType;

	@Getter
	private SpacecraftClassPortType spacecraftClassPortType;

	@Getter
	private SpacecraftPortType spacecraftPortType;

	@Getter
	private TitlePortType titlePortType;

	@Getter
	private MaterialPortType materialPortType;

	@Getter
	private ConflictPortType conflictPortType;

	@Getter
	private AnimalPortType animalPortType;

	@Getter
	private ElementPortType elementPortType;

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
		bookSeriesPortType = new BookSeriesService().getBookSeriesPortType();
		bookPortType = new BookService().getBookPortType();
		bookCollectionPortType = new BookCollectionService().getBookCollectionPortType();
		magazinePortType = new MagazineService().getMagazinePortType();
		magazineSeriesPortType = new MagazineSeriesService().getMagazineSeriesPortType();
		literaturePortType = new LiteratureService().getLiteraturePortType();
		seasonPortType = new SeasonService().getSeasonPortType();
		videoReleasePortType = new VideoReleaseService().getVideoReleasePortType();
		tradingCardSetPortType = new TradingCardSetService().getTradingCardSetPortType();
		tradingCardDeckPortType = new TradingCardDeckService().getTradingCardDeckPortType();
		tradingCardPortType = new TradingCardService().getTradingCardPortType();
		videoGamePortType = new VideoGameService().getVideoGamePortType();
		soundtrackPortType = new SoundtrackService().getSoundtrackPortType();
		weaponPortType = new WeaponService().getWeaponPortType();
		spacecraftClassPortType = new SpacecraftClassService().getSpacecraftClassPortType();
		spacecraftPortType = new SpacecraftService().getSpacecraftPortType();
		titlePortType = new TitleService().getTitlePortType();
		materialPortType = new MaterialService().getMaterialPortType();
		conflictPortType = new ConflictService().getConflictPortType();
		animalPortType = new AnimalService().getAnimalPortType();
		elementPortType = new ElementService().getElementPortType();
	}

	public StapiSoapPortTypesProvider(String apiUrl) {
		this.apiUrl = apiUrl;
		seriesPortType = changeUrl(new SeriesService().getSeriesPortType());
		performerPortType = changeUrl(new PerformerService().getPerformerPortType());
		staffPortType = changeUrl(new StaffService().getStaffPortType());
		characterPortType = changeUrl(new CharacterService().getCharacterPortType());
		episodePortType = changeUrl(new EpisodeService().getEpisodePortType());
		moviePortType = changeUrl(new MovieService().getMoviePortType());
		astronomicalObjectPortType = changeUrl(new AstronomicalObjectService().getAstronomicalObjectPortType());
		companyPortType = changeUrl(new CompanyService().getCompanyPortType());
		comicSeriesPortType = changeUrl(new ComicSeriesService().getComicSeriesPortType());
		comicsPortType = changeUrl(new ComicsService().getComicsPortType());
		comicStripPortType = changeUrl(new ComicStripService().getComicStripPortType());
		comicCollectionPortType = changeUrl(new ComicCollectionService().getComicCollectionPortType());
		speciesPortType = changeUrl(new SpeciesService().getSpeciesPortType());
		organizationPortType = changeUrl(new OrganizationService().getOrganizationPortType());
		foodPortType = changeUrl(new FoodService().getFoodPortType());
		locationPortType = changeUrl(new LocationService().getLocationPortType());
		bookSeriesPortType = changeUrl(new BookSeriesService().getBookSeriesPortType());
		bookPortType = changeUrl(new BookService().getBookPortType());
		bookCollectionPortType = changeUrl(new BookCollectionService().getBookCollectionPortType());
		magazinePortType = changeUrl(new MagazineService().getMagazinePortType());
		magazineSeriesPortType = changeUrl(new MagazineSeriesService().getMagazineSeriesPortType());
		literaturePortType = changeUrl(new LiteratureService().getLiteraturePortType());
		seasonPortType = changeUrl(new SeasonService().getSeasonPortType());
		videoReleasePortType = changeUrl(new VideoReleaseService().getVideoReleasePortType());
		tradingCardSetPortType = changeUrl(new TradingCardSetService().getTradingCardSetPortType());
		tradingCardDeckPortType = changeUrl(new TradingCardDeckService().getTradingCardDeckPortType());
		tradingCardPortType = changeUrl(new TradingCardService().getTradingCardPortType());
		videoGamePortType = changeUrl(new VideoGameService().getVideoGamePortType());
		soundtrackPortType = changeUrl(new SoundtrackService().getSoundtrackPortType());
		weaponPortType = changeUrl(new WeaponService().getWeaponPortType());
		spacecraftClassPortType = changeUrl(new SpacecraftClassService().getSpacecraftClassPortType());
		spacecraftPortType = changeUrl(new SpacecraftService().getSpacecraftPortType());
		titlePortType = changeUrl(new TitleService().getTitlePortType());
		materialPortType = changeUrl(new MaterialService().getMaterialPortType());
		conflictPortType = changeUrl(new ConflictService().getConflictPortType());
		animalPortType = changeUrl(new AnimalService().getAnimalPortType());
		elementPortType = changeUrl(new ElementService().getElementPortType());
	}

	private <T> T changeUrl(T service) {
		BindingProvider bindingProvider = (BindingProvider) service;
		Map<String, Object> requestContext = bindingProvider.getRequestContext();
		String newServiceUrl = changeBaseUrl(apiUrl, (String) requestContext.get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));
		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, newServiceUrl);
		return service;
	}

}
