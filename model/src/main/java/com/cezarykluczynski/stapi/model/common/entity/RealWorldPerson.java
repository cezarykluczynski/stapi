package com.cezarykluczynski.stapi.model.common.entity;

import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Data
@EqualsAndHashCode
@ToString
public class RealWorldPerson implements PageAware {

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "page_id")
	private Page page;

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
