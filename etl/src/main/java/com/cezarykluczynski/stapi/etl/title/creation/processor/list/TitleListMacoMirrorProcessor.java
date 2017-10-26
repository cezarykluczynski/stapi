package com.cezarykluczynski.stapi.etl.title.creation.processor.list;

import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.page.repository.PageRepository;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.model.title.repository.TitleRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class TitleListMacoMirrorProcessor {

	private static final List<String> MACO_RANKS_MIRROR = Lists.newArrayList("Major", "Sergeant", "Corporal", "Private");

	private final PageRepository pageRepository;

	private final UidGenerator uidGenerator;

	private final TitleRepository titleRepository;

	TitleListMacoMirrorProcessor(PageRepository pageRepository, UidGenerator uidGenerator, TitleRepository titleRepository) {
		this.pageRepository = pageRepository;
		this.uidGenerator = uidGenerator;
		this.titleRepository = titleRepository;
	}

	void produceAll(Page item, int index) {
		for (int j = 0; j < MACO_RANKS_MIRROR.size(); j++) {
			toMacoTitle(MACO_RANKS_MIRROR.get(j), item, index + j * 10);
		}
	}

	private void toMacoTitle(String macoTitleName, Page item, int index) {
		com.cezarykluczynski.stapi.model.page.entity.Page modelPage = pageRepository.findByPageId(item.getPageId()).orElse(null);
		Title title = new Title();
		title.setName(macoTitleName + " (MACO, mirror)");
		title.setPage(modelPage);
		title.setUid(uidGenerator.generateForTitleListItem(title.getPage(), index));
		title.setMilitaryRank(true);
		title.setReligiousTitle(false);
		title.setPosition(false);
		title.setFleetRank(false);
		title.setMirror(true);
		titleRepository.save(title);
	}

}
