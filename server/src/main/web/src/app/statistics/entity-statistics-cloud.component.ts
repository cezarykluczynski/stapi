import { Component, OnInit } from '@angular/core';

import { StatisticsApi } from './statistics-api.service';
import { ApiBrowserApi } from '../api-browser/api-browser-api.service';
import {ApiDocumentationApi} from '../api-documentation/api-documentation-api.service';

@Component({
	selector: 'app-entity-statistics-cloud',
	templateUrl: './entity-statistics-cloud.component.html',
	styleUrls: ['./entity-statistics-cloud.component.sass']
})
export class EntityStatisticsCloudComponent implements OnInit {

	private statisticsApi: StatisticsApi;
	private apiBrowserApi: ApiBrowserApi;
	private apiDocumentationApi: ApiDocumentationApi;
	private statistics: any;
	private details: any;
	private fictionalEntities: Array<any>;
	private realWorldEntities: Array<any>;
	private tradingCardsEntities: Array<any>;
	private totalCount: number;
	private fontSizeMin = 18;
	private fontSizeMax = 35;
	private dataVersion: string;

	constructor(statisticsApi: StatisticsApi, apiBrowserApi: ApiBrowserApi, apiDocumentationApi: ApiDocumentationApi) {
		this.statisticsApi = statisticsApi;
		this.apiBrowserApi = apiBrowserApi;
		this.apiDocumentationApi = apiDocumentationApi;
    this.fictionalEntities = [];
    this.realWorldEntities = [];
    this.tradingCardsEntities = [];
    this.totalCount = 0;
    this.dataVersion = '';
	}

	ngOnInit() {
		this.statistics = this.statisticsApi.getStatistics();
		this.details = this.apiBrowserApi.getDetails();
		this.fictionalEntities = this.createStatisticsForFictionalEntities();
		this.realWorldEntities = this.createStatisticsForRealWorldEntities();
		this.tradingCardsEntities = this.createStatisticsForTradingCardsEntities();
		this.dataVersion = this.apiDocumentationApi.getDataVersion();
	}

	createStatisticsForFictionalEntities() {
		return this.getEntitiesOfType('FICTIONAL_PRIMARY', () => true);
	}

	createStatisticsForRealWorldEntities() {
		return this.getEntitiesOfType('REAL_WORLD_PRIMARY', (entity: any) => entity.name.indexOf('TradingCard') === -1);
	}

	createStatisticsForTradingCardsEntities() {
		return this.getEntitiesOfType('REAL_WORLD_PRIMARY', (entity: any) => entity.name.indexOf('TradingCard') > -1);
	}

	getStatisticsForFictionalEntities() {
		return this.fictionalEntities;
	}

	getStatisticsForRealWorldEntities() {
		return this.realWorldEntities;
	}

	getStatisticsForTradingCardsEntities() {
		return this.tradingCardsEntities;
	}

	getTotalCount() {
		return this.statistics.entitiesStatistics.totalCount;
	}

	getRelationsCount() {
		return this.statistics.entitiesStatistics.relationsCount;
	}

	getEntitiesOfType(type: any, func: (entity: any) => boolean) {
		const entitiesNames = this.details
			.filter((entity: any) => entity.type === type)
			.filter((entity: any) => func(entity))
			.map((entity: any) => entity.name);

		const entities = this.statistics.entitiesStatistics.statistics.filter((item: any) => {
			return entitiesNames.includes(item.name);
		});

		if (entities.length === 0) {
			return [];
		}

		const names: any = this.getEntityNameToPluralNameMap();
		const sizes: Array<number> = [];

		entities.sort((left: any, right: any) => {
			sizes.push(left.count);
			sizes.push(right.count);
			return left.count < right.count ? 1 : (left.count === right.count ? 0 : -1);
		});

		if (sizes.length === 0) {
			sizes.push(entities[0].count);
		}

		const min: number = Math.min.apply(null, sizes);
		const max: number = Math.max.apply(null, sizes);

		return entities.map((entity: any) => {
			return {
				text: entity.count + ' ' + names[entity.name],
				weight: entity.count,
				fontSize: this.calculateFontSize(min, entity.count, max)
			};
		});
	}

	calculateFontSize(min: number, current: number, max: number): number {
		const diff: number = this.fontSizeMax - this.fontSizeMin;
		const diff2: number = (current - min) / (max - min);
		if (min === current && max === current) {
			return this.fontSizeMax;
		}
		return diff * diff2 + this.fontSizeMin;
	}

	getEntityNameToPluralNameMap(): any {
		const names: any = {};

		this.details.forEach((detail: any) => {
			names[detail.name] = detail.pluralName;
		});

		return names;
	}

	hasDataVersion() {
		return this.dataVersion != null && this.dataVersion.length;
	}

	getDataVersion() {
		if (!this.hasDataVersion()) {
			return ' at unknown time';
		}
		const dataVersionParts = this.dataVersion.split('-');
		const year = dataVersionParts[0];
		const month = this.getMonth(Number.parseInt(dataVersionParts[1]));
		return 'in ' + month + ' ' + year;
	}

	getMonth(number: number) {
		switch (number) {
			case 1: return 'January';
			case 2: return 'February';
			case 3: return 'March';
			case 4: return 'April';
			case 5: return 'May';
			case 6: return 'June';
			case 7: return 'July';
			case 8: return 'August';
			case 9: return 'September';
			case 10: return 'October';
			case 11: return 'November';
			case 12: return 'December';
		}
		return '';
	}

}
