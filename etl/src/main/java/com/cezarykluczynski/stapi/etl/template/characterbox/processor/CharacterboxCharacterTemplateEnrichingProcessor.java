package com.cezarykluczynski.stapi.etl.template.characterbox.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.etl.template.character.processor.CharacterTemplateWithCharacterboxTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.characterbox.dto.CharacterboxTemplate;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterboxCharacterTemplateEnrichingProcessor implements ItemWithTemplateEnrichingProcessor<CharacterTemplate> {

	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_BETA_EN;

	private final PageApi pageApi;

	private final CharacterboxTemplateProcessor characterboxTemplateProcessor;

	private final CharacterTemplateWithCharacterboxTemplateEnrichingProcessor characterTemplateWithCharacterboxTemplateEnrichingProcessor;

	public CharacterboxCharacterTemplateEnrichingProcessor(PageApi pageApi, CharacterboxTemplateProcessor characterboxTemplateProcessor,
			CharacterTemplateWithCharacterboxTemplateEnrichingProcessor characterTemplateWithCharacterboxTemplateEnrichingProcessor) {
		this.pageApi = pageApi;
		this.characterboxTemplateProcessor = characterboxTemplateProcessor;
		this.characterTemplateWithCharacterboxTemplateEnrichingProcessor = characterTemplateWithCharacterboxTemplateEnrichingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, CharacterTemplate> enrichablePair) throws Exception {
		Template template = enrichablePair.getInput();
		CharacterTemplate characterTemplate = enrichablePair.getOutput();

		List<Template.Part> templatePartList = template.getParts();

		if (templatePartList.size() == 0) {
			Template.Part templatePart = new Template.Part();
			templatePart.setValue(characterTemplate.getPage().getTitle());
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
			characterTemplateWithCharacterboxTemplateEnrichingProcessor
					.enrich(EnrichablePair.of(characterboxTemplate, characterTemplate));
		}
	}

}
