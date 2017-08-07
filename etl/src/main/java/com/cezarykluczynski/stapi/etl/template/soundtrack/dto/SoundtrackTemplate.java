package com.cezarykluczynski.stapi.etl.template.soundtrack.dto;

import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.reference.entity.Reference;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.google.common.collect.Sets;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class SoundtrackTemplate {

	private String title;

	private Page page;

	private LocalDate releaseDate;

	private Integer length;

	private Set<Company> labels = Sets.newHashSet();

	private Set<Staff> composers = Sets.newHashSet();

	private Set<Staff> contributors = Sets.newHashSet();

	private Set<Reference> references = Sets.newHashSet();

	private Set<Staff> orchestrators = Sets.newHashSet();

}
