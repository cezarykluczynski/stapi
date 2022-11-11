package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.etl.template.common.service.PageFilter;
import com.cezarykluczynski.stapi.etl.util.CharacterUtil;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class NonQualifiedCharacterFilter implements PageFilter<String> {

	private static final List<String> REDIRECTS_TO_LISTS_PARTS = Lists.newArrayList(
			"Unnamed", "List of", "Mirror universe", "USS Voyager", "USS Enterprise", "EnterpriseNX", "Enterprise NX-01", "USS Excelsior", "Human ",
			"Bajoran ", "Humanoid ", "Vulcan ", "Cardassians ", "Klingon ", "Starfleet ", "Nausicaan ", "Bolian ", "Triannon ", "Talosian ",
			"Romulan ", "Andorian ", "Tellarite ", "Ajilon Prime ", "Qualor II ", "Enaran ", "Volan III ", "Ferengi ", "Jem'Hadar ", "Gaia Klingon ",
			" Gaia Human", "Antedian ", "Markalian ", "Yridian Chez Sandrine", "Avenger Orion ", "Krenim wife", " 24th ", " 22nd ", " 23rd ",
			" 19th ", "Iotian ", " 20th ", "Starfleet Academy", " dockmaster", "Betazoid Gift Box", "Illusion ", "Dabo girls", "DS9 ",
			"Sigma Draconis", "villager", " therapist", "Pets Troi dog", "computer voice", "personnel", "deputy", "guard", "helmsman", "criminal",
			"tribesman", "engineer", "soldier", "characters", " cadet", " ambassador", " supervisor", "crewmen", "Deep Space 9", "Hotel Royale",
			" settler", " official", " flight controller", "deleted scene", " guard", " elder", " warrior", " miner", " laborer", "inhabitant",
			" survivor", " slave", "prisoner", "overseer", "manager", "bureaucrat", " thief", " patron", " worker", " delegate", " servant",
			"teammember", " student", "troglyte", "interrogation operator", "policeman", "cameraman", "passersby", "gun moll", " spectator",
			" species", "Hospital Ship 4-2", "Tak Tak shopper", "Memory Alpha", "People in Luther Sloan's memory", " nurse", " medtech", " minister",
			"scientist", " courter", " augment", " representative", "ancient humanoid", "Only boy", "Only girl", " barista", " companion",
			" commandant", "Chief ", " astronaut", " patient", " gatherer", "Doppelgänger", " waiter", " juggler", " roommate", " coyote",
			" councilor", " visitor", " escort", " lawgiver", " renegade", " rebel", "automated repair station", "Peliar Zel", " followers",
			" scholar", " glinn", " informant", " passenger", " trooper", " preacher", " undercover", " archaeologist", " bartender", " stablehand",
			"school children", " cowboy", " attendee", " assassin", "boarding party", "Ekosian-Zeon", " associate", " commando", " pilot",
			"starbase operations", " observer", " sentinel", "Wadi man", " assistant", " member", " dignitary", " resident", "Illusory people",
			" tactician", " old man", " citizen", " passerby", " civilian", " musician", "Qomar fan ", "Mari girl", "Mari boy", "Avenger Terran"
	);

	private static final List<String> REDIRECTS_TO_LISTS_EXACT = Lists.newArrayList(
			"Gul", "Sub-Commander", "Corporal", "Sergeant", "Colonel", "Major", "Constable", "Private", "android", "computer", "Legate", "Mister",
			"Third (rank)", "Nurse", "General", "MACO", "Dr.", "Conservator", "Regent", "Intendant", "Alice (ship)", "M-113 creature", "Minister",
			"Vedek", "Targ", "Biomimetic lifeform", "Corvan gilvo", "Kai", "Quago", "El-Adrel IV lifeform", "Vulcan", "Ambassador", "First Minister",
			"Windy", "Runabout", "Universal translator", "Allocator", "Chancellor", "Vissian", "Andorian", "cosmic cloud", "Prylar",
			"Medical Consultant Program Beta one", "Cytoplasmic lifeform", "Yaderans", "Slaver weapon", "Lt. Commander", "Chief", "PADD", "wolf",
			"Breezy", "anthropoid ape", "Klingon"
	);

	private final Set<String> loggedCharacterNames = Sets.newHashSet();

	@Override
	public boolean shouldBeFilteredOut(String characterName) {
		final boolean characterNameContainsRedirectToListsParts = StringUtil.containsAnyIgnoreCase(characterName, REDIRECTS_TO_LISTS_PARTS);
		final boolean characterIsExactRedirectToList = REDIRECTS_TO_LISTS_EXACT.contains(characterName);
		final boolean characterIsOneOfMany = CharacterUtil.isOneOfMany(characterName);
		boolean shouldBeFilteredOut = characterNameContainsRedirectToListsParts || characterIsExactRedirectToList || characterIsOneOfMany;
		if (shouldBeFilteredOut && !characterIsOneOfMany) {
			if (!loggedCharacterNames.contains(characterName)) {
				log.info("Character name {} filtered out from character-performer pair, because nameContainsRedirectToListsParts: {}, "
						+ "isExactRedirectToList: {}", characterName, characterNameContainsRedirectToListsParts, characterIsExactRedirectToList);
				loggedCharacterNames.add(characterName);
			}
		}
		return shouldBeFilteredOut;
	}

}
