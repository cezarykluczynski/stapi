package com.cezarykluczynski.stapi.etl.magazine.creation.processor;

import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplate;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class MagazineTemplateProcessor implements ItemProcessor<MagazineTemplate, Magazine> {

	private final UidGenerator uidGenerator;

	public MagazineTemplateProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public Magazine process(MagazineTemplate item) throws Exception {
		Magazine magazine = new Magazine();

		magazine.setTitle(item.getTitle());
		magazine.setPage(item.getPage());
		magazine.setUid(uidGenerator.generateFromPage(item.getPage(), Magazine.class));
		magazine.setPublishedYear(item.getPublishedYear());
		magazine.setPublishedMonth(item.getPublishedMonth());
		magazine.setPublishedDay(item.getPublishedDay());
		magazine.setCoverYear(item.getCoverYear());
		magazine.setCoverMonth(item.getCoverMonth());
		magazine.setCoverDay(item.getCoverDay());
		magazine.setNumberOfPages(item.getNumberOfPages());
		magazine.setIssueNumber(item.getIssueNumber());
		magazine.getMagazineSeries().addAll(item.getMagazineSeries());
		magazine.getEditors().addAll(item.getEditors());
		magazine.getPublishers().addAll(item.getPublishers());

		return magazine;
	}

}
