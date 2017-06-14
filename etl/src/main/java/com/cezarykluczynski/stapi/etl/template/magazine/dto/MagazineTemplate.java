package com.cezarykluczynski.stapi.etl.template.magazine.dto;

import com.cezarykluczynski.stapi.etl.template.publishable.dto.PublishableTemplate;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MagazineTemplate extends PublishableTemplate {

	private Integer numberOfPages;

	private String issueNumber;

	private Set<MagazineSeries> magazineSeries = Sets.newHashSet();

	private Set<Staff> editors = Sets.newHashSet();

	private Set<Company> publishers = Sets.newHashSet();

}
