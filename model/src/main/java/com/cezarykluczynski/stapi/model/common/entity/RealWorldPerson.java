package com.cezarykluczynski.stapi.model.common.entity;

import com.cezarykluczynski.stapi.model.common.entity.enums.Gender;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class RealWorldPerson extends PageAwareEntity implements PageAware {

	@Column(nullable = false)
	private String name;

	private String birthName;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private LocalDate dateOfBirth;

	private String placeOfBirth;

	private LocalDate dateOfDeath;

	private String placeOfDeath;

}
