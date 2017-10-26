package com.cezarykluczynski.stapi.etl.movie.creation.processor;

import com.cezarykluczynski.stapi.etl.movie.creation.dto.MovieLinkedTitlesDTO;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@SuppressWarnings("MultipleStringLiterals")
public class MovieLinkedTitlesProcessor implements ItemProcessor<List<PageSection>, MovieLinkedTitlesDTO> {

	private static final List<String> IGNORABLE_SECTION_EXACT_TITLE_LIST = Lists.newArrayList("Score Recorded and Mixed at",
			"Theme from ''[[TOS|Star Trek]]'' TV Series", "Grateful acknowledgment is made to the", "[[Spot|Cat]] by", "Rocket Propulsion by",
			"Certain Models Manufactured at", "[[USS Enterprise technical assistant 1|Technical Assistant]]s",
			"Based upon ''[[Star Trek]]'' created by", "Animatic Data Effects by", "[[Jean-Luc Picard]]", "[[William T. Riker]]", "[[Data]]/[[B-4]]",
			"[[Geordi La Forge]]", "[[Worf]]", "[[Deanna Troi]]", "[[Beverly Crusher]]", "[[Shinzon]]", "{{dis|Viceroy|Reman}}",
			"Senator [[Tal'aura]]", "Commander [[Donatra]]", "Commander [[Suran]]", "Praetor [[Hiren]]", "Senator", "Helm Officer [[Branson]]",
			"[[Kathryn Janeway|Admiral Janeway]]", "[[Reman officer 002|Reman Officer]]", "Commanders", "[[Wesley Crusher]]", "[[Computer Voice]]",
			"Special Thanks to", "Theme from ''[[TOS|Star Trek]]'' Television Series", "\"[[Genesis Project]]\" by", "\"[[I Remember You]]\" by",
			"\"[[That Old Black Magic]]\" by", "\"[[Tangerine]]\" by", "Filmed in", "In Association with", "Time Travel",
			"Theme from [[TOS|Star Trek]] Television Series", "The Producers extend special thanks to",
			"The Producer also gratefully acknowledge the cooperation of the Department of the Navy and the department of Defense and the "
					+ "following individuals", "And the Officers and Men of", "Special Thanks To");
	private static final List<String> SCREENPLAY_AUTHORS_SECTION_EXACT_TITLE_LIST = Lists.newArrayList("Screenplay by");
	private static final List<String> WRITERS_SECTION_EXACT_TITLE_LIST = Lists.newArrayList("Written by");
	private static final List<String> STORY_AUTHORS_SECTION_EXACT_TITLE_LIST = Lists.newArrayList("Story by");
	private static final List<String> DIRECTORS_SECTION_EXACT_TITLE_LIST = Lists.newArrayList("Directed by", "Second Unit Director");
	private static final List<String> DIRECTORS_SECTION_MATHCES_TITLE_LIST = Lists.newArrayList("assistant director");
	private static final List<String> PRODUCERS_SECTION_EXACT_TITLE_LIST = Lists.newArrayList(
			"Produced by", "Executive Producers", "Co-Producers", "Assistant Producer", "Associate Producer",
			"Producer", "Unit Production Manager, Co-Producer", "Co-Producer", "Executive in Charge of Production",
			"Associate Producer", "Executive producer", "Associate Producers", "Co-Produced by");
	private static final List<String> STAFF_SECTION_EXACT_TITLE_LIST = Lists.newArrayList("[[I Hate You]]", "Set Security", "Dirt Removal",
			"Director of Photography", "Production Designer", "Edited by", "Music by", "Costume Designer", "Visual Effects Supervisor",
			"Unit Production Manager", "Stunt Coordinator", "Visual Effects Producer", "ILM Visual Effects Supervisor", "ILM Animation Supervisor",
			"ILM Visual Effects Producers", "Post Production Supervisor", "Production Supervisor", "Production Auditor", "Supervising Art Director",
			"Art Directors", "Assistant Art Director", "Concept Illustrators", "Graphic Designer", "Model Maker", "Set Designers",
			"Art Department Coordinator", "Art Department Researchers", "Storyboard Artist",
			"Delta Vega Creatures, Romulans, Insect and Aliens Designed by", "Set Decorator", "Assistant Decorator", "Leadperson",
			"Set Decoration Buyer", "On-Set Dresser", "Set Dressers", "Set Decorating Coordinator", "Script Supervisor", "Camera Operator/Steadicam",
			"First Assistant Photographer", "Second Assistant Photographer", "B Camera Operator", "B First Assistant Photographers",
			"B Second Assistant Photographer", "Scorpio Head Operator", "Technocrane Operator", "Film Loader", "Sound Mixer", "Boom Operator",
			"Utility Sound", "Video Assist", "First Assistant Editors", "Assistant Editor", "Visual Effects Editor",
			"Visual Effects Assistant Editor", "Post Production Coordinator", "Chief Lighting Technician", "Assistant Chief Lighting Technician",
			"Electricians", "Dimmer Operators", "Chief Rigging Electrician", "Assistant Rigging Electrician", "Rigging Electricians",
			"Fixtures Technicians", "First Company Grip", "Second Company Grip", "Dolly Grip Operators", "Grips", "First Company Rigging Grip",
			"Second Company Rigging Grip", "Rigging Grips", "Property Master", "Assistant Property Master", "Props", "Prop Manufacturing Supervisor",
			"Propmakers", "Costume Supervisor", "Assistant Costume Designer", "Costumers", "Set Costumers", "Additional Costumers",
			"Specialty Costume Supervisor", "Specialty Costumers", "Dyers/Agers", "Cutter/Fitters", "Table Persons", "Drapers",
			"Assistants to Costume Department", "Costume Illustrators", "Makeup Department Head", "Hair Department Head", "Key Hairstylist",
			"Hairstylists", "Vulcans and Romulans Created by", "Aliens Designed and Created by", "Production Coordinator",
			"Assistant Production Coordinators", "Production Secretary", "DGA Trainees", "Researcher", "Production Assistants", "Unit Publicist",
			"Catering By", "Still Photographer", "Medics", "Re-Recording Mixers", "Supervising Sound Editors", "Special Sound Effects and Montage",
			"Sound Designers", "ADR Supervisor", "Dialogue Supervisor", "Foley Supervisor", "Dialogue Editor", "Purchasing Agent", "Art Director",
			"Set Director", "Dolly Grip", "Assistant Art Directors", "Illustrator", "Lead Person", "Property Masters", " Location Manager",
			"Wrangler", "Craft Services", "Negative Cutting", "ADR Editors", "Recordists", "Orchestrations", "Assistant Production Auditors",
			"DGA Trainee", "Yosemite Climbing Sequence Provided by", "Coordinator", "Production Assistant", "Technical Advisors", "Stunt Riggers",
			"Assistant Photographers", "Paramedic", "Highest Descender Fall Recorded in the United States", "General Manager", "Location Manager",
			"Negative Cutting by", "First Assistant Climbing Photographer", "Starfleet Uniforms Designed by", "Illustrators",
			"Additional Photography", "Loader", "Second Company Grips", "Cable Person", "Borg Effects Created by:", "Project Foreperson",
			"Borg Department Heads", "Crew", "Borg Production Coordinator", "Electronic Appliances", "On Set Dresser", "Video Engineer",
			"Video Playback", "Key Costumer", " Additional Editing", "ADR Mixer", "Score Recorded and Mixed", "Production Office Coordinator",
			"Assistant Production Office Coordinators", "Assistant Auditors", "First Aid", "Wescam Provided by", "Cranes and Dollies by",
			"Production Coordinators", "Projectionist", "Negative Line-up", "Electronic Appliances by", "Video Playback Operator",
			"Additional Editing", "Unit Production Managers", "Property Persons", "Caters", "Horse Wrangler", "Rocket Propulsion by",
			"Negative Cutter", "Concept Designer", "Director of Photography", "Stage Manager", "Grip", "CG Software Developer", "Optical Line-Up",
			"CG Technical Assistants", "Systems Support Specialist", "CG Production Assistant", "Executive in Charge of Production", "ILM President",
			"Computer Systems Engineer", "Computer Programmer", "Computer System Administrator", "Operators", "Animators", "Systems Manager",
			"Still Photographers", "Director of Photography", "Photo Chemical Composites", "Starfleet Uniform Costumer", "Assistant Property Masters",
			"On Set Dressers", "Supervising Video Engineer", "Catering", "Studio Teacher", "Aerial Coordinators", "Animals by", "Animal Trainers",
			"Additional Voices", "Aerial Pilots", "Production Controller", "On-Set Art Director", "Assistant Set Decorator", "Film Loaders",
			"Video Operator", "Video Assists", "Generator Operator", "Technocrane Operators", "Set Pyrotechnics", "Tool Person",
			"Assistant Property Masters", "Lead Persons", "Set Designer", "Set Decoration", "Cutter/Fitter", "Tailors", "Milliners",
			"Specialty Costumes By", "Creatures Created By", "Creature Design By", "Assistant Hair Department Head", "Post Production Assistants",
			"Set Decoration Models by", "Playback/Graphics Manager", "Video Projectionist", "Production Secretaries", "Payroll Clerk",
			"Post Production Facilities Provided By", "Vocal Contractor", "Scoring Stage Managers", "Additional Score Preparation",
			"Art Department Coordinators", "Picture Car Coordinator", "Special Animation Effects", "Special Science Advisor", "Costume Designer",
			"Set Decorator", "Tool Persons", "Prop Master", "Graphics", "Production Illustrators", "Publicity", "Secretary", "Gaffer", "Key Grip",
			"Photographic Effects Director of Photography", "Production Illustrators", "Animation and Graphics", "Special Electronics",
			"Animation Effects", "Electronic Design", "Titles", "Scoring Mixer", "Cast of Characters", "Geometric Designs", "Directer of photography",
			"Production designer", "Costume designer", "Set Decorators", "Second Unit Director of Photography", "Cheif Costumers",
			"Lead Set Designer", "Production Illustrator", "First Assistant Auditor", "Trasportation Captain", "Caterer",
			"CG Modeling & Lighting Leads", "VG Modeling & Lighting", "CG Effects Animation Lead", "CG Effects Animators", "T.D. Leads", "T.D.'s",
			"Walk-Through Coordinator", "Technical Assistant", "Mechanical Crew", "Lead Compositor", "Conceptual Advisor", "Costumes Designed by",
			"Utility", "Best Boys", "Dolly Grips", "Assistant Property", "Lead Man", "Swing Gang", "Technical Advisor", "Alien Language Created by",
			"Dialogue Coach", "General Manager, ILM", "Additional Spacecraft Design", "Effects Animators", "Video Playback and Displays by",
			"Video Coordinator", "Stunt Coordinators", "Borg Effects Created by", "Key Costumers", "Key Set Costumer",
			"[[Vulcan]]s and [[Romulan]]s Created by", "Accounting Assistant", "ADR Mixers", "Re-Recorded at", "Live Environment Design by",
			"Lead Creative", "Senior Developer", "24 Frame Video Playback by", "Video Playback Producer", "24 Frame Playback Operators", "Rigger",
			"Dolly", "Jackal Mastiff Created by", "Special Props", "Key Costumers", "Production Intern", "Art Department Production Assistants",
			"Klingon Language Specialist", "Hairstlylist", "Scoring", "Underwater Director of Photography", "Effects Director of Photography",
			"Production Manager, ILM", "Whale Operators/Puppeteers", "Underwater Whale Photography", "Matte Photography", "Creatures Created by",
			"Process Coordinator", "[[Genesis Project]] by");
	private static final List<String> STAFF_SECTION_MATCHES_TITLE_LIST = Lists.newArrayList("technician", "klingon and vulcan prosthetics",
			"assistant to", "accountant", "digital", "supervisor", "casting", "construction", "visual effects", "music", "film editor", "camera",
			"make-up", "makeup", "camera operator", "special effects", "artist", "assistant photographers", "wardrobe", "paint", "computer animation",
			"transportation", "sound", "color", "foley", "assistants to", "secretary to", "riggers", "rigging", "editor", "orchestr",
			"location manager", "foreperson", "production associate", "craft service", "visual effect", "scanning", "assistant production",
			"hair stylist", "consultant", "model maker", "optical", "greensperson", "choreographer", "engineer", "greenperson", "3d ",
			"re-recording mixer", "recordist", "modelmaker", "sculptor", "special photographic effects", "mechanical design", "photographic effects",
			"miniature", "title design", "hairstylist");
	private static final List<String> PERFORMERS_SECTION_EXACT_TITLE_LIST = Lists.newArrayList("Cast", "Aliens", "Humans", "Cast",
			"Starfleet Personnel", "Nightclub", "Missile Complex", "Borg", "Aliens", "The Nexus", "Also Starring", "And as [[Spock]]",
			"The Merchantship", "The Klingons", "USS Grissom", "The Bar", "The Excelsior", "The Vulcans", "Others", "Voices", "In Old San Francisco",
			"Naval Personnel");
	private static final List<String> STUNT_PERFORMERS_SECTION_EXACT_TITLE_LIST = Lists.newArrayList("Stunts", "Stunt Players", "Stunt Borg",
			"High Fall Stunt", "Stunt Performers");
	private static final List<String> STUNT_PERFORMERS_SECTION_MATCHES_TITLE_LIST = Lists.newArrayList("stunt double for");
	private static final List<String> STAND_IN_PERFORMERS_SECTION_EXACT_TITLE_LIST = Lists.newArrayList("[[Stand-in|Stand-In]]s");
	private static final List<String> STAND_IN_PERFORMERS_SECTION_MATCHES_TITLE_LIST = Lists.newArrayList("climbing double");

	private final WikitextApi wikitextApi;

	public MovieLinkedTitlesProcessor(WikitextApi wikitextApi) {
		this.wikitextApi = wikitextApi;
	}

	@Override
	public MovieLinkedTitlesDTO process(List<PageSection> item) throws Exception {
		MovieLinkedTitlesDTO movieLinkedTitlesDTO = new MovieLinkedTitlesDTO();

		for (PageSection pageSection : item) {
			String pageSectionTitle = StringUtils.trim(pageSection.getText());
			if (pageSectionTitle.endsWith(":")) {
				pageSectionTitle = pageSectionTitle.substring(0, pageSectionTitle.length() - 1);
			}

			Set<List<String>> sectionLinkListSet = toSectionLinkListSet(pageSection.getWikitext());

			if (isIgnorableSection(pageSectionTitle)) {
				continue;
			}

			if (isWritersSection(pageSectionTitle)) {
				movieLinkedTitlesDTO.getWriters().addAll(sectionLinkListSet);
			} else if (isScreenplayAuthorsSection(pageSectionTitle)) {
				movieLinkedTitlesDTO.getScreenplayAuthors().addAll(sectionLinkListSet);
			} else if (isStoryAuthorsSection(pageSectionTitle)) {
				movieLinkedTitlesDTO.getStoryAuthors().addAll(sectionLinkListSet);
			} else if (isDirectorsSection(pageSectionTitle)) {
				movieLinkedTitlesDTO.getDirectors().addAll(sectionLinkListSet);
			} else if (isProducersSection(pageSectionTitle)) {
				movieLinkedTitlesDTO.getProducers().addAll(sectionLinkListSet);
			} else if (isStaffSection(pageSectionTitle)) {
				movieLinkedTitlesDTO.getStaff().addAll(sectionLinkListSet);
			} else if (isPerformersSection(pageSectionTitle)) {
				movieLinkedTitlesDTO.getPerformers().addAll(sectionLinkListSet);
			} else if (isStuntPerformersSection(pageSectionTitle)) {
				movieLinkedTitlesDTO.getStuntPerformers().addAll(sectionLinkListSet);
			} else if (isStandInPerformersSection(pageSectionTitle)) {
				movieLinkedTitlesDTO.getStandInPerformers().addAll(sectionLinkListSet);
			} else {
				log.warn("Section \"{}\" not mapped to any type of work in movie", pageSectionTitle);
			}
		}

		return movieLinkedTitlesDTO;
	}

	private Set<List<String>> toSectionLinkListSet(String wikitext) {
		Set<List<String>> wikitextLinks = Sets.newHashSet();
		Lists.newArrayList(wikitext.split("\n")).forEach(wikitextLine -> {
			wikitextLinks.add(wikitextApi.getPageTitlesFromWikitext(wikitextLine));
		});
		return wikitextLinks;
	}

	private boolean isWritersSection(String pageSectionTitle) {
		return WRITERS_SECTION_EXACT_TITLE_LIST.contains(pageSectionTitle);
	}

	private boolean isIgnorableSection(String pageSectionTitle) {
		return IGNORABLE_SECTION_EXACT_TITLE_LIST.contains(pageSectionTitle);
	}

	private boolean isScreenplayAuthorsSection(String pageSectionTitle) {
		return SCREENPLAY_AUTHORS_SECTION_EXACT_TITLE_LIST.contains(pageSectionTitle);
	}

	private boolean isStoryAuthorsSection(String pageSectionTitle) {
		return STORY_AUTHORS_SECTION_EXACT_TITLE_LIST.contains(pageSectionTitle);
	}

	private boolean isProducersSection(String pageSectionTitle) {
		return PRODUCERS_SECTION_EXACT_TITLE_LIST.contains(pageSectionTitle);
	}

	private static boolean isDirectorsSection(String pageSectionTitle) {
		return DIRECTORS_SECTION_EXACT_TITLE_LIST.contains(pageSectionTitle)
				|| containsAnyFromList(pageSectionTitle, DIRECTORS_SECTION_MATHCES_TITLE_LIST);
	}

	private boolean isStaffSection(String pageSectionTitle) {
		return STAFF_SECTION_EXACT_TITLE_LIST.contains(pageSectionTitle)
				|| containsAnyFromList(pageSectionTitle, STAFF_SECTION_MATCHES_TITLE_LIST);
	}

	private boolean isPerformersSection(String pageSectionTitle) {
		return PERFORMERS_SECTION_EXACT_TITLE_LIST.contains(pageSectionTitle);
	}

	private boolean isStuntPerformersSection(String pageSectionTitle) {
		return STUNT_PERFORMERS_SECTION_EXACT_TITLE_LIST.contains(pageSectionTitle)
				|| containsAnyFromList(pageSectionTitle, STUNT_PERFORMERS_SECTION_MATCHES_TITLE_LIST);
	}

	private boolean isStandInPerformersSection(String pageSectionTitle) {
		return STAND_IN_PERFORMERS_SECTION_EXACT_TITLE_LIST.contains(pageSectionTitle)
				|| containsAnyFromList(pageSectionTitle, STAND_IN_PERFORMERS_SECTION_MATCHES_TITLE_LIST);
	}

	private static boolean containsAnyFromList(String pageSectionTitle, List<String> matches) {
		return matches.stream()
				.anyMatch(sectionMatch -> pageSectionTitle.toLowerCase().contains(sectionMatch));
	}

}
