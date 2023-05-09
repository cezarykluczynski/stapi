package com.cezarykluczynski.stapi.etl.soundtrack.creation.processor;

import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import com.cezarykluczynski.stapi.model.soundtrack.repository.SoundtrackRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class SoundtrackWriter implements ItemWriter<Soundtrack> {

	private final SoundtrackRepository soundtrackRepository;

	public SoundtrackWriter(SoundtrackRepository soundtrackRepository) {
		this.soundtrackRepository = soundtrackRepository;
	}

	@Override
	public void write(Chunk<? extends Soundtrack> items) throws Exception {
		soundtrackRepository.saveAll(items.getItems());
	}

}
