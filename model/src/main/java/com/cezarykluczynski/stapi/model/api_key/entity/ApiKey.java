package com.cezarykluczynski.stapi.model.api_key.entity;

import com.cezarykluczynski.stapi.model.account.entity.Account;
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@ToString
@TrackedEntity(type = TrackedEntityType.TECHNICAL, repository = ApiKeyRepository.class, apiEntity = false, singularName = "api key",
		pluralName = "api keys")
@Table(name = "api_key", schema = "stapi_users")
public class ApiKey {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "api_key_sequence_generator")
	@SequenceGenerator(name = "api_key_sequence_generator", sequenceName = "api_key_sequence", schema = "stapi_users", allocationSize = 1)
	private Long id;

	private String apiKey;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@Column(name = "account_id", insertable = false, updatable = false)
	private Long accountId;

	private Integer limit;

	private Boolean blocked;

	private String description;

}
