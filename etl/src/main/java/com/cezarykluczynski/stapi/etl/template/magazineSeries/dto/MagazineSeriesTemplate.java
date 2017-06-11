package com.cezarykluczynski.stapi.etl.template.magazineSeries.dto;

import com.cezarykluczynski.stapi.etl.template.publishableSeries.dto.PublishableSeriesTemplate;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class MagazineSeriesTemplate extends PublishableSeriesTemplate {

	private Set<Staff> editors = Sets.newHashSet();

}
