package com.cezarykluczynski.stapi.etl.common.backup

import com.cezarykluczynski.stapi.model.util.DbUtils
import com.zaxxer.hikari.HikariDataSource
import spock.lang.Specification

class PostgresCommandProviderTest extends Specification {

	static final String STEP_NAME = 'STEP_NAME'
	static final String JDBC_URL = 'JDBC_URL'
	static final String PG_DUMP_PATH = 'PG_DUMP_PATH'
	static final String ABSOLUTE_PATH = 'ABSOLUTE_PATH'

	BackupAfterStepProperties backupAfterStepPropertiesMock
	HikariDataSource hikariDataSourceMock
	DbUtils dbUtilsMock
	PostgresCommandProvider postgresCommandProvider

	void setup() {
		backupAfterStepPropertiesMock = Mock()
		hikariDataSourceMock = Mock()
		dbUtilsMock = Mock()
		postgresCommandProvider = new PostgresCommandProvider(backupAfterStepPropertiesMock, hikariDataSourceMock, dbUtilsMock)
	}

	void "when port cannot be obtained, empty list is returned"() {
		given:
		File file = Mock()

		when:
		List<String> command = postgresCommandProvider.provide(file, STEP_NAME)

		then:
		1 * hikariDataSourceMock.jdbcUrl >> JDBC_URL
		1 * dbUtilsMock.extractPortFromJdbcUrl(JDBC_URL) >> -1
		0 * _
		command.empty
	}

	void "when port can be obtained, command is returned"() {
		given:
		File file = Mock()

		when:
		List<String> command = postgresCommandProvider.provide(file, STEP_NAME)

		then:
		1 * hikariDataSourceMock.jdbcUrl >> JDBC_URL
		1 * dbUtilsMock.extractPortFromJdbcUrl(JDBC_URL) >> 5432
		1 * file.absolutePath >> ABSOLUTE_PATH
		1 * backupAfterStepPropertiesMock.pgDumpPath >> PG_DUMP_PATH
		0 * _
		command.size() == 22
		command[0] == '"PG_DUMP_PATH"'
		command[2] == ABSOLUTE_PATH
	}

}
