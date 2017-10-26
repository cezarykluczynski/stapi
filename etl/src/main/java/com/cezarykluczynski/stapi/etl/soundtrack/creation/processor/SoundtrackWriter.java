package com.cezarykluczynski.stapi.etl.soundtrack.creation.processor;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import com.cezarykluczynski.stapi.model.soundtrack.repository.SoundtrackRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoundtrackWriter implements ItemWriter<Soundtrack> {

	private final SoundtrackRepository soundtrackRepository;

	private final DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor;

	public SoundtrackWriter(SoundtrackRepository soundtrackRepository,
			DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessor) {
		this.soundtrackRepository = soundtrackRepository;
		this.duplicateFilteringPreSavePageAwareProcessor = duplicateFilteringPreSavePageAwareProcessor;
	}

	@Override
	public void write(List<? extends Soundtrack> items) throws Exception {
		soundtrackRepository.save(process(items));
	}

	private List<Soundtrack> process(List<? extends Soundtrack> soundtrackList) {
		List<Soundtrack> comicsListWithoutExtends = fromExtendsListToSoundtrackList(soundtrackList);
		return filterDuplicates(comicsListWithoutExtends);
	}

	private List<Soundtrack> fromExtendsListToSoundtrackList(List<? extends Soundtrack> soundtracks) {
		return soundtracks
				.stream()
				.map(pageAware -> (Soundtrack) pageAware)
				.collect(Collectors.toList());
	}

	private List<Soundtrack> filterDuplicates(List<Soundtrack> soundtrackList) {
		return duplicateFilteringPreSavePageAwareProcessor.process(soundtrackList.stream()
				.map(soundtrack -> (PageAware) soundtrack)
				.collect(Collectors.toList()), Soundtrack.class).stream()
				.map(pageAware -> (Soundtrack) pageAware)
				.collect(Collectors.toList());
	}

}
