package com.cezarykluczynski.stapi.model.weapon.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.weapon.repository.WeaponRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = WeaponRepository.class, singularName = "weapon",
		pluralName = "weapons", restApiVersion = "v2")
public class Weapon extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weapon_sequence_generator")
	@SequenceGenerator(name = "weapon_sequence_generator", sequenceName = "weapon_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	private Boolean handHeldWeapon;

	private Boolean laserTechnology;

	private Boolean plasmaTechnology;

	private Boolean photonicTechnology;

	private Boolean phaserTechnology;

	private Boolean directedEnergyWeapon;

	private Boolean explosiveWeapon;

	private Boolean projectileWeapon;

	private Boolean fictionalWeapon;

	private Boolean mirror;

	private Boolean alternateReality;

}
