package com.cezarykluczynski.stapi.etl.common.backup;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "etl.backup-after-step")
public class BackupAfterStepProperties {

	private boolean enabled;

	private String targetDirectory;

	private String pgDumpPath;

}
