package com.cezarykluczynski.stapi.etl.common.backup;

import com.cezarykluczynski.stapi.model.util.DbUtils;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
class PostgresCommandProvider {

	private static final String BACKUP_COMMAND = "--file %s --host \"localhost\" --port %s --username \"postgres\" --verbose --role \"postgres\" "
			+ "--format=t --blobs --section=pre-data --section=data --section=post-data --encoding \"UTF8\" --schema \"stapi\" \"stapi\"";

	private final BackupAfterStepProperties backupAfterStepProperties;
	private final HikariDataSource hikariDataSource;
	private final DbUtils dbUtils;

	List<String> provide(File backupFile, String stepName) {
		String jdbcUrl = hikariDataSource.getJdbcUrl();
		int port = dbUtils.extractPortFromJdbcUrl(jdbcUrl);
		if (port <= 0) {
			log.error("JDBC URL {} could not be parsed and port could not be extracted, skipping PostgreSQL backup for step {}.", jdbcUrl, stepName);
			return List.of();
		}

		String params = String.format(BACKUP_COMMAND, backupFile.getAbsolutePath(), port);
		List<String> command = Lists.newArrayList(Arrays.stream(params.split(" ")).toList());
		command.add(0, backupAfterStepProperties.getPgDumpPath());
		return command;
	}

}
