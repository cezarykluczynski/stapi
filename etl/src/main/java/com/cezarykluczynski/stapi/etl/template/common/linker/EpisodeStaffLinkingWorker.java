package com.cezarykluczynski.stapi.etl.template.common.linker;

import com.cezarykluczynski.stapi.etl.common.processor.LinkingWorker;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

@Service
public class EpisodeStaffLinkingWorker implements LinkingWorker<Page, Episode> {

	@Override
	public void link(Page source, Episode baseEntity) {
		// TODO
	}

}
