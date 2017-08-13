package com.cezarykluczynski.stapi.model.weapon.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.weapon.dto.WeaponRequestDTO
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon_
import com.cezarykluczynski.stapi.model.weapon.query.WeaponQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractWeaponTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class WeaponRepositoryImplTest extends AbstractWeaponTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private WeaponQueryBuilderFactory weaponQueryBuilderFactory

	private WeaponRepositoryImpl weaponRepositoryImpl

	private QueryBuilder<Weapon> weaponQueryBuilder

	private Pageable pageable

	private WeaponRequestDTO weaponRequestDTO

	private Page page

	void setup() {
		weaponQueryBuilderFactory = Mock()
		weaponRepositoryImpl = new WeaponRepositoryImpl(weaponQueryBuilderFactory)
		weaponQueryBuilder = Mock()
		pageable = Mock()
		weaponRequestDTO = Mock()
		page = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = weaponRepositoryImpl.findMatching(weaponRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * weaponQueryBuilderFactory.createQueryBuilder(pageable) >> weaponQueryBuilder

		then: 'uid criteria is set'
		1 * weaponRequestDTO.uid >> UID
		1 * weaponQueryBuilder.equal(Weapon_.uid, UID)

		then: 'string criteria are set'
		1 * weaponRequestDTO.name >> NAME
		1 * weaponQueryBuilder.like(Weapon_.name, NAME)

		then: 'boolean criteria are set'
		1 * weaponRequestDTO.handHeldWeapon >> HAND_HELD_WEAPON
		1 * weaponQueryBuilder.equal(Weapon_.handHeldWeapon, HAND_HELD_WEAPON)
		1 * weaponRequestDTO.laserTechnology >> LASER_TECHNOLOGY
		1 * weaponQueryBuilder.equal(Weapon_.laserTechnology, LASER_TECHNOLOGY)
		1 * weaponRequestDTO.plasmaTechnology >> PLASMA_TECHNOLOGY
		1 * weaponQueryBuilder.equal(Weapon_.plasmaTechnology, PLASMA_TECHNOLOGY)
		1 * weaponRequestDTO.photonicTechnology >> PHOTONIC_TECHNOLOGY
		1 * weaponQueryBuilder.equal(Weapon_.photonicTechnology, PHOTONIC_TECHNOLOGY)
		1 * weaponRequestDTO.phaserTechnology >> PHASER_TECHNOLOGY
		1 * weaponQueryBuilder.equal(Weapon_.phaserTechnology, PHASER_TECHNOLOGY)
		1 * weaponRequestDTO.mirror >> MIRROR
		1 * weaponQueryBuilder.equal(Weapon_.mirror, MIRROR)
		1 * weaponRequestDTO.alternateReality >> ALTERNATE_REALITY
		1 * weaponQueryBuilder.equal(Weapon_.alternateReality, ALTERNATE_REALITY)

		then: 'sort is set'
		1 * weaponRequestDTO.sort >> SORT
		1 * weaponQueryBuilder.setSort(SORT)

		then: 'page is retrieved'
		1 * weaponQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
