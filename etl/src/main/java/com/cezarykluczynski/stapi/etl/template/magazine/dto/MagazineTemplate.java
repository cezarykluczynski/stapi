package com.cezarykluczynski.stapi.etl.template.magazine.dto;

import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.magazineSeries.entity.MagazineSeries;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

@Data
public class MagazineTemplate {

	private Page page;

	private String title;

	private Integer publishedYear;

	private Integer publishedMonth;

	private Integer publishedDay;

	private Integer coverYear;

	private Integer coverMonth;

	private Integer coverDay;

	private Integer numberOfPages;

	private Set<MagazineSeries> magazineSeries = Sets.newHashSet();

	private Set<Staff> editors = Sets.newHashSet();

	private Set<Company> publishers = Sets.newHashSet();

}
