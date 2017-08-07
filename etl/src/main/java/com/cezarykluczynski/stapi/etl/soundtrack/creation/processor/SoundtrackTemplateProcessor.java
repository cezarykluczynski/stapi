package com.cezarykluczynski.stapi.etl.soundtrack.creation.processor;

import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplate;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SoundtrackTemplateProcessor implements ItemProcessor<SoundtrackTemplate, Soundtrack> {

	private final UidGenerator uidGenerator;

	public SoundtrackTemplateProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public Soundtrack process(SoundtrackTemplate item) throws Exception {
		Soundtrack soundtrack = new Soundtrack();

		soundtrack.setTitle(item.getTitle());
		soundtrack.setPage(item.getPage());
		soundtrack.setUid(uidGenerator.generateFromPage(item.getPage(), Soundtrack.class));
		soundtrack.setReleaseDate(item.getReleaseDate());
		soundtrack.setLength(item.getLength());
		soundtrack.getLabels().addAll(item.getLabels());
		soundtrack.getComposers().addAll(item.getComposers());
		soundtrack.getContributors().addAll(item.getContributors());
		soundtrack.getOrchestrators().addAll(item.getOrchestrators());
		soundtrack.getReferences().addAll(item.getReferences());

		return soundtrack;
	}

}
