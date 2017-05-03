import React, { Component } from 'react';
import './ApiBrowser.css';
import { SearchStateService } from '../../service/SearchStateService.js';
import { RestApi } from '../../service/rest/RestApi.js';

export class ApiBrowser extends Component {

	constructor() {
		super();
		var self = this;
		SearchStateService.subscribe((state) => {
			self.update(state);
		});
		this.restApi = RestApi.getInstance();
	}

	render() {
		return (
			<div className='api-browser'>
				ApiBrowser
			</div>
		);
	}

	update(state) {
		this.restApi.search(state.symbol, state.phrase).then(response => {
			console.log(response);
		});
	}

}
