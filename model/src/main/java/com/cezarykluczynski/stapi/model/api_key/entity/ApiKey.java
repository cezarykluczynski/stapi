package com.cezarykluczynski.stapi.model.api_key.entity;

import com.cezarykluczynski.stapi.model.account.entity.Account;
import com.cezarykluczynski.stapi.model.api_key.repository.ApiKeyRepository;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.throttle.entity.Throttle;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "api_key", schema = "stapi_users")
@ToString(exclude = {"account", "throttle"})
@EqualsAndHashCode(exclude = {"account", "throttle"})
@TrackedEntity(type = TrackedEntityType.TECHNICAL, repository = ApiKeyRepository.class, apiEntity = false, singularName = "api key",
		pluralName = "api keys")
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

	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "apiKey", referencedColumnName = "apiKey", updatable = false, insertable = false, unique = true)
	private Throttle throttle;

	private Integer limit;

	private Boolean blocked;

	private String url;

	private String description;

}
