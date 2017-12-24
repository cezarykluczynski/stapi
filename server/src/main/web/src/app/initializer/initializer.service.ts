import { Injectable } from '@angular/core';

import { ApiBrowserApi } from '../api-browser/api-browser-api.service';
import { StatisticsApi } from '../statistics/statistics-api.service';
import { ApiDocumentationApi } from '../api-documentation/api-documentation-api.service';
import { FeatureSwitchApi } from '../feature-switch/feature-switch-api.service';

@Injectable()
export class InitializerService {

	private apiBrowserApi: ApiBrowserApi;
	private statisticsApi: StatisticsApi;
	private apiDocumentationApi: ApiDocumentationApi;
	private featureSwitchApi: FeatureSwitchApi;

	constructor(apiBrowserApi: ApiBrowserApi, statisticsApi: StatisticsApi, apiDocumentationApi: ApiDocumentationApi,
			featureSwitchApi: FeatureSwitchApi) {
		this.apiBrowserApi = apiBrowserApi;
		this.statisticsApi = statisticsApi;
		this.apiDocumentationApi = apiDocumentationApi;
		this.featureSwitchApi = featureSwitchApi;
	}

	init() {
		return Promise.all([
			this.apiBrowserApi.loadDetails(),
			this.statisticsApi.loadStatistics(),
			this.apiDocumentationApi.loadDocumentation(),
			this.featureSwitchApi.loadFeatureSwitches()
		]);
	}

}
