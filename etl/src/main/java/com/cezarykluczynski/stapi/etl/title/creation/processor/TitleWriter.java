package com.cezarykluczynski.stapi.etl.title.creation.processor;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.model.title.repository.TitleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TitleWriter implements ItemWriter<Title> {

	private final TitleRepository titleRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public TitleWriter(TitleRepository titleRepository, DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.titleRepository = titleRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Title> items) throws Exception {
		titleRepository.save(process(items));
	}

	private List<Title> process(List<? extends Title> titleList) {
		List<Title> titleListWithoutExtends = fromExtendsListToTitleList(titleList);
		return filterDuplicates(titleListWithoutExtends);
	}

	private List<Title> fromExtendsListToTitleList(List<? extends Title> titleList) {
		return titleList
				.stream()
				.map(pageAware -> (Title) pageAware)
				.collect(Collectors.toList());
	}

	private List<Title> filterDuplicates(List<Title> titleList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(titleList.stream()
				.map(title -> (PageAware) title)
				.collect(Collectors.toList()), Title.class).stream()
				.map(pageAware -> (Title) pageAware)
				.collect(Collectors.toList());
	}

}
