package com.cezarykluczynski.stapi.etl.template.magazine_series.dto;

import com.cezarykluczynski.stapi.etl.template.publishable_series.dto.PublishableSeriesTemplate;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class MagazineSeriesTemplate extends PublishableSeriesTemplate {

	private Integer numberOfIssues;

	private Set<Staff> editors = Sets.newHashSet();

}
