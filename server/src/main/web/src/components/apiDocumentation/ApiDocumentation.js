import React, { Component } from 'react';
import './ApiDocumentation.css';
import { RestApi } from '../../service/rest/RestApi.js';
import { Info } from '../info/Info.js';

export class ApiDocumentation extends Component {

	constructor() {
		super();

		this.restApi = RestApi.getInstance();
		this.restApi.loadDocumentation().then(response => {
			this.documentation = response;
			this.selectRest();
			return this.documentation;
		});
		this.selectRest = this.selectRest.bind(this);
		this.selectSoap = this.selectSoap.bind(this);
	}

	componentDidUpdate() {
		/* eslint no-undef:0 */
		jQuery('pre code').each(function(i, block) {
			hljs.highlightBlock(block);
		});
	}

	render() {
		return (
			<div className='api-documentation content'>
				<Info message={this.getInfo()}/>
				<div className='row'>
					<div className="btn-group btn-group-justified">
						<a className={"btn btn-default " + (this.getRestClass())} onClick={this.selectRest}>REST documentation</a>
						<a className={"btn btn-default " + (this.getSoapClass())} onClick={this.selectSoap}>SOAP documentation</a>
					</div>
				</div>
				<div className='row api-documentation__files-contents'>
					<div className={this.getFilesListClass()}>
						<div className='list-group table-of-contents'>
							{this.renderFileList()}
						</div>
					</div>
					<div className={this.getFileContentClass()}>
						{this.renderSelectedFileContents()}
					</div>
				</div>
			</div>
		);
	}

	renderFileList() {
		if (!this.state) {
			return '';
		}

		var files = this.state.selectedDocumentationType === 'REST' ? this.documentation.restDocuments : this.documentation.soapDocuments;
		var nodes = [];
		for (let i = 0; i < files.length; i++) {
			nodes.push(<a className='list-group-item api-documentation__listed-file' onClick={() => this.selectFile(i)}>{files[i].path}</a>);
		}
		return nodes;
	}

	getFilesListClass() {
		return this.isSoapDocumentation() ? 'col-md-3' : 'col-md-5';
	}

	getFileContentClass() {
		return this.isSoapDocumentation() ? 'col-md-9' : 'col-md-7';
	}

	renderSelectedFileContents() {
		if (this.isRestDocumentation()) {
			return <pre><code className='yaml' dangerouslySetInnerHTML={{__html: this.getSelectedFileContents()}}></code></pre>;
		} else if (this.isSoapDocumentation()) {
			return <pre><code className='xml' dangerouslySetInnerHTML={{__html: this.getSelectedFileContents()}}></code></pre>;
		}
	}

	selectRest() {
		this.setState({
			selectedDocumentationType: 'REST',
			selectedFilesIndex: 0
		});
	}

	selectFile(index) {
		this.setState({
			selectedFilesIndex: index
		});
	}

	selectSoap() {
		this.setState({
			selectedDocumentationType: 'SOAP',
			selectedFilesIndex: 0
		});
	}

	getInfo() {
		return 'This is API documentation, both REST and SOAP. Please be advised that as long as STAPI stays in alpha version, ' +
				'SOAP contracts and Swagger specifications can and will change. New versions of STAPI will be deployed without warning, ' +
				'breaking backward compatibility.'
	}

	getRestClass() {
		return this.isRestDocumentation() ? 'btn-primary' : '';
	}

	getSoapClass() {
		return this.isSoapDocumentation() ? 'btn-primary' : '';
	}

	isRestDocumentation() {
		return this.state && this.state.selectedDocumentationType === 'REST';
	}

	isSoapDocumentation() {
		return this.state && this.state.selectedDocumentationType === 'SOAP'
	}

	getSelectedFileContents() {
		if (!this.state) {
			return '';
		}

		var documents = this.state.selectedDocumentationType === 'REST' ? this.documentation.restDocuments : this.documentation.soapDocuments;
		return documents[this.state.selectedFilesIndex].content;
	}

}
