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
		NAME_TO_GENDER_MAP.put("Jay Baker", Gender.M); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Bertila Damas", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Baxter Earp", null); // genderize.io is not sure, not enough data on the web to determine, Wikipedia says name can apply to both men and woman
		NAME_TO_GENDER_MAP.put("Breezy", Gender.F); // dog
		NAME_TO_GENDER_MAP.put("Bumper Robinson", Gender.M); // equal number of pronouns, photo used to determine
		NAME_TO_GENDER_MAP.put("Emmy-Lou", Gender.F); // wild boar
		NAME_TO_GENDER_MAP.put("Folkert Schmidt", Gender.M); // equal number of pronouns, photo used to determine
		NAME_TO_GENDER_MAP.put("Jan Jones", null); // maybe an actress January Jones, but uncertain
		NAME_TO_GENDER_MAP.put("Kamala Lopez-Dawson", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Chris Nelson Norris", Gender.M); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Al Rodrigo", Gender.M); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Leslie Shatner", Gender.F); // processors gave ambiguous results, photo used to determine
		NAME_TO_GENDER_MAP.put("Hilary Shepard-Turner", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Jerry Sroka", Gender.M); // Zarabeth (wife's role) is picked by parsed because it has individual template
		NAME_TO_GENDER_MAP.put("Tadeski twins", null); // two people in one page, uncertain
		NAME_TO_GENDER_MAP.put("Dendrie Taylor", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Maurishka", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Sylte", null); // no way to tell from either MA nor IMDB
		NAME_TO_GENDER_MAP.put("Denny Allan", Gender.M); // genderize.io result for alias Dan Allan
		NAME_TO_GENDER_MAP.put("Aubrey Bradford", Gender.M); // source: http://marvel.wikia.com/wiki/Aubrey_Bradford
		NAME_TO_GENDER_MAP.put("B.C. Cameron", Gender.F); // first female pronoun too far into the page
		NAME_TO_GENDER_MAP.put("Michele Wolfman", Gender.F); // source: http://marvel.wikia.com/wiki/Michelle_Wolfman
		NAME_TO_GENDER_MAP.put("Anatonia Napoli", Gender.F); // determined from MA page content
		NAME_TO_GENDER_MAP.put("Sandy Schofield", null); // pen name for two people
		NAME_TO_GENDER_MAP.put("Hilary Bader", Gender.F); // genderize.io is not sure, IMDB used to determine
		NAME_TO_GENDER_MAP.put("Two Steps From Hell", null); // group of two composers
		NAME_TO_GENDER_MAP.put("Claudia Balboni", Gender.F); // wrong guess from pronouns
		NAME_TO_GENDER_MAP.put("Lee Halpern", null); // genderize.io is not sure, not enough data on the web
		NAME_TO_GENDER_MAP.put("Chris Abbott", Gender.F); // source: https://en.wikipedia.org/wiki/Chris_Abbott
		NAME_TO_GENDER_MAP.put("Buffee Friedlich", Gender.F); // determined from MA page content
		NAME_TO_GENDER_MAP.put("Skye Dent", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Kem Antilles", null); // pseudonym for two people
		NAME_TO_GENDER_MAP.put("Monte Thrasher", Gender.M); // source: https://www.linkedin.com/in/monte-thrasher-8500b145
		NAME_TO_GENDER_MAP.put("Dana Kramer-Rolls", null); // genderize.io is not sure, not enough data on the web
		NAME_TO_GENDER_MAP.put("W. Reed Moran", Gender.M); // source: https://web.csulb.edu/depts/film/faculty_moran_w.html
		NAME_TO_GENDER_MAP.put("L.A. Graf", null); // pseudonym for multiple people
		NAME_TO_GENDER_MAP.put("Len Janson", Gender.M); // source: https://en.wikipedia.org/wiki/Len_Janson
		NAME_TO_GENDER_MAP.put("Eli Golub", Gender.M); // genderize.io is not sure, IMDB used to determine
		NAME_TO_GENDER_MAP.put("TG Theodore", Gender.M); // source: http://www.ted.to/
		NAME_TO_GENDER_MAP.put("Paul Lynch", Gender.M); // determined from MA page content
		NAME_TO_GENDER_MAP.put("Dene Nee", null); // genderize.io is not sure, not enough data on the web
		NAME_TO_GENDER_MAP.put("Murray Golden", null); // genderize.io is not sure, not enough data on the web
		NAME_TO_GENDER_MAP.put("Sherman Labby", Gender.M); // source: https://en.wikipedia.org/wiki/Sherman_Labby
		NAME_TO_GENDER_MAP.put("Chris Tezber", null); // genderize.io is not sure, not enough data on the web
		NAME_TO_GENDER_MAP.put("Lynn Barker", Gender.F); // genderize.io is not sure, IMDB used to determine
		NAME_TO_GENDER_MAP.put("Perri Sorel", Gender.F); // source: https://www.instagram.com/perrithegirl/
		NAME_TO_GENDER_MAP.put("Jordu Schell", Gender.M); // source: http://memory-alpha.wikia.com/wiki/Jordu_Schell
		NAME_TO_GENDER_MAP.put("Daryl Towles", Gender.M); // determined from MA page content
		NAME_TO_GENDER_MAP.put("Ilbra Yacoob", Gender.F); // genderize.io is not sure, photo used to determine
		NAME_TO_GENDER_MAP.put("Zimmerman (costumer)", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Hagen", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Schultz", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Soufle", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Fulghan", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Marvellen", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Serc Soc", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("Evangelatos", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("T.L. Mancour", null); // no way of telling from the description
		NAME_TO_GENDER_MAP.put("D. Harrington", null); // no way of telling from the description
	}

	@Override
	public FixedValueHolder<Gender> getSearchedValue(String key) {
		return FixedValueHolder.of(NAME_TO_GENDER_MAP.containsKey(key), NAME_TO_GENDER_MAP.get(key));
	}

}
