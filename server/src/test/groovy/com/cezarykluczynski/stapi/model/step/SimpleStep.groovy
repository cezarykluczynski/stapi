package com.cezarykluczynski.stapi.model.step

import lombok.Data
import org.springframework.batch.core.BatchStatus

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table

@Data
@Entity(name = "SimpleStep")
@Table(name = "batch_step_execution")
public class SimpleStep {

	@Id
	@Column(name = "step_execution_id")
	private Long id

	private String stepName

	@Enumerated(EnumType.STRING)
	private BatchStatus status

}
