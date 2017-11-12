import { Injectable } from '@angular/core';

import RestClient from 'another-rest-client';

import { RestApiService } from '../rest-api/rest-api.service';

@Injectable()
export class StatisticsApi {

	private api: RestClient;
	private statistics: any;

	constructor(restApiService: RestApiService) {
		this.api = restApiService.getApi();
		this.register();
	}

	loadStatistics() {
		const sorter = (left, right) => {
			return left > right ? 1 : left === right ? 0 : -1;
		};
		const entitiesStatisticsPromise = this.api.common.statistics.entities.get().then(response => {
			response.statistics.sort(sorter);
			this.statistics = this.statistics || {};
			this.statistics.entitiesStatistics = response;
		});
		const hitsStatisticsPromise = this.api.common.statistics.hits.get().then(response => {
			response.statistics.sort(sorter);
			this.statistics = this.statistics || {};
			this.statistics.hitsStatistics = response;
		});
		return Promise.all([entitiesStatisticsPromise, hitsStatisticsPromise]).then(() => {
			this.statistics.loaded = true;
		});
	}

	getStatistics() {
		return this.statistics;
	}

	private register() {
		this.api.res('common').res('statistics').res('entities');
		this.api.res('common').res('statistics').res('hits');
	}

}
