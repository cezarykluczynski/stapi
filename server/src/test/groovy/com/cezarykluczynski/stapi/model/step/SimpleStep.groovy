package com.cezarykluczynski.stapi.model.step

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.batch.core.BatchStatus
import org.springframework.data.repository.CrudRepository

@Entity(name = 'SimpleStep')
@Table(schema = 'stapi', name = 'batch_step_execution')
@SuppressWarnings('UnusedPrivateField')
@TrackedEntity(type = TrackedEntityType.TECHNICAL, repository = CrudRepository, apiEntity = false, singularName = '', pluralName = '')
class SimpleStep {

	@Id
	@Column(name = 'step_execution_id')
	private Long id

	private String stepName

	@Enumerated(EnumType.STRING)
	private BatchStatus status

	Long getId() {
		id
	}

	void setId(Long id) {
		this.id = id
	}

	String getStepName() {
		stepName
	}

	void setStepName(String stepName) {
		this.stepName = stepName
	}

	BatchStatus getStatus() {
		status
	}

	void setStatus(BatchStatus status) {
		this.status = status
	}

}
