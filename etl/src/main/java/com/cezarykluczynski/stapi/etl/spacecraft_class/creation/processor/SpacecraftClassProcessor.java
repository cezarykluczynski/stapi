package com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.starship_class.processor.StarshipClassTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftClassProcessor extends CompositeItemProcessor<PageHeader, SpacecraftClass> {

	public SpacecraftClassProcessor(PageHeaderProcessor pageHeaderProcessor, StarshipClassTemplatePageProcessor starshipClassTemplatePageProcessor,
			StarshipClassTemplateProcessor starshipClassTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, starshipClassTemplatePageProcessor, starshipClassTemplateProcessor));
	}

}
