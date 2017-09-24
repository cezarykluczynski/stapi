package com.cezarykluczynski.stapi.client.api;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectPortType;
import com.cezarykluczynski.stapi.client.v1.soap.BookPortType;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicCollectionPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripPortType;
import com.cezarykluczynski.stapi.client.v1.soap.ComicsPortType;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyPortType;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodePortType;
import com.cezarykluczynski.stapi.client.v1.soap.FoodPortType;
import com.cezarykluczynski.stapi.client.v1.soap.LiteraturePortType;
import com.cezarykluczynski.stapi.client.v1.soap.LocationPortType;
import com.cezarykluczynski.stapi.client.v1.soap.MagazinePortType;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialPortType;
import com.cezarykluczynski.stapi.client.v1.soap.MoviePortType;
import com.cezarykluczynski.stapi.client.v1.soap.OrganizationPortType;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftPortType;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesPortType;
import com.cezarykluczynski.stapi.client.v1.soap.StaffPortType;
import com.cezarykluczynski.stapi.client.v1.soap.TitlePortType;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckPortType;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardPortType;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetPortType;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGamePortType;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleasePortType;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponPortType;
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
		comicsPortType = stapiSoapPortTypesProvider.getComicsPortType();
		comicStripPortType = stapiSoapPortTypesProvider.getComicStripPortType();
		comicCollectionPortType = stapiSoapPortTypesProvider.getComicCollectionPortType();
		speciesPortType = stapiSoapPortTypesProvider.getSpeciesPortType();
		organizationPortType = stapiSoapPortTypesProvider.getOrganizationPortType();
		foodPortType = stapiSoapPortTypesProvider.getFoodPortType();
		locationPortType = stapiSoapPortTypesProvider.getLocationPortType();
		bookSeriesPortType = stapiSoapPortTypesProvider.getBookSeriesPortType();
		bookPortType = stapiSoapPortTypesProvider.getBookPortType();
		magazinePortType = stapiSoapPortTypesProvider.getMagazinePortType();
		magazineSeriesPortType = stapiSoapPortTypesProvider.getMagazineSeriesPortType();
		literaturePortType = stapiSoapPortTypesProvider.getLiteraturePortType();
		seasonPortType = stapiSoapPortTypesProvider.getSeasonPortType();
		videoReleasePortType = stapiSoapPortTypesProvider.getVideoReleasePortType();
		tradingCardSetPortType = stapiSoapPortTypesProvider.getTradingCardSetPortType();
		tradingCardDeckPortType = stapiSoapPortTypesProvider.getTradingCardDeckPortType();
		tradingCardPortType = stapiSoapPortTypesProvider.getTradingCardPortType();
		videoGamePortType = stapiSoapPortTypesProvider.getVideoGamePortType();
		soundtrackPortType = stapiSoapPortTypesProvider.getSoundtrackPortType();
		weaponPortType = stapiSoapPortTypesProvider.getWeaponPortType();
		spacecraftClassPortType = stapiSoapPortTypesProvider.getSpacecraftClassPortType();
		spacecraftPortType = stapiSoapPortTypesProvider.getSpacecraftPortType();
		titlePortType = stapiSoapPortTypesProvider.getTitlePortType();
		materialPortType = stapiSoapPortTypesProvider.getMaterialPortType();
	}

}
