package com.cezarykluczynski.stapi.etl.magazine.creation.processor;

import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplate;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MagazineTemplateProcessor implements ItemProcessor<MagazineTemplate, Magazine> {

	private final UidGenerator uidGenerator;

	@Inject
	public MagazineTemplateProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public Magazine process(MagazineTemplate item) throws Exception {
		//TODO
		return null;
	}

}
