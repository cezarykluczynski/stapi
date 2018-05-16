package com.cezarykluczynski.stapi.model.account.entity;

import com.cezarykluczynski.stapi.model.account.repository.AccountRepository;
import com.cezarykluczynski.stapi.model.api_key.entity.ApiKey;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.consent.entity.Consent;
import com.google.common.collect.Sets;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Set;

@Data
@Entity
@Table(name = "account", schema = "stapi_users")
@ToString(exclude = {"apiKeys", "consents"})
@EqualsAndHashCode(exclude = {"apiKeys", "consents"})
@TrackedEntity(type = TrackedEntityType.TECHNICAL, repository = AccountRepository.class, apiEntity = false, singularName = "account",
		pluralName = "accounts")
public class Account {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence_generator")
	@SequenceGenerator(name = "account_sequence_generator", sequenceName = "account_sequence", schema = "stapi_users", allocationSize = 1)
	private Long id;

	private String name;

	private String email;

	@Column(name = "github_user_id")
	private Long gitHubUserId;

	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<ApiKey> apiKeys = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinTable(name = "accounts_consents", schema = "stapi_users",
			joinColumns = @JoinColumn(name = "account_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "consent_id", nullable = false, updatable = false))
	private Set<Consent> consents = Sets.newHashSet();

}
