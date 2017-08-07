package com.cezarykluczynski.stapi.etl.template.soundtrack.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.RecordingTimeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatePartToLocalDateProcessor;
import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplate;
import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SoundtrackTemplateContentsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, SoundtrackTemplate>> {

	private final DatePartToLocalDateProcessor datePartToLocalDateProcessor;

	private final NumberOfPartsProcessor numberOfPartsProcessor;

	private final RecordingTimeProcessor recordingTimeProcessor;

	@Inject
	public SoundtrackTemplateContentsEnrichingProcessor(DatePartToLocalDateProcessor datePartToLocalDateProcessor,
			NumberOfPartsProcessor numberOfPartsProcessor, RecordingTimeProcessor recordingTimeProcessor) {
		this.datePartToLocalDateProcessor = datePartToLocalDateProcessor;
		this.numberOfPartsProcessor = numberOfPartsProcessor;
		this.recordingTimeProcessor = recordingTimeProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, SoundtrackTemplate> enrichablePair) throws Exception {
		Template template = enrichablePair.getInput();
		SoundtrackTemplate soundtrackTemplate = enrichablePair.getOutput();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case SoundtrackTemplateParameter.RELEASED:
					soundtrackTemplate.setReleaseDate(datePartToLocalDateProcessor.process(part));
					break;
				case SoundtrackTemplateParameter.DISKS:
					soundtrackTemplate.setNumberOfDataCarriers(numberOfPartsProcessor.process(value));
					break;
				case SoundtrackTemplateParameter.LENGTH:
					soundtrackTemplate.setLength(recordingTimeProcessor.process(value));
					break;
				default:
					break;
			}
		}
	}

}
