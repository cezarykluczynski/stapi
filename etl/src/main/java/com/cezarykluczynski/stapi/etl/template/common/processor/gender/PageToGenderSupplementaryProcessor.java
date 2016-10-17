package com.cezarykluczynski.stapi.etl.template.common.processor.gender;

import com.cezarykluczynski.stapi.etl.template.common.dto.Gender;
import com.cezarykluczynski.stapi.wiki.dto.Page;
import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PageToGenderSupplementaryProcessor implements ItemProcessor<Page, PageToGenderSupplementaryProcessor.Finding> {

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
	}

	@Data
	public static class Finding {

		private boolean found;

		private Gender gender;

	}

	@Override
	public Finding process(Page item) throws Exception {
		String title = item.getTitle();
		boolean found = NAME_TO_GENDER_MAP.containsKey(title);
		Finding finding = new Finding();

		finding.setFound(found);
		finding.setGender(found ? NAME_TO_GENDER_MAP.get(title) : null);
		return finding;
	}

}
