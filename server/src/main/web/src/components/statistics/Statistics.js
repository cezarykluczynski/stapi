import React, { Component } from 'react';
import './Statistics.css';
import { EntityStatisticsCloud } from './EntityStatisticsCloud.js';
import { EntityHitsGrid } from './EntityHitsGrid.js';

export class Statistics extends Component {

	render() {
		return (
			<div className='statistics container content'>
				<div className="row">
					<div className="col-md-6">
						<EntityStatisticsCloud />
					</div>
					<div className="col-md-6">
						<EntityHitsGrid />
					</div>
				</div>
			</div>
		);
	}

}
