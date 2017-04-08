package com.cezarykluczynski.stapi.etl.episode.creation.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformanceDTO
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.enums.PerformanceType
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApiImpl
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class EpisodePerformancesExtractingProcessorTest extends Specification {

	private static final String UNKNOWN_PAGE_SECTION = 'Unknown page section'

	private static final List<PageSection> PAGE_SECTION_LIST = Lists.newArrayList(
			new PageSection(level: 2, text: 'Summary', anchor: 'Summary', number: '1', byteOffset: 1657, wikitext: ''),
			new PageSection(level: 2, text: 'Links and references', anchor: 'Links_and_references', number: '4', byteOffset: 62396, wikitext:''),
			new PageSection(level: 3, text: 'Starring', anchor: 'Starring', number: '4.1', byteOffset: 62423, wikitext: '* [[Patrick Stewart]] ' +
					'as [[Captain|Capt.]] [[Jean-Luc Picard]]\n* [[Jonathan Frakes]] as [[Commander|Cmdr.]] [[William T. Riker]])'),
			new PageSection(level: 3, text: 'Also starring', anchor: 'Also_starring', number: '4.2', byteOffset: 62570, wikitext:
					'* [[LeVar Burton]] as [[Lieutenant Commander|Lt. Cmdr.]] [[Geordi La Forge]]\n* [[Michael Dorn]] as [[Lieutenant]] ' +
					'[[Worf]]\n* [[Gates McFadden]] as [[Doctor|Dr.]] [[Beverly Crusher]]\n* [[Marina Sirtis]] as [[Counselor]] ' +
					'[[Deanna Troi]]\n* [[Brent Spiner]] as [[Lieutenant Commander|Lt. Commander]] [[Data]])'),
			new PageSection(level: 3, text: 'Guest stars', anchor: 'Guest_stars', number: '4.3', byteOffset: 62898, wikitext:
					'* [[John de Lancie]] as [[Q]]\n* [[Andreas Katsulas]] as [[Tomalak]]\n* [[Clyde Kusatsu]] as [[Nakamura]]\n* ' +
					'[[Patti Yasutake]] as [[Alyssa Ogawa]])'),
			new PageSection(level: 3, text: 'Special guest stars', anchor: 'Special_guest_stars', number: '4.4', byteOffset: 63064, wikitext:
					'* [[Denise Crosby]] as [[Tasha Yar]]\n* [[Colm Meaney]] as [[Miles O\'Brien|O\'Brien]])'),
			new PageSection(level: 3, text: 'Co-stars', anchor: 'Co-stars', number: '4.5', byteOffset: 63177, wikitext: '* [[Pamela Kosh]] ' +
					'as [[Jessel]]\n* [[Tim Kelleher]] as [[Lieutenant|Lt.]] [[Gaines]]\n* [[Alison Brooks]] as [[Ensign]] ' +
					'[[Nell Chilton|Chilton]]\n* [[Stephen Matthew Garvin]] as [[Starfleet future Enterprise-D ensign 001|Ensign]]\n* ' +
					'[[Majel Barrett]] as [[Computer Voice]])'),
			new PageSection(level: 3, text: 'Uncredited co-stars', anchor: 'Uncredited_co-stars', number: '4.6', byteOffset: 63464, wikitext:
					'* [[David Keith Anderson]] as [[Armstrong]]\n* [[Aspen]] as [[Cat|Data\'s cat]]\n* [[Cameron]] as [[Kellogg]]\n* ' +
					'[[Tracee Lee Cocco]] as [[Jae]]\n* [[Marijane Cole]] as [[Unnamed USS Enterprise (NCC-1701-D) operations division ' +
					'personnel#Female operations division officer (2364)|operations division officer]]\n* [[Nyra Crenshaw]] as ' +
					'[[Unnamed USS Enterprise (NCC-1701-D) operations division personnel#Female ops ensign|operations division officer]]\n* ' +
					'{{dis|Crystal|cat}} as [[Cat|Data\'s cat]]\n* [[Brian Demonbreun]] as [[Andrew Powell]]\n* [[Fido]] as [[Cat|Data\'s cat]]\n* ' +
					'[[Holiday Freeman]] as [[Unnamed USS Enterprise (NCC-1701-D) command division personnel#Female command division ' +
					'officer|command division officer]]\n"'),
			new PageSection(level: 3, text: 'Stunt double', anchor: 'Stunt_double', number: '4.7', byteOffset: 67875, wikitext:
					'* [[LaFaye Baker]] as [[stunt double]] for [[Alison Brooks]])'),
			new PageSection(level: 3, text: 'Stand-ins', anchor: 'Stand-ins', number: '4.8', byteOffset: 67958, wikitext:
					'* [[David Keith Anderson]] &ndash; [[stand-in]] for [[LeVar Burton]]\n* [[Richard Sarstedt]] &ndash; stand-in for ' +
					'[[Jonathan Frakes]] and [[John de Lancie]]\n* [[Guy Vardaman]] &ndash; [[photo double]] for [[Brent Spiner]])'),
			new PageSection(level: 3, text: 'References', anchor: 'References', number: '4.9', byteOffset: 68554, wikitext: '[[acetylcholine]]; ' +
					'[[alphabet]]; [[alternate timeline]]; [[amino acid]]; [[anti-time]]; [[Pets Data cats 001|Anti-time Data\'s cats]]; ' +
					'[[anti-time eruption]]; [[anti-time future]]; [[anti-time past]]; [[anti-time present]]; [[antimatter containment]]; ' +
					'[[bacillus spray]]; [[balalaika]]; [[Betazed]]; [[Black Sea]]; \'\'[[Black Sea at Night]]\'\'; [[blood gas analysis]]; ' +
					'[[boatswain\'s whistle]]; {{SSU|Bozeman}}; {{revname|Leah|Brahms}}; [[Chicken and the Egg]]; [[Cambridge University]]; ' +
					'[[cat]]; [[cerebral cortex]]; [[Chateau Picard]]; [[cloaking device]]; {{SSU|Concord}}; {{Class|D\'deridex}}; ' +
					'[[darjeeling tea]]; [[Daystrom Institute]]; [[dealer]]; [[deflector dish]]; [[Devron system]]; [[DNA]]; [[Earl Grey tea]]; ' +
					'[[Earth]]; [[Earth Station McKinley]]; [[emergency power]]; [[etymology]]; [[Farpoint Mission]]; [[Farpoint Station]]; ' +
					'[[Federation]]; [[fireplace]]; [[fire-suppression system]]; [[focal p'),
			new PageSection(level: 4, text: 'Other references', anchor: 'Other_references', number: '4.9.1', byteOffset: 71354, wikitext:
					'* \'\'\'USS \'\'Pasteur\'\' dedication plaque:\'\'\' [[Starfleet Advanced Technologies|Advanced Technologies]]; ' +
							'[[Aesculapius]]; {{revname|Greg|Agalsoff|Starfleet}}; [[Apollo]]; {{revname|Rick|Berman|Admiral}}; ' +
							'{{revname|Bob|Blackman|Starfleet}}; {{revname|Ed|Charnock, Jr.|Starfleet}}; {{revname|Ed|Cooper|Starfleet}}; ' +
							'{{revname|Daniel|Curry|Captain}}; {{revname|Dick|D\'Angelo|Lieutenant}}; {{revname|Wendy|Drapanas|Starfleet}}; ' +
							'[[Starfleet Corps of Engineers|Engineering Division]]; {{revname|Alex|Fenin}}; [[Starfleet Operations|Fleet ' +
							'Operations]]; {{revname|Jerry|Fleck|Captain}}; {{revname|Arlene|Fukai|Starfleet}}; {{revname|Steve|Gausche, ' +
							'Jr.|Starfleet}}; {{revname|Bill|George|Starfleet}};  {{revname|Merri|Howard|Admiral}}; ' +
							'{{revname|Richard|James|Captain}}; {{revname|Ronnie|Knox|Starfleet}}; {{revname|Peter|Lauritson|Admiral}}; ' +
							'{{revname|David|Livingston|Admiral}}; {{revname|Jim|Magdalen'),
			new PageSection(level: 4, text: 'Unreferenced material', anchor: 'Unreferenced_material', number: '4.9.2', byteOffset: 73201, wikitext:
					'[[Androna]]; [[Freighter|merchant ship]]; [[Selar]]; [[Terrellian transport ship]])'),
			new PageSection(level: 3, text: 'Timeline', anchor: 'Timeline', number: '4.10', byteOffset: 73315, wikitext: '; [[3.5 billion years ' +
					'ago]] (alternate): Q shows Picard prehistoric [[France]], the site of the first [[amino acid]] formation on [[Earth]].\n; ' +
					'[[2364]] ([[anti-time past]]): The \'\'Enterprise\'\'-D is diverted to the [[Romulan Neutral Zone]] from the [[Farpoint ' +
					'Station]] mission.\n; [[2370]] ([[anti-time present]]): ...\n; [[anti-time future|2395 in an alternate future timeframe]]: ' +
					'Data is a professor at Cambridge. Picard is retired in France. Riker has been elevated to admiral. The Klingons have taken ' +
					'over the Romulan Empire. Crusher is captain of the \'\'Pasteur\'\'. Troi has died. La Forge is a writer and the father of ' +
					'three children.)'),
			new PageSection(level: 3, text: 'External links', anchor: 'External_links', number: '4.11', byteOffset: 73970, wikitext:
					'* {{mbeta-quote|All Good Things...}}\n* {{wikipedia-quote|All Good Things... (Star Trek: The Next Generation)|All Good ' +
							'Things...}}\n* {{startrek.com|all-good-things-part-i|All Good Things..., Part I}}\n* ' +
							'{{startrek.com|all-good-things-part-ii|All Good Things..., Part II}}\n\n{{Q episodes}}\n{{featured|date=August ' +
							'2004|id=23544}}\n{{TNG nav|season=7|last={{e|Preemptive Strike}}|next={{final|episode}}}}\n\n[[de:Gestern, Heute, ' +
							'Morgen, Teil I]]\n[[es:All Good Things...]]\n[[fr:All Good Things...]]\n[[ja:永遠への旅（エピソード）]]\n[[nl:All Good ' +
							'Things...]]\n[[sv:All Good Things...]]\n[[Category:TNG episodes]])')
	)

	private EpisodePerformancesExtractingProcessor episodePerformancesExtractor

	void setup() {
		episodePerformancesExtractor = new EpisodePerformancesExtractingProcessor(new WikitextApiImpl())
	}

	void "logs error and returns when page section list does not contain 'Links and references'"() {
		given:
		Page page = Mock()

		when:
		List<EpisodePerformanceDTO> episodePerformances = episodePerformancesExtractor.process(page)

		then:
		1 * page.sections >> Lists.newArrayList()
		1 * page.title
		0 * _
		episodePerformances.empty
	}

	void "logs error when there are sections other than specified"() {
		given:
		Page page = Mock()
		PageSection linksAndReferencesPageSection = Mock()
		PageSection starringPageSection = Mock()
		PageSection unknownPageSection = Mock()

		when:
		episodePerformancesExtractor.process(page)

		then:
		page.sections >> Lists.newArrayList(
				linksAndReferencesPageSection,
				starringPageSection,
				unknownPageSection
		)
		3 * linksAndReferencesPageSection.text >> EpisodePerformancesExtractingProcessor.LINKS_AND_REFERENCES
		2 * linksAndReferencesPageSection.number >> '4'
		2 * starringPageSection.text >> EpisodePerformancesExtractingProcessor.STARRING
		1 * unknownPageSection.number >> '4.1'
		2 * unknownPageSection.text >> UNKNOWN_PAGE_SECTION
	}

	void "extracts the right links from the right sections"() {
		given:
		Page page = new Page(
				sections: PAGE_SECTION_LIST
		)

		when:
		List<EpisodePerformanceDTO> episodePerformances = episodePerformancesExtractor.process(page)

		then:
		episodePerformances.size() == 27
		episodePerformances[0] == createEpisodePerformanceDTO('Patrick Stewart', 'Jean-Luc Picard', PerformanceType.PERFORMANCE)
		episodePerformances[1] == createEpisodePerformanceDTO('Jonathan Frakes', 'William T. Riker', PerformanceType.PERFORMANCE)
		episodePerformances[2] == createEpisodePerformanceDTO('LeVar Burton', 'Geordi La Forge', PerformanceType.PERFORMANCE)
		episodePerformances[3] == createEpisodePerformanceDTO('Michael Dorn', 'Worf', PerformanceType.PERFORMANCE)
		episodePerformances[4] == createEpisodePerformanceDTO('Gates McFadden', 'Beverly Crusher', PerformanceType.PERFORMANCE)
		episodePerformances[5] == createEpisodePerformanceDTO('Marina Sirtis', 'Deanna Troi', PerformanceType.PERFORMANCE)
		episodePerformances[6] == createEpisodePerformanceDTO('Brent Spiner', 'Data', PerformanceType.PERFORMANCE)
		episodePerformances[7] == createEpisodePerformanceDTO('John de Lancie', 'Q', PerformanceType.PERFORMANCE)
		episodePerformances[8] == createEpisodePerformanceDTO('Andreas Katsulas', 'Tomalak', PerformanceType.PERFORMANCE)
		episodePerformances[9] == createEpisodePerformanceDTO('Clyde Kusatsu', 'Nakamura', PerformanceType.PERFORMANCE)
		episodePerformances[10] == createEpisodePerformanceDTO('Patti Yasutake', 'Alyssa Ogawa', PerformanceType.PERFORMANCE)
		episodePerformances[11] == createEpisodePerformanceDTO('Denise Crosby', 'Tasha Yar', PerformanceType.PERFORMANCE)
		episodePerformances[12] == createEpisodePerformanceDTO('Colm Meaney', 'Miles O\'Brien', PerformanceType.PERFORMANCE)
		episodePerformances[13] == createEpisodePerformanceDTO('Pamela Kosh', 'Jessel', PerformanceType.PERFORMANCE)
		episodePerformances[14] == createEpisodePerformanceDTO('Tim Kelleher', 'Gaines', PerformanceType.PERFORMANCE)
		episodePerformances[15] == createEpisodePerformanceDTO('Alison Brooks', 'Nell Chilton', PerformanceType.PERFORMANCE)
		episodePerformances[16] == createEpisodePerformanceDTO('Stephen Matthew Garvin', 'Starfleet future Enterprise-D ensign 001',
				PerformanceType.PERFORMANCE)
		episodePerformances[17] == createEpisodePerformanceDTO('Majel Barrett', 'Computer Voice', PerformanceType.PERFORMANCE)
		episodePerformances[18] == createEpisodePerformanceDTO('David Keith Anderson', 'Armstrong', PerformanceType.PERFORMANCE)
		episodePerformances[19] == createEpisodePerformanceDTO('Cameron', 'Kellogg', PerformanceType.PERFORMANCE)
		episodePerformances[20] == createEpisodePerformanceDTO('Tracee Lee Cocco', 'Jae', PerformanceType.PERFORMANCE)
		episodePerformances[21] == createEpisodePerformanceDTO('Brian Demonbreun', 'Andrew Powell', PerformanceType.PERFORMANCE)
		episodePerformances[22] == createEpisodePerformanceDTOPerformingFor('LaFaye Baker', 'Alison Brooks', PerformanceType.STUNT)
		episodePerformances[23] == createEpisodePerformanceDTOPerformingFor('David Keith Anderson', 'LeVar Burton', PerformanceType.STAND_IN)
		episodePerformances[24] == createEpisodePerformanceDTOPerformingFor('Richard Sarstedt', 'Jonathan Frakes', PerformanceType.STAND_IN)
		episodePerformances[25] == createEpisodePerformanceDTOPerformingFor('Richard Sarstedt', 'John de Lancie', PerformanceType.STAND_IN)
		episodePerformances[26] == createEpisodePerformanceDTOPerformingFor('Guy Vardaman', 'Brent Spiner', PerformanceType.STAND_IN)
	}

	private static EpisodePerformanceDTO createEpisodePerformanceDTO(String performerName, String characterName, PerformanceType performanceType) {
		new EpisodePerformanceDTO(performerName: performerName, characterName: characterName, performanceType: performanceType)
	}

	private static EpisodePerformanceDTO createEpisodePerformanceDTOPerformingFor(String performerName, String performingFor,
			PerformanceType performanceType) {
		new EpisodePerformanceDTO(performerName: performerName, performingFor: performingFor, performanceType: performanceType)
	}
}
