import { Injectable } from '@angular/core';

import RestClient from 'another-rest-client/dist/rest-client';

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
		const sorter = (left: any, right: any) => {
			return left > right ? 1 : left === right ? 0 : -1;
		};
		const entitiesStatisticsPromise = (<any> this.api).common.statistics.entities.get().then((response: any) => {
			response.statistics.sort(sorter);
			this.statistics = this.statistics || {};
			this.statistics.entitiesStatistics = response;
		});
		return Promise.all([entitiesStatisticsPromise]).then(() => {
			this.statistics.loaded = true;
		});
	}

	getStatistics() {
		return this.statistics;
	}

	private register() {
		this.api.res('common').res('statistics').res('entities');
	}

}
