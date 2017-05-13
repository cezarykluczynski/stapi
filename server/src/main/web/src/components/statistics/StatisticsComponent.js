import { Component } from 'react';
import { RestApi } from '../../service/rest/RestApi.js';

export class StatisticsComponent extends Component {

	constructor() {
		super();
		this.restApi = RestApi.getInstance();
		this.restApi.onStatisticsReady(() => {
			this.updateStatisticsFromRestApi();
		});
	}

	componentDidMount() {
		if (this.restApi.hasStatistics()) {
			this.updateStatisticsFromRestApi();
		}
	}

	updateStatisticsFromRestApi() {
		this.setState({
			statistics: this.restApi.getStatistics(),
			details: this.restApi.getDetails()
		});
	}

	hasStatistics() {
		return this.state && this.state.statistics;
	}

	getEntityNameToPluralNameMap() {
		const names = {};

		this.state.details.forEach((detail) => {
			names[detail.name] = detail.pluralName;
		});

		return names;
	}

}
