package com.cezarykluczynski.stapi.etl.performer.creation.processor;

import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class PerformerWriter implements ItemWriter<Performer> {

	private PerformerRepository performerRepository;

	@Inject
	public PerformerWriter(PerformerRepository performerRepository) {
		this.performerRepository = performerRepository;
	}

	@Override
	public void write(List<? extends Performer> items) throws Exception {
		performerRepository.save(items);
	}

}
