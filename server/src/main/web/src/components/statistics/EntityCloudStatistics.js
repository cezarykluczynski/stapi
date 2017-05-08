import React, { Component } from 'react';
import './EntityCloudStatistics.css';
import { RestApi } from '../../service/rest/RestApi.js';
import { TagCloud } from "react-tagcloud";

export class EntityCloudStatistics extends Component {

	constructor() {
		super();
		this.restApi = RestApi.getInstance();
		this.restApi.onStatisticsReady(() => {
			this.setState({
				statistics: this.restApi.getStatistics(),
				details: this.restApi.getDetails()
			});
		});
	}

	render() {
		return (
			<div className='entity-cloud-statistics'>
				{this.hasStatistics() ? this.renderStatistics() : ''}
			</div>
		);
	}

	renderStatistics() {
		return (
			<div >
				<h4>Currently serving...</h4>
				<hr/>
				<TagCloud minSize={18} maxSize={35} tags={this.getStatisticsForFictionalEntities()}
					shuffle={false} disableRandomColor={true} />
				<hr/>
				<TagCloud minSize={18} maxSize={35} tags={this.getStatisticsForRealWorldEntities()}
					shuffle={false} disableRandomColor={true} />
			</div>
		);
	}

	hasStatistics() {
		return this.state && this.state.statistics;
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

		let entities = Array.from(this.state.statistics).filter((item) => {
			return entitiesNames.includes(item.name);
		});
		let names = {};

		this.state.details.forEach((detail) => {
			names[detail.name] = detail.pluralName;
		});

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
