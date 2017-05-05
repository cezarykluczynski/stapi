import React, { Component } from 'react';
import './ApiBrowserResponse.css';
import { SearchStateService } from '../../service/SearchStateService.js';
import { RestApi } from '../../service/rest/RestApi.js';

export class ApiBrowserResponse extends Component {

	constructor() {
		super();
		this.state = {
			lastUpdateWasError: false,
			lastUpdateType: 'search'
		};
		var self = this;
		this.restApi = RestApi.getInstance();
		SearchStateService.subscribe((state, error) => {
			error ? self.handleInitialError(state) : self.updateFromSearch(state);
		});
		this.restApi.onLimitUpdate(limits => {
			self.setState({
				limits: limits
			});
		})
	}

	render() {
		return (
			<div>
				{this.hasMetadata() ? this.renderMetadata() : ''}
				{this.hasError() ? this.renderError() : ''}
				{this.hasContent() ? this.renderContent() : ''}
			</div>
		);
	}

	updateFromSearch(state) {
		this.setState({
			lastUpdateType: 'search'
		});
		this.handleRequest(this.restApi.search(state.symbol, state.phrase));
	}

	updateFromEntityPick(uid) {
		this.setState({
			lastUpdateType: 'get'
		});
		this.handleRequest(this.restApi.get(this.extractSymbol(uid), uid));
	}

	hasMetadata() {
		return !this.state.lastUpdateWasError && this.state.response && this.state.response.content;
	}

	hasError() {
		return this.state.lastUpdateWasError;
	}

	hasContent() {
		return !!this.state.response;
	}

	hasLimits() {
		return this.state.limits && this.state.limits.total;
	}

	handleRequest(promise) {
		promise.then(response => {
			this.setState({
				lastUpdateWasError: false,
				response: response
			});
		}).catch(error => {
			this.state.lastUpdateWasError = true;
			this.state.response = JSON.parse(error.response);
			this.forceUpdate();
		});
	}

	handleInitialError(error) {
		this.setState({
			lastUpdateWasError: true,
			response: error
		});
	}

	renderMetadata() {
		if (!this.state.response) {
			return '';
		}

		var page = this.state.response.page;

		if (this.state.lastUpdateType === 'search') {
			return <div className={"api-browser__response-data col-md-12 alert " + (page.totalElements === 0 ? "alert-warning" : "alert-info")}><span>
				{this.hasLimits() ? <span className="api-browser__limits"><span className="api-browser__remaining">{this.state.limits.remaining}</span> requests of {this.state.limits.total} left</span> : ''}
				{this.state.phrase ? <span>Searched for phrase <span className="api-browser__phrase-result">{SearchStateService.getState().phrase}</span>.</span> : ''}
				There were {page.totalElements} result.
				{page.totalElements > page.numberOfElements && <span> First 50 are shown.</span>}
			</span></div>;
		}
	}

	renderError() {
		return <div className="api-browser__response_data col-md-12 alert alert-danger"><span>
			There was an error. Details bellow.
		</span></div>;
	}

	renderContent() {
		var items = [];

		if (!this.state.response) {
			return items;
		}

		var content = this.state.response.content;

		if (this.state.lastUpdateWasError) {
			items.push(<li key="error">{this.doRender(this.state.response)}</li>);
			return this.wrapResponse(items);
		}

		if (!content) {
			return items;
		}

		var keys = Object.keys(content);

		if (!keys.length) {
			return items;
		}

		var level = 0;

		if (Array.isArray(content)) {
			for (let i = 0; i < content.length; i++) {
				items.push(this.doRender(content[i], level));

				if (i + 1 < content.length && content.length > 1) {
					items.push(<li key={i} className='api-browser__separator'></li>);
				}
			}
		} else {
			items.push(this.doRender(content, level));
		}

		return this.wrapResponse(items);
	}

	wrapResponse(items) {
		return <div className="api-browser__response col-md-12">{items}</div>;
	}

	doRender(content, level) {
		let items = [];
		for (let i in content) {
			if (Array.isArray(content[i])) {
				var childItems = this.doRender(content[i], level + 1);
				for (let j = 0; j < childItems.length; j++) {
					items.push(childItems[j]);
				}
				continue;
			}

			if (typeof content[i] === 'object') {
				if (content[i] === null) {
					continue;
				}

				var objectChildItems = this.doRender(content[i], level + 1);
				items.push(<li><span key={i} className="api-browser__label">{i}:</span><ul>{objectChildItems}</ul></li>);
			}

			let value = this.renderValue(content[i], i);

			if (value === undefined) {
				continue;
			}

			items.push(<li key={i}><span className="api-browser__label">{i}:</span> {value}</li>);
		}

		return items;
	}

	renderValue(value, key) {
		if (Array.isArray(value) && !value.length || value === null || key === 'uid' || typeof value === 'object') {
			return;
		}

		if (typeof value === 'boolean') {
			return value ? 'yes' : undefined;
		}

		return value;
	}

}
