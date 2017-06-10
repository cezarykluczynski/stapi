package com.cezarykluczynski.stapi.etl.magazineSeries.creation.processor;

import com.cezarykluczynski.stapi.etl.template.magazineSeries.dto.MagazineSeriesTemplate;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.magazineSeries.entity.MagazineSeries;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MagazineSeriesTemplateProcessor implements ItemProcessor<MagazineSeriesTemplate, MagazineSeries> {

	private final UidGenerator uidGenerator;

	@Inject
	public MagazineSeriesTemplateProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public MagazineSeries process(MagazineSeriesTemplate item) throws Exception {
		//TODO
		return null;
	}


}
