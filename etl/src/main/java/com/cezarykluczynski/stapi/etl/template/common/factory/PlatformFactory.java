package com.cezarykluczynski.stapi.etl.template.common.factory;

import com.cezarykluczynski.stapi.etl.platform.service.PlatformCodeToNameMapper;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.platform.entity.Platform;
import com.cezarykluczynski.stapi.model.platform.repository.PlatformRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PlatformFactory {

	private final PlatformCodeToNameMapper platformCodeToNameMapper;

	private final PlatformRepository platformRepository;

	private final UidGenerator uidGenerator;

	public PlatformFactory(PlatformCodeToNameMapper platformCodeToNameMapper, PlatformRepository platformRepository, UidGenerator uidGenerator) {
		this.platformCodeToNameMapper = platformCodeToNameMapper;
		this.platformRepository = platformRepository;
		this.uidGenerator = uidGenerator;
	}

	public synchronized Optional<Platform> createForCode(String code) {
		String name = platformCodeToNameMapper.map(code);

		if (name == null) {
			log.warn("Could not map code \"{}\" to platform name", code);
			return Optional.empty();
		}

		Optional<Platform> platformOptional = platformRepository.findByName(name);

		if (platformOptional.isPresent()) {
			return platformOptional;
		} else {
			Platform platform = new Platform();
			platform.setName(name);
			platform.setUid(uidGenerator.generateForPlatform(code));
			platformRepository.save(platform);
			return Optional.of(platform);
		}
	}

}
