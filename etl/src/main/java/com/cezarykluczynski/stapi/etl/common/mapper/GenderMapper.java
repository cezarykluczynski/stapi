package com.cezarykluczynski.stapi.etl.common.mapper;

import com.cezarykluczynski.stapi.model.common.entity.enums.Gender;
import org.springframework.stereotype.Service;

@Service
public class GenderMapper {

	public Gender fromEtlToModel(com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender gender) {
		if (gender == null) {
			return null;
		}

		switch (gender) {
			case F:
				return Gender.F;
			case M:
				return Gender.M;
			default:
					return null;
		}
	}

}
