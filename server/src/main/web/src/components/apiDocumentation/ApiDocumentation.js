import React, { Component } from 'react';
import './ApiDocumentation.css';
import { RestApi } from '../../service/rest/RestApi.js';

export class ApiDocumentation extends Component {

	constructor() {
		super();

		this.restApi = RestApi.getInstance();
		this.restApi.loadDocumentation().then(response => {
			this.documentation = response;
			this.setState({
				selectedDocumentationType: 'SOAP'
			});
			return this.documentation;
		});
		this.selectRest = this.selectRest.bind(this);
		this.selectSoap = this.selectSoap.bind(this);
	}

	render() {
		return (
			<div className='api-documentation'>
				<div className='row'>
					<div className="btn-group btn-group-justified">
						<a className={"btn btn-default " + (this.getRestClass())} onClick={this.selectRest}>REST documentation</a>
						<a className={"btn btn-default " + (this.getSoapClass())} onClick={this.selectSoap}>SOAP documentation</a>
					</div>
				</div>
				<div className='row'>
					<div className='col-md-3'>
						{this.renderFileList()}
					</div>
				</div>
			</div>
		);
	}

	renderFileList() {
		return '';
	}

	selectRest() {
		this.setState({
			selectedDocumentationType: 'REST'
		});
	}

	selectSoap() {
		this.setState({
			selectedDocumentationType: 'SOAP'
		});
	}

	getRestClass() {
		return this.state && this.state.selectedDocumentationType === 'REST' ? 'btn-primary' : '';
	}

	getSoapClass() {
		return this.state && this.state.selectedDocumentationType === 'SOAP' ? 'btn-primary' : '';
	}

}
