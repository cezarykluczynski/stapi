package com.cezarykluczynski.stapi.etl.astronomical_object.link.processor;

import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.data.RepositoryItemReader;

@Slf4j
public class AstronomicalObjectLinkReader extends RepositoryItemReader<AstronomicalObject> {
}
