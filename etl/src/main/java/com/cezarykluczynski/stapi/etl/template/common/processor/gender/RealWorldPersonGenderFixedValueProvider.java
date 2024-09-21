package com.cezarykluczynski.stapi.etl.template.common.processor.gender;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RealWorldPersonGenderFixedValueProvider implements FixedValueProvider<String, Gender> {

	private static final Map<String, Gender> NAME_TO_GENDER_MAP = Maps.newHashMap();

	static {
		NAME_TO_GENDER_MAP.put("Adam Kane", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Adam John Backauskas", Gender.M); // source: https://www.imdb.com/name/nm0045629/
		NAME_TO_GENDER_MAP.put("Al Rodrigo", Gender.M); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Alan S. Kaye", Gender.M); // genderize.io score: 1.00
		NAME_TO_GENDER_MAP.put("Ahna Packard", Gender.F); // source: https://www.unk.edu/academics/itec/interior_design/ahna-packard.php
		NAME_TO_GENDER_MAP.put("Al Bettcher", Gender.M); // source: https://www.imdb.com/name/nm0079281/
		NAME_TO_GENDER_MAP.put("Alain Rivard", Gender.M); // source: https://www.facebook.com/profile.php?id=100064521349351&sk=photos
		NAME_TO_GENDER_MAP.put("Alan Dingman", Gender.M); // source: https://faso.com/artists/17062.html
		NAME_TO_GENDER_MAP.put("Alisen Down", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Alison Elbl", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Alex R. White", Gender.M); // source: https://en.wikipedia.org/wiki/Alex_White_(author)
		NAME_TO_GENDER_MAP.put("Alix Kell", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Allan Dean Moore", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Amy Sisson", Gender.F); // source: http://amysreviews.blogspot.com/
		NAME_TO_GENDER_MAP.put("Ana Sani", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Anatonia Napoli", Gender.F); // determined from MA page content
		NAME_TO_GENDER_MAP.put("Andy Tsang", Gender.M); // source: https://www.instagram.com/tsangstagangsta/
		NAME_TO_GENDER_MAP.put("Andre Ware", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Andrea Davis", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Andrea Yu", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Andy Simonson", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Ann Cusack", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Ann Goobie", Gender.F); //  Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Anna Seltzer", Gender.F); //  Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Anne Wokanovicz", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Anushka Rani", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("April Webster", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Armando Contreras", Gender.M); // judging from description here: https://memory-alpha.fandom.com/wiki/Armando_Contreras
		NAME_TO_GENDER_MAP.put("Arnold Rudnick", Gender.M); // source: https://www.imdb.com/name/nm0748860/
		NAME_TO_GENDER_MAP.put("Artemis Pebdani", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Ashley", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Ashley Bell", Gender.F); // source: https://www.imdb.com/name/nm1623940/
		NAME_TO_GENDER_MAP.put("Aubrey Bradford", Gender.M); // source: http://marvel.fandom.com/wiki/Aubrey_Bradford
		NAME_TO_GENDER_MAP.put("Auman", null); // two people
		NAME_TO_GENDER_MAP.put("B. Burton", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("B. Reynolds", null); // too little data
		NAME_TO_GENDER_MAP.put("B. Stanley", null); // too little data
		NAME_TO_GENDER_MAP.put("B. Tracy", null); // too little data
		NAME_TO_GENDER_MAP.put("B.C. Cameron", Gender.F); // first female pronoun too far into the page
		NAME_TO_GENDER_MAP.put("Barbara Williams", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Bari Biern", Gender.F); // source: https://www.imdb.com/name/nm0081600/
		NAME_TO_GENDER_MAP.put("Barker", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Baxter Earp", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Beebee", null); // too little data
		NAME_TO_GENDER_MAP.put("Berle", null); // two people
		NAME_TO_GENDER_MAP.put("Ben M. Waller", Gender.M); // source: https://www.imdb.com/name/nm2312117/
		NAME_TO_GENDER_MAP.put("Ben Robinson", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Benita Andre", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Bertila Damas", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Bessie Cheng", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Beth Moberly", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Bidwell", null); // too little data
		NAME_TO_GENDER_MAP.put("Billy Vernon", Gender.M); // source: https://www.imdb.com/name/nm0894642/
		NAME_TO_GENDER_MAP.put("Blues Saraceno", Gender.M); // source: https://en.wikipedia.org/wiki/Blues_Saraceno
		NAME_TO_GENDER_MAP.put("Bob Carlson", Gender.M); // source: https://www.imdb.com/name/nm0137866/
		NAME_TO_GENDER_MAP.put("Bob Trochim", Gender.M); // source: https://www.imdb.com/name/nm1100702/
		NAME_TO_GENDER_MAP.put("Bobbi J.G. Weiss", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Boris Gorelick", Gender.M); // source: https://www.imdb.com/name/nm0330788/
		NAME_TO_GENDER_MAP.put("Brandy (cat)", null); // too little data
		NAME_TO_GENDER_MAP.put("Breezy", Gender.F); // dog
		NAME_TO_GENDER_MAP.put("Brie Ford", Gender.F); // source: https://www.imdb.com/name/nm1402106/
		NAME_TO_GENDER_MAP.put("Buffee Friedlich", Gender.F); // determined from MA page content
		NAME_TO_GENDER_MAP.put("Bumper Robinson", Gender.M); // equal number of pronouns, photo used to determine
		NAME_TO_GENDER_MAP.put("Cari Lamb", Gender.M); // source: https://www.imdb.com/name/nm1658994/
		NAME_TO_GENDER_MAP.put("Carol Kane", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Casey Feldt", Gender.M); // Memory Alpha calls him a man
		NAME_TO_GENDER_MAP.put("Casey Onaitis", Gender.M); // source: https://www.ancestry.com/genealogy/records/results?firstName=stanley&lastName=onaitis
		NAME_TO_GENDER_MAP.put("C. Blanc", null); // too little data
		NAME_TO_GENDER_MAP.put("C. Taylor", null); // too little data
		NAME_TO_GENDER_MAP.put("C.B.", Gender.M); // source: https://www.imdb.com/name/nm1093840/
		NAME_TO_GENDER_MAP.put("C.H.", null); // too little data
		NAME_TO_GENDER_MAP.put("Campbell (costumer)", null); // too little data
		NAME_TO_GENDER_MAP.put("Casey Childs", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Charlie Curtis", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Chelsea Harris", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Chris Abbott", Gender.F); // source: https://en.wikipedia.org/wiki/Chris_Abbott
		NAME_TO_GENDER_MAP.put("Chris Nelson Norris", Gender.M); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Chris Tezber", Gender.M); // genderize.io gives probability 1.0 for birth name Christopher
		NAME_TO_GENDER_MAP.put("Chris Weeks", Gender.M); // source: https://www.imdb.com/name/nm1093840/
		NAME_TO_GENDER_MAP.put("Chris Westlake", Gender.M); // photo used to determine
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
		NAME_TO_GENDER_MAP.put("D.M. Phoenix", null); // too little data
		NAME_TO_GENDER_MAP.put("Dale Grahn", Gender.M); // source: https://www.imdb.com/name/nm0334391/
		NAME_TO_GENDER_MAP.put("Dale Hale", Gender.M); // source: https://www.imdb.com/name/nm0354880/
		NAME_TO_GENDER_MAP.put("Dan Abnett", Gender.M); // source: https://en.wikipedia.org/wiki/Dan_Abnett
		NAME_TO_GENDER_MAP.put("Dan Berkowitz", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Dan Young", Gender.F); // source: https://www.imdb.com/name/nm1649472/
		NAME_TO_GENDER_MAP.put("Dan Jurgens", Gender.M); // source: https://en.wikipedia.org/wiki/Dan_Jurgens
		NAME_TO_GENDER_MAP.put("Dana Kramer-Rolls", Gender.F); // source: https://www.isfdb.org/cgi-bin/ea.cgi?5456
		NAME_TO_GENDER_MAP.put("Dana Levenson", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Daniel McCarthy", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Daniel R. Purinton", Gender.M); // Memory Alpha calls him a man
		NAME_TO_GENDER_MAP.put("Daniel Woodrow", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Daniels (stand-in)", null); // too little data
		NAME_TO_GENDER_MAP.put("Darrell Anderson", Gender.M); // Memory Alpha calls him a man
		NAME_TO_GENDER_MAP.put("Daryl", null); // too little data
		NAME_TO_GENDER_MAP.put("Daryl Towles", Gender.M); // determined from MA page content
		NAME_TO_GENDER_MAP.put("Davy Nethercutt", Gender.M); // source: https://vfxkit.com/vfx-supervisor-davy-nethercutt-backpack-kit/
		NAME_TO_GENDER_MAP.put("David Booth", Gender.M); // source: https://www.imdb.com/name/nm0095673/
		NAME_TO_GENDER_MAP.put("David Rossi", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Dawn Gilliam", Gender.F); // source: https://www.imdb.com/name/nm0319101/
		NAME_TO_GENDER_MAP.put("Dean Parks", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Dendrie Taylor", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Dene Nee", null); // genderize.io is not sure, not enough data on the web
		NAME_TO_GENDER_MAP.put("Denny Allan", Gender.M); // genderize.io result for alias Dan Allan
		NAME_TO_GENDER_MAP.put("Devon Raymond", Gender.F); // source: https://www.imdb.com/name/nm0713239/
		NAME_TO_GENDER_MAP.put("Diane Salinger", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Dominie Lee", null); // too little data
		NAME_TO_GENDER_MAP.put("Don Coleman", Gender.M); // source: https://www.imdb.com/name/nm0002623/
		NAME_TO_GENDER_MAP.put("Don Ivan Punchatz", Gender.M); // source: https://en.wikipedia.org/wiki/Don_Ivan_Punchatz
		NAME_TO_GENDER_MAP.put("Don Peters", Gender.M); // source: https://www.imdb.com/name/nm2051862/
		NAME_TO_GENDER_MAP.put("Dorren Lee", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Doug Jung", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Doug Stone", Gender.M); // source: https://www.imdb.com/name/nm0422849/
		NAME_TO_GENDER_MAP.put("Duffie McIntire", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Ed Charnock", Gender.M); // Memory Alpha calls him a man
		NAME_TO_GENDER_MAP.put("Elden E. Ruberg", Gender.M); // source: https://www.imdb.com/name/nm0747952/
		NAME_TO_GENDER_MAP.put("Eli Golub", Gender.M); // genderize.io is not sure, IMDB used to determine
		NAME_TO_GENDER_MAP.put("Eileen Salamas", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Elana Dunkelman", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Ellen J. Hornstein", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Ellis (assistant director)", null); // too little data
		NAME_TO_GENDER_MAP.put("Emmy-Lou", Gender.F); // wild boar
		NAME_TO_GENDER_MAP.put("Evangelatos", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Folkert Schmidt", Gender.M); // equal number of pronouns, photo used to determine
		NAME_TO_GENDER_MAP.put("Fred Grable", Gender.M); // source: https://www.imdb.com/name/nm0300090/
		NAME_TO_GENDER_MAP.put("Fulghan", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("G. Usher", null); // too little data
		NAME_TO_GENDER_MAP.put("G. Young", null); // too little data
		NAME_TO_GENDER_MAP.put("Garverick", null); // too little data
		NAME_TO_GENDER_MAP.put("George (craft service)", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("George Ball", Gender.M); // source: https://www.imdb.com/name/nm0050372/
		NAME_TO_GENDER_MAP.put("George Barr", Gender.M); // Memory Alpha calls him a man
		NAME_TO_GENDER_MAP.put("George Caltsoudas", Gender.M); // source: https://georgecaltsoudas.com/about
		NAME_TO_GENDER_MAP.put("George Cvjetnicanin", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("George F. Slavin", Gender.M); // source: https://www.imdb.com/name/nm0805577/
		NAME_TO_GENDER_MAP.put("George Waiss", Gender.M); // source: https://www.imdb.com/name/nm0906569/
		NAME_TO_GENDER_MAP.put("Gold (camera operator)", null); // too little data
		NAME_TO_GENDER_MAP.put("Gonzalez", null); // too little data
		NAME_TO_GENDER_MAP.put("Grace Norris", Gender.F); // context used to determine: twin sister Riley
		NAME_TO_GENDER_MAP.put("Green (hair stylist)", null); // too little data
		NAME_TO_GENDER_MAP.put("Greg Eagles", Gender.M); // source: https://www.imdb.com/name/nm0126023/
		NAME_TO_GENDER_MAP.put("Gwen L. Sutton", Gender.F); // genderize.io score: 0.95
		NAME_TO_GENDER_MAP.put("Gwen Van Dam", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Haeffmeier", null); // too little data
		NAME_TO_GENDER_MAP.put("Hagen", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Hall (actor)", null); // too little data
		NAME_TO_GENDER_MAP.put("Handy", null); // too little data
		NAME_TO_GENDER_MAP.put("Hank Smith", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Heide Margolis", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Heide Pendergast", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Helga Wool-Smith", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Hermine Kosta", Gender.F); // genderize.io score: 0.98
		NAME_TO_GENDER_MAP.put("Hilary J. Bader", Gender.F); // genderize.io is not sure, IMDB used to determine
		NAME_TO_GENDER_MAP.put("Hilary Shepard-Turner", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Holly Hunter", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Howard Block", Gender.M); // Memory Alpha calls him a man
		NAME_TO_GENDER_MAP.put("Huey Duval", null); // too little data
		NAME_TO_GENDER_MAP.put("Houy", null); // too little data
		NAME_TO_GENDER_MAP.put("Ilbra Yacoob", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Isabel Krebs", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("J. Weir", null); // too little data
		NAME_TO_GENDER_MAP.put("Jack Hunsaker", Gender.M); // source: https://www.imdb.com/name/nm0402299/
		NAME_TO_GENDER_MAP.put("Jack Miller", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Jacqueline Schultz", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Jacquelynn King", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("James M. Ward", Gender.M); // source: https://www.imdb.com/name/nm0911589/
		NAME_TO_GENDER_MAP.put("Jamie Berger", Gender.M); // source: https://www.imdb.com/name/nm1503613/
		NAME_TO_GENDER_MAP.put("Jane Wuu", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Jenifer A. Lee", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Jan Jones", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Jay Baker", Gender.M); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Jean Austin", null); // too little data
		NAME_TO_GENDER_MAP.put("Jean Pierre Durand", Gender.M); // obvious
		NAME_TO_GENDER_MAP.put("Jeff Baker", Gender.M); // source: https://www.imdb.com/name/nm1198561/
		NAME_TO_GENDER_MAP.put("Jeffrey Byron", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Jen Kelly", null); // too little data
		NAME_TO_GENDER_MAP.put("Jen McGowan", Gender.F); // source: https://www.jenmcgowan.com/about
		NAME_TO_GENDER_MAP.put("Jen Patton", Gender.F); // source: https://twitter.com/jenpattonla
		NAME_TO_GENDER_MAP.put("Jennifer W. Evans", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Jennifer Williams", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Jerry Sroka", Gender.M); // Zarabeth (wife's role) is picked by parsed because it has individual template
		NAME_TO_GENDER_MAP.put("Jerry Vanderstelt", Gender.M); // source: https://vandersteltstudio.com/about/
		NAME_TO_GENDER_MAP.put("Jess Cosio", null); // too little data
		NAME_TO_GENDER_MAP.put("Jessica Hendra", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Jillian Johnston", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Jim Thompson", null); // too little data
		NAME_TO_GENDER_MAP.put("Jo Perry", Gender.F); // source: https://www.murderbooks.com/event/thomas-perry
		NAME_TO_GENDER_MAP.put("Jodi Haynes", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Joe Menendez", Gender.M); // source: https://joemenendez.com/
		NAME_TO_GENDER_MAP.put("Joe Nunez", Gender.M); // source: https://www.imdb.com/name/nm1757362/
		NAME_TO_GENDER_MAP.put("John Fifer", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("John Kretchmer", Gender.M); // Memory Alpha calls him a man
		NAME_TO_GENDER_MAP.put("John W. Hanley", Gender.M); // Memory Alpha calls him a man
		NAME_TO_GENDER_MAP.put("Jordan Hoffman", Gender.M); // source: https://jordanhoffman.com/about-me/
		NAME_TO_GENDER_MAP.put("Jordu Schell", Gender.M); // source: http://memory-alpha.fandom.com/wiki/Jordu_Schell
		NAME_TO_GENDER_MAP.put("Joseph C. Sasgen", Gender.M); // Memory Alpha calls him a man
		NAME_TO_GENDER_MAP.put("Joseph G. Sorokin", Gender.M); // source: https://www.imdb.com/name/nm0815121/
		NAME_TO_GENDER_MAP.put("Joseph Pilato", Gender.M); // source: https://www.imdb.com/name/nm0683334/
		NAME_TO_GENDER_MAP.put("Joseph Markham", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Joseph Richard Collins", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Joseph White", Gender.M); // source: https://www.imdb.com/name/nm0924892/
		NAME_TO_GENDER_MAP.put("Joshua Henson", Gender.M); // Memory Alpha calls him a man
		NAME_TO_GENDER_MAP.put("Joy Dever", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Julia Cassidy", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("K. Willey", null); // too little data
		NAME_TO_GENDER_MAP.put("Kacey Arnold-Ince", null); // too little data
		NAME_TO_GENDER_MAP.put("Kaem Wong", null); // too little data
		NAME_TO_GENDER_MAP.put("Kamala Lopez-Dawson", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Karen", null); // too little data
		NAME_TO_GENDER_MAP.put("Karen Garutso", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Karen Rose Cercone", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Karen Shaffner", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Katelin Petersen", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Kathleen J. Grant", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Kay Wright", null); // too little data
		NAME_TO_GENDER_MAP.put("Kelli Kirkland", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Kelly Cooper", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Kem Antilles", null); // pseudonym for two people
		NAME_TO_GENDER_MAP.put("Ken Adam", Gender.M); // source: https://en.wikipedia.org/wiki/Ken_Adam
		NAME_TO_GENDER_MAP.put("Ken Dufva", Gender.M); // source: https://cinemontage.org/in-memoriam-kenneth-r-dufva-foley-artist/
		NAME_TO_GENDER_MAP.put("Kent Allen Jones", Gender.M); // source: https://www.youtube.com/watch?v=m3pr9kxv-FQ
		NAME_TO_GENDER_MAP.put("Kenney", Gender.M); // genderize.io score: 0.98
		NAME_TO_GENDER_MAP.put("Kentucky Rhodes", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Kerry A. Vill", null); // too little data
		NAME_TO_GENDER_MAP.put("Kim Friedman", Gender.F); // source: https://en.wikipedia.org/wiki/Kim_Friedman
		NAME_TO_GENDER_MAP.put("Kinsel", null); // too little data
		NAME_TO_GENDER_MAP.put("Kip Welch", Gender.M); // source: https://www.imdb.com/name/nm0919596/
		NAME_TO_GENDER_MAP.put("Kourtney Richard", Gender.F); // source: https://www.imdb.com/name/nm6663711/
		NAME_TO_GENDER_MAP.put("Kris Zimmerman", Gender.F); // source: https://www.imdb.com/name/nm0956743/
		NAME_TO_GENDER_MAP.put("Krista Jang", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Kristine Fong", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Kurt Bonzell", Gender.M); // source: https://www.imdb.com/name/nm2192570/
		NAME_TO_GENDER_MAP.put("Leigh Allyn Baker", Gender.F); // source: https://www.imdb.com/name/nm0048687/
		NAME_TO_GENDER_MAP.put("L.A. Graf", null); // pseudonym for multiple people
		NAME_TO_GENDER_MAP.put("L.J. Strom", null); // too little data
		NAME_TO_GENDER_MAP.put("Laura Villari", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("La Verne Harding", Gender.F); // source: https://www.imdb.com/name/nm0362354/
		NAME_TO_GENDER_MAP.put("Laura Lee", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Les Menchen", null); // too little data
		NAME_TO_GENDER_MAP.put("Laurie Resnick", Gender.F); // source: https://www.linkedin.com/in/laurie-resnick-373385a/
		NAME_TO_GENDER_MAP.put("Laurie Ulster", Gender.F); // source: https://www.laurieulster.com/about
		NAME_TO_GENDER_MAP.put("Lee Halpern", Gender.M); // source: https://www.imdb.com/name/nm0356951/
		NAME_TO_GENDER_MAP.put("Len Janson", Gender.M); // source: https://en.wikipedia.org/wiki/Len_Janson
		NAME_TO_GENDER_MAP.put("Len Rogers", null); // too little data
		NAME_TO_GENDER_MAP.put("Lenny Kravitz", Gender.M); // common knowledge
		NAME_TO_GENDER_MAP.put("Leon Harris", Gender.M); // source: https://www.imdb.com/name/nm0364994/
		NAME_TO_GENDER_MAP.put("Leslie Shatner", Gender.F); // processors gave ambiguous results, photo used to determine
		NAME_TO_GENDER_MAP.put("Linda Nile", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Lindsey Haun", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Lis Bothwell", Gender.F); // source: https://www.linkedin.com/in/lisbothwell/
		NAME_TO_GENDER_MAP.put("Lisa Michelle Cornelius", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("LJ Scott", null); // too little data
		NAME_TO_GENDER_MAP.put("Little C", null); // too little data
		NAME_TO_GENDER_MAP.put("Loretta Shenosky", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Lori D. Harris", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Lou", null); // too little data
		NAME_TO_GENDER_MAP.put("Lou Kachivas", null); // too little data
		NAME_TO_GENDER_MAP.put("Louis P. DeSantis", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Louise Schulze", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Louise Sandoval", Gender.F); // source: https://www.linkedin.com/in/e-m-louise-sandoval-b5682650/
		NAME_TO_GENDER_MAP.put("Lynn Barker", Gender.F); // genderize.io is not sure, IMDB used to determine
		NAME_TO_GENDER_MAP.put("Lynn Ledgerwood", null); // too little data
		NAME_TO_GENDER_MAP.put("Lynnda Ferguson", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("M. Johnson", null); // too little data
		NAME_TO_GENDER_MAP.put("Margaret Prentice", Gender.M); // source: https://www.imdb.com/name/nm0078642/
		NAME_TO_GENDER_MAP.put("Makiko Konishi", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Marina Abramyan", Gender.F); // source: https://www.imdb.com/name/nm2920285/
		NAME_TO_GENDER_MAP.put("Marion Turk", Gender.F); // genderize.io score: 0.97
		NAME_TO_GENDER_MAP.put("Mark Cromer", Gender.M); // source: https://www.imdb.com/name/nm1790191/
		NAME_TO_GENDER_MAP.put("Marsh Lamore", Gender.F); // also known as Marshall La More, genderize.io score for Marshall: 0.98
		NAME_TO_GENDER_MAP.put("Martin A. Winer", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Mary Ann", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Mary Ann Barton", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Mary P. Taylor", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Marty (stand-in)", null); // too little data
		NAME_TO_GENDER_MAP.put("Marvellen", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Mathew Hanlen", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Matilda Recindes", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Maurice Harvey", Gender.M); // source: https://www.imdb.com/name/nm0367632/
		NAME_TO_GENDER_MAP.put("Maurishka", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("McGhee", null); // too little data
		NAME_TO_GENDER_MAP.put("Melissa Gieringer", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Melissa Roxburgh", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Mendez (grip)", null); // too little data
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
		NAME_TO_GENDER_MAP.put("Molly Rennie", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Mona Clee", Gender.F); // genderize.io score: 0.97
		NAME_TO_GENDER_MAP.put("Monte Thrasher", Gender.M); // source: https://www.linkedin.com/in/monte-thrasher-8500b145
		NAME_TO_GENDER_MAP.put("Murata", null); // too little data
		NAME_TO_GENDER_MAP.put("Murray Golden", Gender.M); // source: https://www.imdb.com/name/nm0325476/
		NAME_TO_GENDER_MAP.put("Myles", null); // too little data
		NAME_TO_GENDER_MAP.put("Nancy P. Townsend", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Naomi Kyle", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Natalie Moon", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Neil S. Bulk", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Nick Infield", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Nicole Sarah Fellows", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Nina Kent", Gender.F); // genderize.io score: 0.98
		NAME_TO_GENDER_MAP.put("Osni Omena", Gender.M); // source: https://www.linkedin.com/in/osniomena/
		NAME_TO_GENDER_MAP.put("Pam Wigginton", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Pat Keppler", null); // too little data
		NAME_TO_GENDER_MAP.put("Paul Lynch", Gender.M); // determined from MA page content
		NAME_TO_GENDER_MAP.put("Perri Sorel", Gender.F); // source: https://www.instagram.com/perrithegirl/
		NAME_TO_GENDER_MAP.put("Perry Brown", Gender.M); // source: https://www.imdb.com/name/nm2170670/
		NAME_TO_GENDER_MAP.put("Phil Bishop", Gender.M); // Memory Alpha calls him a man
		NAME_TO_GENDER_MAP.put("Phil Farrand", Gender.M); // source: https://en.wikipedia.org/wiki/Phil_Farrand
		NAME_TO_GENDER_MAP.put("Phil Hetos", Gender.F); // source: https://www.imdb.com/name/nm0381756/
		NAME_TO_GENDER_MAP.put("Phil Stirling", Gender.F); // also known as Phillip Sterling, genderize.io score for Phillip: 1.00
		NAME_TO_GENDER_MAP.put("R. Dixon", null); // too little data
		NAME_TO_GENDER_MAP.put("R. Green", null); // too little data
		NAME_TO_GENDER_MAP.put("R.C. Johnston", null); // too little data
		NAME_TO_GENDER_MAP.put("R.D. Knox", Gender.M); // Memory Alpha calls him a man
		NAME_TO_GENDER_MAP.put("Ramsey Avery", Gender.M); // Memory Alpha calls him a man
		NAME_TO_GENDER_MAP.put("Randy K. Singer", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Ray Shaffer", Gender.M); // source: https://myspace.com/rayshaffer
		NAME_TO_GENDER_MAP.put("Reed (performer)", null); // too little data
		NAME_TO_GENDER_MAP.put("Riley Norris", Gender.F); // context used to determine: twin sister Grace
		NAME_TO_GENDER_MAP.put("Rivera", null); // too little data
		NAME_TO_GENDER_MAP.put("R.J. Hohman", null); // too little data
		NAME_TO_GENDER_MAP.put("Robbie Robinson", null); // too little data
		NAME_TO_GENDER_MAP.put("Rock Benedetto", Gender.M); // genderize.io score: 0.95
		NAME_TO_GENDER_MAP.put("Robert Barron", Gender.M); // source: https://www.imdb.com/name/nm0057642/
		NAME_TO_GENDER_MAP.put("Rod Stoick", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Ron Turner", Gender.M); // source: https://en.wikipedia.org/wiki/Ron_Turner_(illustrator)
		NAME_TO_GENDER_MAP.put("Ron Westland", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Ron Wilkerson", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Roy Blumenfeld", Gender.M); // source: https://www.imdb.com/name/nm0089792/
		NAME_TO_GENDER_MAP.put("Robin Bonaccorsi", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Rosie Malek-Yonan", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Rudy Cataldi", Gender.M); // source: https://www.imdb.com/name/nm0145865/
		NAME_TO_GENDER_MAP.put("Runa Ewok", Gender.F); // a female dog
		NAME_TO_GENDER_MAP.put("Rylee Alazraqui", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("S. Kay", null); // too little data
		NAME_TO_GENDER_MAP.put("Sadie Munroe", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Samantha Repp", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Sambo", null); // too little data
		NAME_TO_GENDER_MAP.put("Sandy Schofield", null); // pen name for two people
		NAME_TO_GENDER_MAP.put("Sandford Galden-Stone", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Sandra Collier", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Sandra Sena", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Sara Nabor", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Schiavone", null); // too little data
		NAME_TO_GENDER_MAP.put("Schultz", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Serc Soc", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Sharman DiVono", Gender.F); // source: https://www.imdb.com/name/nm1406396/
		NAME_TO_GENDER_MAP.put("Sherman Labby", Gender.M); // source: https://en.wikipedia.org/wiki/Sherman_Labby
		NAME_TO_GENDER_MAP.put("Sharyl Fickas", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Skye Dent", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Silver (actor)", null); // too little data
		NAME_TO_GENDER_MAP.put("Simon Halpern", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Simon Hugo", Gender.M); // photo used to determine
		NAME_TO_GENDER_MAP.put("Somchith Vongprachanh", null); // too little data
		NAME_TO_GENDER_MAP.put("Sonja Ruta", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Soufle", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Spencer (cat)", null); // too little data
		NAME_TO_GENDER_MAP.put("Spencer Pinckney", Gender.M); // genderize.io score: 0.96
		NAME_TO_GENDER_MAP.put("Stedman", null); // too little data
		NAME_TO_GENDER_MAP.put("Stanley Goldstein", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Steve Bulen", Gender.M); // source: https://www.imdb.com/name/nm0119876/
		NAME_TO_GENDER_MAP.put("Steward (hair stylist)", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Sumalee Montano", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Sunny Ozell", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Suzy Berger", Gender.F); // source: https://www.imdb.com/name/nm0074379/
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
		NAME_TO_GENDER_MAP.put("Tina Kline", Gender.F); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Tom Hallman", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Tom Wetterman", Gender.M); // genderize.io score: 0.99
		NAME_TO_GENDER_MAP.put("Tony Leader", Gender.M); // source: https://www.imdb.com/name/nm0494903/
		NAME_TO_GENDER_MAP.put("Two Steps From Hell", null); // group of two composers
		NAME_TO_GENDER_MAP.put("Velma Wayland", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Valerie Weiss", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Vanessa Gonzalez Real", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("W. Reed Moran", Gender.M); // source: https://web.csulb.edu/depts/film/faculty_moran_w.html
		NAME_TO_GENDER_MAP.put("W.R. Thompson", null); // too little data
		NAME_TO_GENDER_MAP.put("Wanda M. Haight", Gender.F); // genderize.io score: 0.98
		NAME_TO_GENDER_MAP.put("Watts (child actor)", null); // too little data
		NAME_TO_GENDER_MAP.put("Wendy Engalla", Gender.F); // Memory Alpha calls her a woman
		NAME_TO_GENDER_MAP.put("Wendy Schaal", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Wes Herschensohn", Gender.M); // source: https://www.imdb.com/name/nm0380861/
		NAME_TO_GENDER_MAP.put("Wil", null); // too little data
		NAME_TO_GENDER_MAP.put("Williams (dga trainee)", Gender.M); // genderize.io score: 0.96
		NAME_TO_GENDER_MAP.put("Willie", null); // too little data
		NAME_TO_GENDER_MAP.put("Xuelian Lei", Gender.F); // photo used to determine
		NAME_TO_GENDER_MAP.put("Zimmerman", null); // too little data
		NAME_TO_GENDER_MAP.put("Zion Davush", null); // too little data
		NAME_TO_GENDER_MAP.put("Zoe", null); // too little data
		NAME_TO_GENDER_MAP.put("Zoe Chernov", Gender.F); // photo used to determine
	}

	@Override
	public FixedValueHolder<Gender> getSearchedValue(String key) {
		return FixedValueHolder.of(NAME_TO_GENDER_MAP.containsKey(key), NAME_TO_GENDER_MAP.get(key));
	}

}
