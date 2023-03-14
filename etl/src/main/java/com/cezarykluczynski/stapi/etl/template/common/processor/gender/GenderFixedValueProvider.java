package com.cezarykluczynski.stapi.etl.template.common.processor.gender;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GenderFixedValueProvider implements FixedValueProvider<String, Gender> {

	private static final Map<String, Gender> NAME_TO_GENDER_MAP = Maps.newHashMap();

	static {
		NAME_TO_GENDER_MAP.put("Al Rodrigo", Gender.M); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Alan Dingman", Gender.M); // source: https://faso.com/artists/17062.html
		NAME_TO_GENDER_MAP.put("Alisen Down", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Alison Elbl", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Alix Kell", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Allan Dean Moore", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Amy Sisson", Gender.F); // source: http://amysreviews.blogspot.com/
		NAME_TO_GENDER_MAP.put("Ana Sani", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Anatonia Napoli", Gender.F); // determined from MA page content
		NAME_TO_GENDER_MAP.put("Andre Ware", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Andrea Davis", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Andrea Yu", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Andy Simonson", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Ann Cusack", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Anushka Rani", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Artemis Pebdani", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Ashley", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Aubrey Bradford", Gender.M); // source: http://marvel.fandom.com/wiki/Aubrey_Bradford
		NAME_TO_GENDER_MAP.put("Auman", null); // two people
		NAME_TO_GENDER_MAP.put("B. Burton", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("B.C. Cameron", Gender.F); // first female pronoun too far into the page
		NAME_TO_GENDER_MAP.put("Barbara Williams", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Bari Biern", Gender.F); // source: https://www.imdb.com/name/nm0081600/
		NAME_TO_GENDER_MAP.put("Barker", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Baxter Earp", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Berle", null); // two people
		NAME_TO_GENDER_MAP.put("Benita Andre", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Bertila Damas", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Bessie Cheng", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Beth Moberly", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Bidwell", null); // too little data
		NAME_TO_GENDER_MAP.put("Blues Saraceno", Gender.M); // source: https://en.wikipedia.org/wiki/Blues_Saraceno
		NAME_TO_GENDER_MAP.put("Brandy (cat)", null); // too little data
		NAME_TO_GENDER_MAP.put("Breezy", Gender.F); // dog
		NAME_TO_GENDER_MAP.put("Buffee Friedlich", Gender.F); // determined from MA page content
		NAME_TO_GENDER_MAP.put("Bumper Robinson", Gender.M); // equal number of pronouns, photo used to determine
		NAME_TO_GENDER_MAP.put("Cari Lamb", Gender.M); // source: https://www.imdb.com/name/nm1658994/
		NAME_TO_GENDER_MAP.put("C. Taylor", null); // too little data
		NAME_TO_GENDER_MAP.put("C.B.", Gender.M); // source: https://www.imdb.com/name/nm1093840/
		NAME_TO_GENDER_MAP.put("C.H.", null); // too little data
		NAME_TO_GENDER_MAP.put("Casey Childs", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Charlie Curtis", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Chelsea Harris", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Chris Abbott", Gender.F); // source: https://en.wikipedia.org/wiki/Chris_Abbott
		NAME_TO_GENDER_MAP.put("Chris Nelson Norris", Gender.M); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Chris Tezber", Gender.M); // genderize.io gives probability 1.0 for birth name Christopher
		NAME_TO_GENDER_MAP.put("Chris Weeks", Gender.M); // source: https://www.imdb.com/name/nm1093840/
		NAME_TO_GENDER_MAP.put("Christine Jansen", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Chuck Beyer", Gender.M); // source: https://www.imdb.com/name/nm0079899/
		NAME_TO_GENDER_MAP.put("Cindy Rocco", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Claudia Balboni", Gender.F); // wrong guess from pronouns
		NAME_TO_GENDER_MAP.put("Cleo Severy", Gender.F); // source: https://www.linkedin.com/in/cleo-mannell-1a019781/
		NAME_TO_GENDER_MAP.put("Cooke", null); // too little data
		NAME_TO_GENDER_MAP.put("Colette Whitaker", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Constance Towers", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("D. Harrington", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("D. Marghane", null); // too little data
		NAME_TO_GENDER_MAP.put("D. Wayman", null); // too little data
		NAME_TO_GENDER_MAP.put("Dan Young", Gender.F); // source: https://www.imdb.com/name/nm1649472/
		NAME_TO_GENDER_MAP.put("Dana Kramer-Rolls", Gender.F); // source: https://www.isfdb.org/cgi-bin/ea.cgi?5456
		NAME_TO_GENDER_MAP.put("Dana Levenson", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Daniels (stand-in)", null); // too little data
		NAME_TO_GENDER_MAP.put("Daryl", null); // too little data
		NAME_TO_GENDER_MAP.put("Daryl Towles", Gender.M); // determined from MA page content
		NAME_TO_GENDER_MAP.put("David Booth", Gender.M); // source: https://www.imdb.com/name/nm0095673/
		NAME_TO_GENDER_MAP.put("David Rossi", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Dawn Gilliam", Gender.F); // source: https://www.imdb.com/name/nm0319101/
		NAME_TO_GENDER_MAP.put("Dendrie Taylor", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Dene Nee", null); // genderize.io is not sure, not enough data on the web
		NAME_TO_GENDER_MAP.put("Denny Allan", Gender.M); // genderize.io result for alias Dan Allan
		NAME_TO_GENDER_MAP.put("Devon Raymond", Gender.F); // source: https://www.imdb.com/name/nm0713239/
		NAME_TO_GENDER_MAP.put("Diane Salinger", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Dominie Lee", null); // too little data
		NAME_TO_GENDER_MAP.put("Don Coleman", Gender.M); // source: https://www.imdb.com/name/nm0002623/
		NAME_TO_GENDER_MAP.put("Dorren Lee", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Doug Jung", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Doug Stone", Gender.M); // source: https://www.imdb.com/name/nm0422849/
		NAME_TO_GENDER_MAP.put("Duffie McIntire", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Eli Golub", Gender.M); // genderize.io is not sure, IMDB used to determine
		NAME_TO_GENDER_MAP.put("Eileen Salamas", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Elana Dunkelman", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Ellen J. Hornstein", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Ellis (assistant director)", null); // too little data
		NAME_TO_GENDER_MAP.put("Emmy-Lou", Gender.F); // wild boar
		NAME_TO_GENDER_MAP.put("Evangelatos", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Folkert Schmidt", Gender.M); // equal number of pronouns, photo used to determine
		NAME_TO_GENDER_MAP.put("Fulghan", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("G. Usher", null); // too little data
		NAME_TO_GENDER_MAP.put("Garverick", null); // too little data
		NAME_TO_GENDER_MAP.put("George Ball", Gender.M); // source: https://www.imdb.com/name/nm0050372/
		NAME_TO_GENDER_MAP.put("Gonzalez", null); // too little data
		NAME_TO_GENDER_MAP.put("Greg Eagles", Gender.M); // source: https://www.imdb.com/name/nm0126023/
		NAME_TO_GENDER_MAP.put("Haeffmeier", null); // too little data
		NAME_TO_GENDER_MAP.put("Hagen", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Hall (actor)", null); // too little data
		NAME_TO_GENDER_MAP.put("Handy", null); // too little data
		NAME_TO_GENDER_MAP.put("Heide Margolis", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Heide Pendergast", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Hilary J. Bader", Gender.F); // genderize.io is not sure, IMDB used to determine
		NAME_TO_GENDER_MAP.put("Hilary Shepard-Turner", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Houy", null); // too little data
		NAME_TO_GENDER_MAP.put("Ilbra Yacoob", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("J. Weir", null); // too little data
		NAME_TO_GENDER_MAP.put("Jacqueline Schultz", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Jacquelynn King", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("James M. Ward", Gender.M); // source: https://www.imdb.com/name/nm0911589/
		NAME_TO_GENDER_MAP.put("Jamie Berger", Gender.M); // source: https://www.imdb.com/name/nm1503613/
		NAME_TO_GENDER_MAP.put("Jan Jones", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Jordu Schell", Gender.M); // source: http://memory-alpha.fandom.com/wiki/Jordu_Schell
		NAME_TO_GENDER_MAP.put("Jay Baker", Gender.M); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Jean Pierre Durand", Gender.M); // obvious
		NAME_TO_GENDER_MAP.put("Jeff Baker", Gender.M); // source: https://www.imdb.com/name/nm1198561/
		NAME_TO_GENDER_MAP.put("Jeffrey Byron", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Jen Patton", Gender.F); // source: https://twitter.com/jenpattonla
		NAME_TO_GENDER_MAP.put("Jennifer W. Evans", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Jennifer Williams", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Jerry Sroka", Gender.M); // Zarabeth (wife's role) is picked by parsed because it has individual template
		NAME_TO_GENDER_MAP.put("Jessica Hendra", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Jillian Johnston", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Jo Perry", Gender.F); // source: https://www.murderbooks.com/event/thomas-perry
		NAME_TO_GENDER_MAP.put("Jodi Haynes", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("John Fifer", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Jordan Hoffman", Gender.M); // source: https://jordanhoffman.com/about-me/
		NAME_TO_GENDER_MAP.put("Joseph Pilato", Gender.M); // source: https://www.imdb.com/name/nm0683334/
		NAME_TO_GENDER_MAP.put("Joseph White", Gender.M); // source: https://www.imdb.com/name/nm0924892/
		NAME_TO_GENDER_MAP.put("Joshua Henson", Gender.M); // Memory Alpha calls him a man
		NAME_TO_GENDER_MAP.put("Joy Dever", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("K. Willey", null); // too little data
		NAME_TO_GENDER_MAP.put("Kamala Lopez-Dawson", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Katelin Petersen", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Kathleen J. Grant", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Kelli Kirkland", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Kelly Cooper", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Kem Antilles", null); // pseudonym for two people
		NAME_TO_GENDER_MAP.put("Kentucky Rhodes", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Kim Friedman", Gender.F); // source: https://en.wikipedia.org/wiki/Kim_Friedman
		NAME_TO_GENDER_MAP.put("Kinsel", null); // too little data
		NAME_TO_GENDER_MAP.put("Kip Welch", Gender.M); // source: https://www.imdb.com/name/nm0919596/
		NAME_TO_GENDER_MAP.put("Kris Zimmerman", Gender.F); // source: https://www.imdb.com/name/nm0956743/
		NAME_TO_GENDER_MAP.put("Krista Jang", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Kristine Fong", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Leigh Allyn Baker", Gender.F); // source: https://www.imdb.com/name/nm0048687/
		NAME_TO_GENDER_MAP.put("L.A. Graf", null); // pseudonym for multiple people
		NAME_TO_GENDER_MAP.put("Lee Halpern", Gender.M); // source: https://www.imdb.com/name/nm0356951/
		NAME_TO_GENDER_MAP.put("Len Janson", Gender.M); // source: https://en.wikipedia.org/wiki/Len_Janson
		NAME_TO_GENDER_MAP.put("Lenny Kravitz", Gender.M); // common knowledge
		NAME_TO_GENDER_MAP.put("Leslie Shatner", Gender.F); // processors gave ambiguous results, photo used to determine
		NAME_TO_GENDER_MAP.put("Linda Nile", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Lisa Michelle Cornelius", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Loretta Shenosky", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Lou", null); // too little data
		NAME_TO_GENDER_MAP.put("Louise Schulze", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Lynn Barker", Gender.F); // genderize.io is not sure, IMDB used to determine
		NAME_TO_GENDER_MAP.put("Lynn Ledgerwood", null); // too little data
		NAME_TO_GENDER_MAP.put("Lynnda Ferguson", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("M. Johnson", null); // too little data
		NAME_TO_GENDER_MAP.put("Makiko Konishi", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Mark Cromer", Gender.M); // source: https://www.imdb.com/name/nm1790191/
		NAME_TO_GENDER_MAP.put("Marty (stand-in)", null); // too little data
		NAME_TO_GENDER_MAP.put("Marvellen", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Matilda Recindes", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Maurishka", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("McGhee", null); // too little data
		NAME_TO_GENDER_MAP.put("Melissa Gieringer", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Melissa Roxburgh", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Menning", null); // too little data
		NAME_TO_GENDER_MAP.put("Michael E. Strickland", Gender.M); // source: https://www.imdb.com/name/nm1167861/
		NAME_TO_GENDER_MAP.put("Michael Jerome West", Gender.M); // source: https://www.imdb.com/name/nm0871670/
		NAME_TO_GENDER_MAP.put("Michaels (performer)", null); // too little data
		NAME_TO_GENDER_MAP.put("Michele Wolfman", Gender.F); // source: http://marvel.fandom.com/wiki/Michelle_Wolfman
		NAME_TO_GENDER_MAP.put("Michelle Hurd", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Millicent Wise", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Mimi Kuzyk", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Molly Brandenburg", Gender.F); // source: https://www.imdb.com/name/nm1689441/
		NAME_TO_GENDER_MAP.put("Molly Brink", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Monte Thrasher", Gender.M); // source: https://www.linkedin.com/in/monte-thrasher-8500b145
		NAME_TO_GENDER_MAP.put("Murata", null); // too little data
		NAME_TO_GENDER_MAP.put("Murray Golden", Gender.M); // source: https://www.imdb.com/name/nm0325476/
		NAME_TO_GENDER_MAP.put("Myles", null); // too little data
		NAME_TO_GENDER_MAP.put("Natalie Moon", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Neil S. Bulk", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Nicole Sarah Fellows", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Paul Lynch", Gender.M); // determined from MA page content
		NAME_TO_GENDER_MAP.put("Perri Sorel", Gender.F); // source: https://www.instagram.com/perrithegirl/
		NAME_TO_GENDER_MAP.put("Perry Brown", Gender.M); // source: https://www.imdb.com/name/nm2170670/
		NAME_TO_GENDER_MAP.put("Rivera", null); // too little data
		NAME_TO_GENDER_MAP.put("Robert Barron", Gender.M); // source: https://www.imdb.com/name/nm0057642/
		NAME_TO_GENDER_MAP.put("Robin Bonaccorsi", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Rosie Malek-Yonan", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Runa Ewok", Gender.F); // a female dog
		NAME_TO_GENDER_MAP.put("Rylee Alazraqui", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("S. Kay", null); // too little data
		NAME_TO_GENDER_MAP.put("Sadie Munroe", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Sambo", null); // too little data
		NAME_TO_GENDER_MAP.put("Sandy Schofield", null); // pen name for two people
		NAME_TO_GENDER_MAP.put("Sara Nabor", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Schultz", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Serc Soc", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Sharman DiVono", Gender.F); // source: https://www.imdb.com/name/nm1406396/
		NAME_TO_GENDER_MAP.put("Sherman Labby", Gender.M); // source: https://en.wikipedia.org/wiki/Sherman_Labby
		NAME_TO_GENDER_MAP.put("Skye Dent", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Silver (actor)", null); // too little data
		NAME_TO_GENDER_MAP.put("Somchith Vongprachanh", null); // too little data
		NAME_TO_GENDER_MAP.put("Soufle", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Spencer (cat)", null); // too little data
		NAME_TO_GENDER_MAP.put("Stedman", null); // too little data
		NAME_TO_GENDER_MAP.put("Steve Bulen", Gender.M); // source: https://www.imdb.com/name/nm0119876/
		NAME_TO_GENDER_MAP.put("Sumalee Montano", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Sunny Ozell", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Sylte", null); // no way to tell from either MA nor IMDB
		NAME_TO_GENDER_MAP.put("Sylvia Biller", Gender.F); // source: https://www.imdb.com/name/nm0082378/
		NAME_TO_GENDER_MAP.put("T. Blue", null); // too little data
		NAME_TO_GENDER_MAP.put("T.L. Mancour", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Tadeski twins", null); // two people in one page, uncertain
		NAME_TO_GENDER_MAP.put("Tara Nicodemo", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Terrence Stone", Gender.M); // source: https://www.imdb.com/name/nm0731619/
		NAME_TO_GENDER_MAP.put("TG Theodore", Gender.M); // source: http://www.ted.to/
		NAME_TO_GENDER_MAP.put("Thomas E. Benkert", Gender.M); // source: https://www.imdb.com/name/nm0071402/
		NAME_TO_GENDER_MAP.put("Thomas F. Wilson", Gender.M); // source: https://www.imdb.com/name/nm0001855/
		NAME_TO_GENDER_MAP.put("Two Steps From Hell", null); // group of two composers
		NAME_TO_GENDER_MAP.put("W. Reed Moran", Gender.M); // source: https://web.csulb.edu/depts/film/faculty_moran_w.html
		NAME_TO_GENDER_MAP.put("Watts (child actor)", null); // too little data
		NAME_TO_GENDER_MAP.put("Wendy Schaal", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Willie", null); // too little data
		NAME_TO_GENDER_MAP.put("Xuelian Lei", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Zimmerman (costumer)", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Zoe", null); // too little data
		NAME_TO_GENDER_MAP.put("Zoe Chernov", Gender.F); // photo used to determine
	}

	@Override
	public FixedValueHolder<Gender> getSearchedValue(String key) {
		return FixedValueHolder.of(NAME_TO_GENDER_MAP.containsKey(key), NAME_TO_GENDER_MAP.get(key));
	}

}
