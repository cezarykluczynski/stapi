import React, { Component } from 'react';
import './ApiBrowser.css';
import { SearchStateService } from '../../service/SearchStateService.js';
import { RestApi } from '../../service/rest/RestApi.js';
import { ApiBrowserResponse } from './ApiBrowserResponse.js';
import { Info } from '../info/Info.js';

export class ApiBrowser extends Component {

	constructor() {
		super();
		this.state = {};
		var self = this;
		this.restApi = RestApi.getInstance();
		this.restApi.whenReady(() => {
			self.refreshDetailsFromRestApi();
			self.forceUpdate();
		});
		this.restApi.onError(error => {
			SearchStateService.markInvalid(error);
		});
		this.search = this.search.bind(this);
		this.changeSelection = this.changeSelection.bind(this);
		this.updatePhrase = this.updatePhrase.bind(this);
	}

	componentDidMount() {
		if (this.restApi.hasDetails()) {
			this.refreshDetailsFromRestApi();
		}
	}

	refreshDetailsFromRestApi() {
		const details = this.restApi.getDetails();
		this.setState({
			details: details,
			symbol: details[0].symbol
		});
		SearchStateService.markValid();
	}

	render() {
		return (
			<div className='api-browser container content'>
				<Info message={this.getInfo()}/>
				<div className="row">
					<form role="search" className="form-horizontal" onSubmit={this.search}>
						<div className="col-md-5">
							<input type="text" className="form-control" placeholder="Search the API"
								onChange={this.updatePhrase} disabled={this.isDisabled()} />
						</div>
						<div className="col-md-5">
							<select className="form-control" onChange={this.changeSelection} disabled={this.isDisabled()}>
								{this.createOptions()}
							</select>
						</div>
						<div className="col-md-2">
							<button type="submit" className="btn btn-default" disabled={this.isDisabled()}>Submit</button>
						</div>
					</form>
				</div>
				<div className="row">
					<ApiBrowserResponse />
				</div>
			</div>
		);
	}

	createOptions() {
		let items = [];
		if (!this.state.details) {
			return items;
		}

		for (let i = 0; i < this.state.details.length; i++) {
			let url = this.state.details[i];
			items.push(<option key={url.symbol} value={url.symbol}>{url.name}</option>);
		}
		return items;
	}

	changeSelection(event) {
		this.setState({
			symbol: event.target.value
		});

		if (this.state.phrase) {
			this.search();
		}
	}

	updatePhrase(event) {
		this.setState({
			phrase: event.target.value
		});
	}

	search(event) {
		event && event.preventDefault();

		SearchStateService.push({
			phrase: this.state.phrase,
			symbol: this.state.symbol
		});
	}

	getInfo() {
		return 'This is API Browser. Existing API entities can be queried here by name or title. <br>' +
				'Pick resource to search in, type in full name or title, or part of name or title, then press enter.';
	}

	isDisabled() {
		return SearchStateService.isInvalid();
	}

}
