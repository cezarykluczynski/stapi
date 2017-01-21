package com.cezarykluczynski.stapi.etl.common.mapper

import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender as EtlGender
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender as ModelGender
import spock.lang.Specification

class GenderMapperTest extends Specification {

	private GenderMapper genderMapper

	void setup() {
		genderMapper = new GenderMapper()
	}

	void "maps ETL gender to model gender"() {
		expect:
		genderMapper.fromEtlToModel(null) == null
		genderMapper.fromEtlToModel(EtlGender.F) == ModelGender.F
		genderMapper.fromEtlToModel(EtlGender.M) == ModelGender.M
	}

}
