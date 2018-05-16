package com.cezarykluczynski.stapi.model.step

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType
import lombok.Data
import org.springframework.batch.core.BatchStatus
import org.springframework.data.repository.CrudRepository

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table

@Data
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

}
