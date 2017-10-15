package com.cezarykluczynski.stapi.server.element.mapper

import com.cezarykluczynski.stapi.model.element.entity.Element
import com.cezarykluczynski.stapi.util.AbstractElementTest

abstract class AbstractElementMapperTest extends AbstractElementTest {

	protected static Element createElement() {
		new Element(
				uid: UID,
				name: NAME,
				symbol: SYMBOL,
				atomicNumber: ATOMIC_NUMBER,
				atomicWeight: ATOMIC_WEIGHT,
				transuranium: TRANSURANIUM,
				gammaSeries: GAMMA_SERIES,
				hypersonicSeries: HYPERSONIC_SERIES,
				megaSeries: MEGA_SERIES,
				omegaSeries: OMEGA_SERIES,
				transonicSeries: TRANSONIC_SERIES,
				worldSeries: WORLD_SERIES)
	}

}
