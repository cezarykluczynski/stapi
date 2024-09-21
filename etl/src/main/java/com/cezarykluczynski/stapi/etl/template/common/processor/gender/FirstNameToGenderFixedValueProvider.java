package com.cezarykluczynski.stapi.etl.template.common.processor.gender;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

// only genderize.io results with score 1.00 should go here
@Service
public class FirstNameToGenderFixedValueProvider implements FixedValueProvider<String, Gender> {

	private static final Map<String, Gender> NAME_TO_GENDER_MAP = Maps.newHashMap();

	static {
		NAME_TO_GENDER_MAP.put("Alan", Gender.M);
		NAME_TO_GENDER_MAP.put("Alberto", Gender.M);
		NAME_TO_GENDER_MAP.put("Alexander", Gender.M);
		NAME_TO_GENDER_MAP.put("Allan", Gender.M);
		NAME_TO_GENDER_MAP.put("Arthur", Gender.M);
		NAME_TO_GENDER_MAP.put("Barbara", Gender.F);
		NAME_TO_GENDER_MAP.put("Bill", Gender.M);
		NAME_TO_GENDER_MAP.put("Brad", Gender.M);
		NAME_TO_GENDER_MAP.put("Brenda", Gender.F);
		NAME_TO_GENDER_MAP.put("Brian", Gender.M);
		NAME_TO_GENDER_MAP.put("Briana", Gender.F);
		NAME_TO_GENDER_MAP.put("Brooke", Gender.F);
		NAME_TO_GENDER_MAP.put("Bryan", Gender.M);
		NAME_TO_GENDER_MAP.put("Carolyn", Gender.F);
		NAME_TO_GENDER_MAP.put("Catherine", Gender.F);
		NAME_TO_GENDER_MAP.put("Charles", Gender.M);
		NAME_TO_GENDER_MAP.put("Cindy", Gender.F);
		NAME_TO_GENDER_MAP.put("Colin", Gender.M);
		NAME_TO_GENDER_MAP.put("Craig", Gender.M);
		NAME_TO_GENDER_MAP.put("Curt", Gender.M);
		NAME_TO_GENDER_MAP.put("Darryl", Gender.M);
		NAME_TO_GENDER_MAP.put("Dave", Gender.M);
		NAME_TO_GENDER_MAP.put("David", Gender.M);
		NAME_TO_GENDER_MAP.put("Dawn", Gender.F);
		NAME_TO_GENDER_MAP.put("Deborah", Gender.F);
		NAME_TO_GENDER_MAP.put("Derek", Gender.M);
		NAME_TO_GENDER_MAP.put("Diane", Gender.F);
		NAME_TO_GENDER_MAP.put("Donald", Gender.M);
		NAME_TO_GENDER_MAP.put("Doug", Gender.M);
		NAME_TO_GENDER_MAP.put("Douglas", Gender.M);
		NAME_TO_GENDER_MAP.put("Ed", Gender.M);
		NAME_TO_GENDER_MAP.put("Elaine", Gender.F);
		NAME_TO_GENDER_MAP.put("Eric", Gender.M);
		NAME_TO_GENDER_MAP.put("Fernando", Gender.M);
		NAME_TO_GENDER_MAP.put("Frank", Gender.M);
		NAME_TO_GENDER_MAP.put("Fred", Gender.M);
		NAME_TO_GENDER_MAP.put("Frederik", Gender.M);
		NAME_TO_GENDER_MAP.put("Gerald", Gender.M);
		NAME_TO_GENDER_MAP.put("Gregory", Gender.M);
		NAME_TO_GENDER_MAP.put("Harold", Gender.M);
		NAME_TO_GENDER_MAP.put("Heather", Gender.F);
		NAME_TO_GENDER_MAP.put("Ilaria", Gender.F);
		NAME_TO_GENDER_MAP.put("Jacob", Gender.M);
		NAME_TO_GENDER_MAP.put("James", Gender.M);
		NAME_TO_GENDER_MAP.put("Janet", Gender.F);
		NAME_TO_GENDER_MAP.put("Jason", Gender.M);
		NAME_TO_GENDER_MAP.put("Jeff", Gender.M);
		NAME_TO_GENDER_MAP.put("Jeffrey", Gender.M);
		NAME_TO_GENDER_MAP.put("Jennifer", Gender.F);
		NAME_TO_GENDER_MAP.put("Jeremy", Gender.M);
		NAME_TO_GENDER_MAP.put("Jessica", Gender.M);
		NAME_TO_GENDER_MAP.put("Jim", Gender.M);
		NAME_TO_GENDER_MAP.put("John", Gender.M);
		NAME_TO_GENDER_MAP.put("Kathy", Gender.F);
		NAME_TO_GENDER_MAP.put("Keith", Gender.M);
		NAME_TO_GENDER_MAP.put("Kenneth", Gender.M);
		NAME_TO_GENDER_MAP.put("Kevin", Gender.M);
		NAME_TO_GENDER_MAP.put("Kurt", Gender.M);
		NAME_TO_GENDER_MAP.put("Larry", Gender.M);
		NAME_TO_GENDER_MAP.put("Lisa", Gender.F);
		NAME_TO_GENDER_MAP.put("Margaret", Gender.F);
		NAME_TO_GENDER_MAP.put("Mark", Gender.M);
		NAME_TO_GENDER_MAP.put("Melissa", Gender.F);
		NAME_TO_GENDER_MAP.put("Michael", Gender.M);
		NAME_TO_GENDER_MAP.put("Michelle", Gender.F);
		NAME_TO_GENDER_MAP.put("Mike", Gender.M);
		NAME_TO_GENDER_MAP.put("Monica", Gender.F);
		NAME_TO_GENDER_MAP.put("Patricia", Gender.F);
		NAME_TO_GENDER_MAP.put("Patrick", Gender.M);
		NAME_TO_GENDER_MAP.put("Paul", Gender.M);
		NAME_TO_GENDER_MAP.put("Peter", Gender.M);
		NAME_TO_GENDER_MAP.put("Philip", Gender.M);
		NAME_TO_GENDER_MAP.put("Richard", Gender.M);
		NAME_TO_GENDER_MAP.put("Rick", Gender.M);
		NAME_TO_GENDER_MAP.put("Rob", Gender.M);
		NAME_TO_GENDER_MAP.put("Robert", Gender.M);
		NAME_TO_GENDER_MAP.put("Rochelle", Gender.F);
		NAME_TO_GENDER_MAP.put("Roland", Gender.M);
		NAME_TO_GENDER_MAP.put("Rolando", Gender.M);
		NAME_TO_GENDER_MAP.put("Scott", Gender.M);
		NAME_TO_GENDER_MAP.put("Sean", Gender.M);
		NAME_TO_GENDER_MAP.put("Stephanie", Gender.F);
		NAME_TO_GENDER_MAP.put("Steve", Gender.M);
		NAME_TO_GENDER_MAP.put("Stuart", Gender.M);
		NAME_TO_GENDER_MAP.put("Sue", Gender.F);
		NAME_TO_GENDER_MAP.put("Susan", Gender.F);
		NAME_TO_GENDER_MAP.put("Terri", Gender.F);
		NAME_TO_GENDER_MAP.put("Thomas", Gender.M);
		NAME_TO_GENDER_MAP.put("Timothy", Gender.M);
		NAME_TO_GENDER_MAP.put("Todd", Gender.M);
		NAME_TO_GENDER_MAP.put("Troy", Gender.M);
		NAME_TO_GENDER_MAP.put("Wesley", Gender.M);
	}

	@Override
	public FixedValueHolder<Gender> getSearchedValue(String key) {
		return FixedValueHolder.of(NAME_TO_GENDER_MAP.containsKey(key), NAME_TO_GENDER_MAP.get(key));
	}

}
