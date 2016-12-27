package com.cezarykluczynski.stapi.etl.template.common.dto.performance;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.Set;

@Data
public class EpisodePerformancesEntitiesDTO {

	private Set<Performer> performerSet = Sets.newHashSet();

	private Set<Character> characterSet = Sets.newHashSet();

}
