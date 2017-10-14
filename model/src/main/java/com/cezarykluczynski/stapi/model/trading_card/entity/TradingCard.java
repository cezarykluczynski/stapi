package com.cezarykluczynski.stapi.model.trading_card.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.trading_card.repository.TradingCardRepository;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Data
@Entity
@ToString(exclude = {"tradingCardSet", "tradingCardDeck"})
@EqualsAndHashCode(exclude = {"tradingCardSet", "tradingCardDeck"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = TradingCardRepository.class, singularName = "trading card set",
		pluralName = "trading card sets")
public class TradingCard {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trading_card_sequence_generator")
	@SequenceGenerator(name = "trading_card_sequence_generator", sequenceName = "trading_card_sequence", allocationSize = 1)
	private Long id;

	@Column(name = "u_id")
	private String uid;

	private String name;

	@Column(name = "card_number")
	private String number;

	private Integer releaseYear;

	private Integer productionRun;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "trading_card_set_id")
	private TradingCardSet tradingCardSet;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "trading_card_deck_id")
	private TradingCardDeck tradingCardDeck;

}
