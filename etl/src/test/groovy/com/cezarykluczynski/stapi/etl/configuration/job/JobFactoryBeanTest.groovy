package com.cezarykluczynski.stapi.etl.configuration.job

import org.springframework.batch.core.Job
import spock.lang.Specification

class JobFactoryBeanTest extends Specification {

	def "should create factory with non-null Job"() {
		given:
		Job job = Mock(Job)

		when:
		JobFactoryBean jobFactoryBean = new JobFactoryBean(job)

		then:
		jobFactoryBean.object == job
		jobFactoryBean.objectType == Job.class
		jobFactoryBean.singleton
	}

	def "should create factory with null Job"() {
		when:
		JobFactoryBean jobFactoryBean = new JobFactoryBean(null)

		then:
		jobFactoryBean.object == null
		jobFactoryBean.objectType == null
		jobFactoryBean.singleton
	}

}
