package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.etl.template.common.service.PageFilter;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NonQualifiedCharacterFilter implements PageFilter<String> {

	private static final List<String> REDIRECTS_TO_LISTS_PARTS = Lists.newArrayList(
			"Unnamed", "List of", "Mirror universe", "USS Voyager", "USS Enterprise", "EnterpriseNX", "Enterprise NX-01", "USS Excelsior",
			"Cardassian", "Human ", "Bajoran ", "Humanoid ", "Vulcan ", "Cardassians ", "Klingon ", "Starfleet ", "Nausicaan ", "Bolian ",
			"Triannon ", "Talosian ", "Romulan ", "Andorian ", "Tellarite ", "Ajilon Prime ", "Qualor II ", "Enaran ", "Volan III ", "Ferengi ",
			"Jem'Hadar ", "Gaia Klingon ", " Gaia Human", "Antedian ", "Markalian ", "Yridian Chez Sandrine", "Avenger Orion ", "Krenim wife",
			" 24th ", " 22nd ", " 23rd ", " 19th ", "Iotian ", " 20th ", "Starfleet Academy", " dockmaster", "Betazoid Gift Box",
			"hologram", "Illusion ", "Quarren ", "Dabo girls", "DS9 ", "Sigma Draconis", "villager", " therapist", "Pets Troi dog", "computer voice",
			"crewman", "lieutenant", "personnel", "officer", "crewmember", "ensign", "technician", "deputy", "guard", "helmsman", "Admiral",
			" native", "criminal", "tribesman", "engineer", "security", "soldier", "characters", " cadet", " ambassador", " supervisor", "crewmen",
			"Deep Space 9", "Changeling", "Humanoid Figure", "Hotel Royale", " settler", " official", " flight controller", "deleted scene",
			" guard", "woman", " elder", " warrior", " miner", " laborer", "inhabitant", " survivor", " slave", "colonist", "prisoner", "overseer",
			"manager", "captain", "bureaucrat", " thief", " patron", " worker", " delegate", " servant", "teammember", " commander", " student",
			" female", " male", "troglyte", "interrogation operator", "policeman", "cameraman", "passersby", "gun moll", " spectator", " species",
			"Hospital Ship 4-2", "Tak Tak shopper", "Memory Alpha", "People in Luther Sloan's memory", " nurse", " doctor", " medtech", " minister",
			"scientist", " courter", " chef", " augment", " representative", "ancient humanoid", "Only boy", "Only girl", " barista", " companion",
			" commandant", "Chief ", " astronaut", " patient", " gatherer", "Doppelgänger", " waiter", " juggler", " roommate", " coyote",
			" councilor", " visitor", " escort", " lawgiver", " renegade", " rebel", "automated repair station", "Peliar Zel", " followers",
			" scholar", " glinn", " informant", " passenger", " trooper", " preacher", " undercover", " archaeologist", " bartender", " stablehand",
			"school children", " cowboy", " attendee", " assassin", "boarding party", "Ekosian-Zeon", " associate", " commando", " pilot",
			"starbase operations", " observer", " sentinel", "Wadi man", " assistant", " member", " dignitary", " resident", "Illusory people",
			" tactician", " old man", " citizen", " passerby", " civilian", " musician", "Qomar fan ", "Mari girl", "Mari boy", "Avenger Terran"
	);

	private static final List<String> REDIRECTS_TO_LISTS_EXACT = Lists.newArrayList(
			"Gul", "Sub-Commander", "Corporal", "Sergeant", "Colonel", "Major", "Constable", "Private", "android", "computer", "Legate", "Mister",
			"Third (rank)", "Nurse", "General", "Thot", "MACO", "Dr.", "Wraith", "Conservator", "Regent", "Intendant", "Alice (ship)", "Beauregard",
			"M-113 creature", "Minister", "Vedek", "Targ", "Biomimetic lifeform", "Corvan gilvo", "Kai", "Quago", "El-Adrel IV lifeform", "Vulcan",
			"Ambassador", "First Minister", "Windy", "Freebus", "Rhylo", "Kar", "Runabout", "Universal translator", "Allocator", "Chancellor",
			"Vissian", "Andorian", "cosmic cloud", "Prylar", "Androna", "Medical Consultant Program Beta one", "Cytoplasmic lifeform", "Yaderans",
			"Slaver weapon", "Lt. Commander", "Chief", "Maura", "PADD", "Becky", "Kalar", "Devil", "wolf", "Breezy", "anthropoid ape", "Vanessa Bova",
			"Klingon"
	);

	@Override
	public boolean shouldBeFilteredOut(String characterName) {
		return StringUtil.containsAnyIgnoreCase(characterName, REDIRECTS_TO_LISTS_PARTS) || REDIRECTS_TO_LISTS_EXACT.contains(characterName);
	}

}
