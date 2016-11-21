package com.cezarykluczynski.stapi.model.character.repository;

import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;

public interface CharacterRepositoryCustom extends CriteriaMatcher<CharacterRequestDTO, Character> {
}
