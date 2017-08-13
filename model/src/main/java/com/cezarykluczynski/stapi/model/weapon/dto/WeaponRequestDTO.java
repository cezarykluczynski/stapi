package com.cezarykluczynski.stapi.model.weapon.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class WeaponRequestDTO {

	private String uid;

	private String name;

	private Boolean handHeldWeapon;

	private Boolean laserTechnology;

	private Boolean plasmaTechnology;

	private Boolean photonicTechnology;

	private Boolean phaserTechnology;

	private Boolean mirror;

	private Boolean alternateReality;

	private RequestSortDTO sort;

}
