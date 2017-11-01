package com.cezarykluczynski.stapi.model.account.entity;

import com.cezarykluczynski.stapi.model.account.repository.AccountRepository;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@ToString
@TrackedEntity(type = TrackedEntityType.TECHNICAL, repository = AccountRepository.class, apiEntity = false, singularName = "account",
		pluralName = "accounts")
@Table(name = "account", schema = "stapi_users")
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

}
