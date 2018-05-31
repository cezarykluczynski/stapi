package com.cezarykluczynski.stapi.etl.template.planet.processor;

import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AstronomicalObjectWikitextProcessor implements ItemProcessor<String, AstronomicalObjectType> {

	private static final String ROGUE_PLANET = "Rogue planet";
	private static final String M_CLASS_1 = "M-class";
	private static final String M_CLASS_2 = "M Class";
	private static final String CLASS_D = "Class D";
	private static final String CLASS_H = "Class H";
	private static final String CLASS_L = "Class L";
	private static final String CLASS_M = "Class M";
	private static final String CLASS_K_PLANET = "Class K planet";
	private static final String CLASS_Y_PLANET = "Class Y planet";
	private static final String GAS_GIANT = "Gas giant";
	private static final String PLANETOID = "Planetoid";
	private static final String MOON = "Moon";
	private static final String CLASS_4_MOON = "Class 4 moon";
	private static final String NON = "non-";
	private static final String FORMERLY = "formerly";
	private static final String PLANET = "Planet";
	private static final String ASTEROID = "Asteroid";
	private static final String STAR = "Star";
	private static final String BINARY_SUN = "Binary sun";
	private static final String PROTOPLANET = "Protoplanet";

	private final WikitextApi wikitextApi;

	public AstronomicalObjectWikitextProcessor(WikitextApi wikitextApi) {
		this.wikitextApi = wikitextApi;
	}

	@Override
	@SuppressWarnings("CyclomaticComplexity")
	public AstronomicalObjectType process(String item) throws Exception {
		List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(item);

		if (pageLinkList.isEmpty()) {
			return null;
		}

		for (PageLink pageLink : pageLinkList) {
			String title = pageLink.getTitle();
			AstronomicalObjectType astronomicalObjectType = null;
			if (ROGUE_PLANET.equalsIgnoreCase(title)) {
				astronomicalObjectType = AstronomicalObjectType.ROGUE_PLANET;
			} else if (M_CLASS_1.equalsIgnoreCase(title) || M_CLASS_2.equalsIgnoreCase(title) || CLASS_M.equalsIgnoreCase(title)) {
				String itemLowerCase = item.toLowerCase();
				return itemLowerCase.contains(NON) || itemLowerCase.contains(FORMERLY) ? AstronomicalObjectType.PLANET
						: AstronomicalObjectType.M_CLASS_PLANET;
			} else if (CLASS_D.equalsIgnoreCase(title)) {
				astronomicalObjectType = AstronomicalObjectType.D_CLASS_PLANET;
			} else if (CLASS_H.equalsIgnoreCase(title)) {
				astronomicalObjectType = AstronomicalObjectType.H_CLASS_PLANET;
			} else if (CLASS_L.equalsIgnoreCase(title)) {
				astronomicalObjectType = AstronomicalObjectType.L_CLASS_PLANET;
			} else if (CLASS_K_PLANET.equalsIgnoreCase(title)) {
				astronomicalObjectType = AstronomicalObjectType.K_CLASS_PLANET;
			} else if (CLASS_Y_PLANET.equalsIgnoreCase(title)) {
				astronomicalObjectType = AstronomicalObjectType.Y_CLASS_PLANET;
			} else if (GAS_GIANT.equalsIgnoreCase(title)) {
				astronomicalObjectType = AstronomicalObjectType.GAS_GIANT_PLANET;
			} else if (PLANETOID.equalsIgnoreCase(title)) {
				astronomicalObjectType = AstronomicalObjectType.PLANETOID;
			} else if (MOON.equalsIgnoreCase(title) || CLASS_4_MOON.equalsIgnoreCase(title)) {
				astronomicalObjectType = AstronomicalObjectType.MOON;
			} else if (PLANET.equalsIgnoreCase(title)) {
				astronomicalObjectType = AstronomicalObjectType.PLANET;
			} else if (ASTEROID.equalsIgnoreCase(title)) {
				astronomicalObjectType = AstronomicalObjectType.ASTEROID;
			} else if (STAR.equalsIgnoreCase(title)) {
				astronomicalObjectType = AstronomicalObjectType.STAR;
			} else if (BINARY_SUN.equalsIgnoreCase(title)) {
				astronomicalObjectType = AstronomicalObjectType.STAR; // TODO version 2: Change to BINARY_STAR
			} else if (PROTOPLANET.equalsIgnoreCase(title)) {
				astronomicalObjectType = AstronomicalObjectType.PLANET; // TODO version 2: change to PROTOPLANET
			}

			if (astronomicalObjectType != null) {
				return astronomicalObjectType;
			}
		}

		return null;
	}

}
