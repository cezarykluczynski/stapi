package com.cezarykluczynski.stapi.etl.common.service.step;

import com.cezarykluczynski.stapi.etl.common.dto.Range;
import com.cezarykluczynski.stapi.model.configuration.converter.LocalDateTimeAttributeConverter;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Order(3)
@Service
@Slf4j
public class StepTimeLogger implements StepLogger {

	private final Map<String, LocalDateTime> stepsStartTimes = Maps.newLinkedHashMap();
	private final Map<String, Range<LocalDateTime>> stepsTotalTimes = Maps.newLinkedHashMap();

	private final LocalDateTimeAttributeConverter localDateTimeAttributeConverter;

	private final StepTimeFormatter stepTimeFormatter;

	public StepTimeLogger(LocalDateTimeAttributeConverter localDateTimeAttributeConverter, StepTimeFormatter stepTimeFormatter) {
		this.localDateTimeAttributeConverter = localDateTimeAttributeConverter;
		this.stepTimeFormatter = stepTimeFormatter;
	}

	public synchronized void stepStarted(StepExecution stepExecution) {
		stepsStartTimes.put(stepExecution.getStepName(), localDateTimeAttributeConverter.convertToEntityAttribute(stepExecution.getStartTime()));
	}

	public synchronized void stepEnded(StepExecution stepExecution) {
		String stepName = stepExecution.getStepName();
		LocalDateTime stepStartTime = stepsStartTimes.get(stepName);
		stepsStartTimes.remove(stepName);
		stepsTotalTimes.put(stepName, Range.of(stepStartTime, localDateTimeAttributeConverter
				.convertToEntityAttribute(stepExecution.getLastUpdated())));
		log.info(stepTimeFormatter.format(stepsTotalTimes));
	}

}
