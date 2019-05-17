package com.cezarykluczynski.stapi.etl.organization.creation.service;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationNameFilter {

	private static final List<String> NOT_AN_ORGANIZATIONS = Lists.newArrayList("Intelligence agency", "Platoon", "Task force", "Dungeon", "Baldwin",
			"Flotilla", "League", "Assault team", "Medical complex", "Column", "Squad", "Assault fleet", "Squadron", "Research facilities", "Fleet",
			"Grand jury", "Legion", "Regiment","Fleet organization", "Garrison", "Division", "Dynasty", "Armada", "Battalion", "Attack wing",
			"Brigade", "Militia", "Battery (unit)", "Democrat", "Service branch", "Kingdom", "City council", "News agency",
			"Secretary of Exploration", "Medical ethics board", "Civic center");

	private static final List<String> ORGANIZATIONS = Lists.newArrayList(
			"Bolian restaurant", "Lazon II labor camp", "Vulcan Institute for Defensive Arts", "Singha refugee camp", "Lincoln", "Datsun",
			"Quarren Ministry of Health", "Paradan government", "Empire", "San Francisco bar", "Division of Planetary Operations", "Bowling alley",
			"Grain processing center", "Vulcan nightclub", "Central Bank of Lissepia", "Central Hospital of Altair IV", "Morn's",
			"Quark's Bar, Grill, Gaming House and Holosuite Arcade", "Pyrex", "Klingon training academy", "Le Coeur de Lion", "Tagruato",
			"Ferengi government", "Tilonus Institute for Mental Disorders", "Big Creek Manufacturing and Sales Co.", "Coalition of Madena",
			"SafeTech", "Elba II asylum", "Dodge", "Schutzstaffel", "Akritirian maximum security detention facility", "Vic's lounge",
			"Sisters of Hope Infirmary", "NATO", "Nygean prison ship", "Institute", "Bajoran Ministry of Commerce", "Hot Dog on a Stick",
			"Stryker", "Royal Family of Luria", "Division of Control", "Cardassian Ministry for the Refutation of Bajoran Fairy Tales", "Baylor",
			"Weiss Institute for Theoretical Physics", "Commission on Political Traitors", "Ravinok", "Enolian transport", "Vlugta government",
			"Hierarchy", "Osaarian merchant fleet", "Gallitep labor camp", "Bajoran Center for Science", "Romulan military", "Milliways",
			"Mahl'kom group", "Wendy's", "Atrean government", "BMR", "Manzanar", "Chamber of Horrors", "Walworth", "Cardassian Ministry of Justice",
			"Wehrmacht", "Warp Five program", "Bank of America", "Legation of Unity", "Norcadian Museum of Entomology", "Bajoran civil police",
			"Purification squad", "T. Purser Hardware and Plumbing", "Fusion (night club)", "Port of San Francisco bar", "Canamar", "Logicians",
			"Circle of Exobiology", "Café des Artistes", "Tombs", "Kzinti government", "Klingon shock troops", "The Ox and Lamb",
			"Tarahong detention center", "Grubeco", "Dozaria", "Destroyer unit", "Orpheum", "Allcroft", "Ruling Tribunal of the Aquans",
			"College of Medical Sciences", "Camillo's", "Tantalus colony", "Orion Institute of Cosmology", "Radio Corporation of America", "Dragons",
			"Duronom", "Cosimo's", "Wedgewood", "Dominion", "Abaddon's Repository of Lost Treasures", "Packard", "Lithium cracking station",
			"Hutet labor camp", "Information kiosk", "Bank of Bolias", "Galaxy wing", "Romulan Ministry of Science", "Hotbox", "Circle of Archeology",
			"Corleone's", "Promenade", "Central Bureau of Penology", "New Sydney police bureau", "Yan-Isleth", "Coalition of Planets",
			"Tarahong government", "Klingon restaurant", "Teatro alla Scala", "Kran-Tobol prison", "Maintenance", "Sullivan's",
			"Academy of Starfleet Surgeons", "Academy wrestling team", "Crosley", "Central Bank of Bajor", "Sena", "Army of Evil",
			"Cardassian Bureau of Identification", "Prometheus Gifts and Knicknaks", "Federation Bureau of Planetary Treaties", "Cloning facility",
			"V'Shar", "Velos VII internment camp", "Trillian government", "Laktivia recreational facility", "Institute for Unauthorized Experiments",
			"Department of Temporal Investigations", "Parliament of Angel I", "Plaza", "Errand of Mercy Meal Support", "University of Culat",
			"MI5", "Moët et Chandon", "Orbital traffic control", "Klingon prison planet", "Hall of Fame", "Lexington", "KDA",
			"Tanugan security force", "Ministry of Elders", "Chevrolet", "Royal Museum of Epsilon Hydra VII", "Grunsfeld Institute for Astrophysics",
			"University of Oneamisu", "Benetton", "SeaWorld", "Cavalry", "University of Nairobi", "Cardassian military", "Trelonian government",
			"Port of San Francisco", "Lactra VII zoo", "United States armed forces", "University of Hurkos", "Federal Bureau of Investigation",
			"Feinberg's Loan and Pawn", "KGB", "Massachusetts Institute of Technology", "UESPA headquarters", "Bajoran Institute of Science",
			"NX-Control", "Special Weapons and Tactics", "Resuscitation team", "Gammadan mining facility", "Chemist's shop",
			"Department of Investigation", "Twinlab", "National Aeronautics and Space Administration", "Deathwatch facility",
			"United Federation of Planets", "Museum of Kyrian Heritage", "Confederacy of Surak", "Dartmoor", "Holo-processing plant",
			"Ariannus Ministry of Health", "Stern Institute for Astrophysics", "Rawlings", "Gestapo", "Bajoran Freight and Shipping Authority",
			"Plexicorp", "Bajoran customs office", "Lakeside", "University of Alpha Centauri", "Federation Astrophysical survey", "Nokia",
			"Vulcan Ministry of Information", "Spacehab", "Oshare", "Board of Liquidators", "Central Museum of Remmil VI", "Pine Tree Bar and Grill",
			"University of Bajor", "BOQ", "OCC", "Pfaltzgraff", "University of Tomobiki", "Hyster", "Tribunal of Alpha III", "Paul Institute of Mars",
			"Metropolitan", "Science Institute of Bilana III", "A-6 excavation team", "Quarren power plant", "Department of Cartography",
			"Academy of Science", "Gocke's House of Mirrors", "Congress of Economic Advisors", "Council of Ministers", "Cumberland",
			"Karemman Ministry of Trade", "Wurlitzer", "San Francisco Department of Sanitation", "Assay office", "Vosk's squadron", "Batal", "Mazda",
			"Luftwaffe regional command", "Council of Physicians", "Mathenite government", "Sovereign Dynasty of Krios Prime", "Airtrax", "Marsport",
			"Skipworth and Co", "Fiorella's", "Circles of Science", "University of Copernicus", "Federation Bureau of Industrialization",
			"Cardassian Institute of Art", "Burgerland", "Niners", "Zee Magnees Institute for Theoretical Research", "Klaestron government",
			"Oak Street station", "Carlton", "Federation Council on Exobiology", "Hekaran government", "University of Betazed", "Bothan government",
			"Biggs'", "Elasian Council of Nobles", "Confederate States of America", "Banzai Institute for Advanced Studies", "Angosian military",
			"University of Mississippi", "University of Texas", "California Institute of Technology", "University of California, Santa Cruz",
			"Zefram Cochrane Institute for Advanced Theoretical Physics", "University of California, Berkeley", "University of Manitoba",
			"University of Hawaii", "Terran cabinet", "Rutian police", "Bureau of Identification", "Betazed government", "Tellun",
			"New Sydney police", "Suliban government", "Ministry of Information", "Mexican army", "Council of Nobles", "Quaternary star systems",
			"Ministry of Justice");

	public Match isAnOrganization(String organizationName) {
		if (NOT_AN_ORGANIZATIONS.contains(organizationName)) {
			return Match.IS_NOT_AN_ORGANIZATION;
		} else if (ORGANIZATIONS.contains(organizationName)) {
			return Match.IS_AN_ORGANIZATION;
		}

		return Match.UNKNOWN_RESULT;
	}

	public enum Match {

		IS_AN_ORGANIZATION,
		IS_NOT_AN_ORGANIZATION,
		UNKNOWN_RESULT

	}

}
