package com.cezarykluczynski.stapi.etl.template.starship.processor;

import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassTemplateSpacecraftClassesProcessor implements ItemProcessor<Template.Part, List<SpacecraftClass>> {

	@Override
	public List<SpacecraftClass> process(Template.Part item) throws Exception {
		// TODO
		return null;
	}

}
