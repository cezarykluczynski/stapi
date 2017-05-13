import React from 'react';
import './EntityStatisticsCloud.css';
import { TagCloud } from "react-tagcloud";
import { StatisticsComponent } from './StatisticsComponent.js';

export class EntityStatisticsCloud extends StatisticsComponent {

	constructor() {
		super();
	}

	render() {
		return (
			<div className='entity-statistics-cloud'>
				{this.hasStatistics() ? this.renderStatistics() : ''}
			</div>
		);
	}

	renderStatistics() {
		return (
			<div>
				<h4>Currently serving <strong>{this.state.statistics.entitiesStatistics.totalCount}</strong> entities!</h4>
				<hr/>
				<TagCloud minSize={18} maxSize={35} tags={this.getStatisticsForFictionalEntities()}
					shuffle={false} disableRandomColor={true} />
				<hr/>
				<TagCloud minSize={18} maxSize={35} tags={this.getStatisticsForRealWorldEntities()}
					shuffle={false} disableRandomColor={true} />
			</div>
		);
	}

	getStatisticsForFictionalEntities() {
		return this.getEntitiesOfType('FICTIONAL_PRIMARY');
	}

	getStatisticsForRealWorldEntities() {
		return this.getEntitiesOfType('REAL_WORLD_PRIMARY');
	}

	getEntitiesOfType(type) {
		let entitiesNames = this.state.details
			.filter(entity => entity.type === type)
			.map(entity => entity.name);

		let entities = Array.from(this.state.statistics.entitiesStatistics.statistics).filter((item) => {
			return entitiesNames.includes(item.name);
		});
		let names = this.getEntityNameToPluralNameMap();

		entities.sort((left, right) => {
			return left.count < right.count ? 1 : (left.count === right.count ? 0 : -1);
		});

		return entities.map((entity) => {
			return {
				value: entity.count + ' ' + names[entity.name],
				count: entity.count
			};
		});
	}


}
