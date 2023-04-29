package com.cezarykluczynski.stapi.etl.common.backup;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({BackupAfterStepProperties.class})
public class BackupAfterStepConfiguration {
}
