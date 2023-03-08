package com.cezarykluczynski.stapi.server.common.cache

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository
import com.cezarykluczynski.stapi.model.trading_card.dto.TradingCardRequestDTO
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import spock.lang.Specification

class EagerCacheReflectionHelperTest extends Specification {

	static final String UID = 'UID'

	EagerCacheReflectionHelper eagerCacheReflectionHelper

	void setup() {
		eagerCacheReflectionHelper = new EagerCacheReflectionHelper()
	}

	void "creates criteria (page-aware entity)"() {
		given:
		Staff staff = new Staff(uid: UID)

		when:
		Object staffRequestDTO = eagerCacheReflectionHelper.createCriteria(StaffRequestDTO, staff)

		then:
		staffRequestDTO == new StaffRequestDTO(uid: UID)
	}

	void "creates criteria (not page-aware entity)"() {
		given:
		TradingCard tradingCard = new TradingCard(uid: UID)

		when:
		Object tradingCardRequestDTO = eagerCacheReflectionHelper.createCriteria(TradingCardRequestDTO, tradingCard)

		then:
		tradingCardRequestDTO == new TradingCardRequestDTO(uid: UID)
	}

	void "throws exception when criteria cannot be created"() {
		given:
		Staff staff = new Staff(uid: UID)

		when:
		eagerCacheReflectionHelper.createCriteria(Object, staff)

		then:
		thrown(RuntimeException)
	}

	void "gets criteria class from CriteriaMatcher"() {
		given:
		CriteriaMatcher criteriaMatcher = Mock(StaffRepository)

		when:
		Class criteriaClass = eagerCacheReflectionHelper.getCriteriaClass(criteriaMatcher)

		then:
		criteriaClass == StaffRequestDTO
	}

	void "returns null for not found criteria class"() {
		given:
		CriteriaMatcher criteriaMatcher = Mock()

		when:
		Class criteriaClass = eagerCacheReflectionHelper.getCriteriaClass(criteriaMatcher)

		then:
		criteriaClass == null
	}

}
