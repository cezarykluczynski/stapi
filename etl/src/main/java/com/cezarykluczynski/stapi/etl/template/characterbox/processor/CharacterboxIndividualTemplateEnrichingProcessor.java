package com.cezarykluczynski.stapi.etl.template.characterbox.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.characterbox.dto.CharacterboxTemplate;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate;
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualTemplateWithCharacterboxTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class CharacterboxIndividualTemplateEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, IndividualTemplate>> {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_BETA_EN;

	private PageApi pageApi;

	private CharacterboxTemplateProcessor characterboxTemplateProcessor;

	private IndividualTemplateWithCharacterboxTemplateEnrichingProcessor individualTemplateWithCharacterboxTemplateEnrichingProcessor;

	@Inject
	public CharacterboxIndividualTemplateEnrichingProcessor(PageApi pageApi, CharacterboxTemplateProcessor characterboxTemplateProcessor,
			IndividualTemplateWithCharacterboxTemplateEnrichingProcessor individualTemplateWithCharacterboxTemplateEnrichingProcessor) {
		this.pageApi = pageApi;
		this.characterboxTemplateProcessor = characterboxTemplateProcessor;
		this.individualTemplateWithCharacterboxTemplateEnrichingProcessor = individualTemplateWithCharacterboxTemplateEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, IndividualTemplate> enrichablePair) throws Exception {
		Template template = enrichablePair.getInput();
		IndividualTemplate individualTemplate = enrichablePair.getOutput();

		List<Template.Part> templatePartList = template.getParts();

		if (templatePartList.size() == 0) {
			Template.Part templatePart = new Template.Part();
			templatePart.setValue(individualTemplate.getPage().getTitle());
			template.getParts().add(templatePart);
		}

		if (!TemplateTitle.MBETA.equals(template.getTitle()) || templatePartList.size() != 1) {
			return;
		}

		Page page = pageApi.getPage(TitleUtil.toMediaWikiTitle(templatePartList.get(0).getValue()), SOURCE);

		if (page == null) {
			return;
		}

		CharacterboxTemplate characterboxTemplate = characterboxTemplateProcessor.process(page);
		if (characterboxTemplate != null) {
			individualTemplateWithCharacterboxTemplateEnrichingProcessor
					.enrich(EnrichablePair.of(characterboxTemplate, individualTemplate));
		}
	}

}
