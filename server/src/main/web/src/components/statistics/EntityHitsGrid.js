import React from 'react';
import './EntityHitsGrid.css';
import ReactDataGrid from 'react-data-grid';
import { StatisticsComponent } from './StatisticsComponent.js';

export class EntityHitsGrid extends StatisticsComponent {

	constructor() {
		super();
		this.rowGetter = this.rowGetter.bind(this);
		this.sorter = this.sorter.bind(this);
	}

	render() {
		return (
			<div className='entity-hits-grid'>
				{this.hasStatistics() ? this.renderStatistics() : ''}
			</div>
		);
	}

	updateStatisticsFromRestApi() {
		super.updateStatisticsFromRestApi();
		this.originalSort = this.state.statistics.hitsStatistics.statistics.slice(0);
	}

	renderStatistics() {
		this.names = this.getEntityNameToPluralNameMap();
		return (
			<div>
				<h4>A total of <strong>{this.state.statistics.hitsStatistics.totalCount}</strong> calls to API was made!</h4>
				<hr />
				<ReactDataGrid columns={this.getColumns()} rowGetter={this.rowGetter} rowsCount={this.getRowsCount()}
				 		onGridSort={this.sorter} minHeight={400} />
			</div>
		)
	}

	getColumns() {
		return [
			{
				key: 'name',
				name: 'Resource name',
				sortable: true
			},
			{
				key: 'count',
				name: 'Total number of hits',
				sortable: true
			}
		]
	}

	rowGetter(i) {
		return this.state && this.state.statistics ? this.toRow(this.state.statistics.hitsStatistics.statistics[i]) : null;
	}

	toRow(entry) {
		return {
			name: this.names[entry.name],
			count: entry.count
		};
	}

	sorter(sortColumn, sortDirection) {
		const comparer = (a, b) => {
			if (sortDirection === 'ASC') {
				return (a[sortColumn] > b[sortColumn]) ? 1 : -1;
			} else if (sortDirection === 'DESC') {
				return (a[sortColumn] < b[sortColumn]) ? 1 : -1;
			}
		}

		const statistics = sortDirection === 'NONE' ? this.originalSort.slice(0)
				: this.state.statistics.hitsStatistics.statistics.slice(0).sort(comparer);

		console.log(this.originalSort);

		this.setState({
			statistics: {
				hitsStatistics: {
					statistics: statistics
				}
			}
		});
	}

	getRowsCount() {
		return this.state && this.state.statistics ? this.state.statistics.hitsStatistics.statistics.length : 0;
	}

}
