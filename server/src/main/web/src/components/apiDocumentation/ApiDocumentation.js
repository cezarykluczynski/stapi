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
				<div className='alert alert-success'>{this.getInfo()}</div>
				<div className='row'>
					<div className="btn-group btn-group-justified">
						<div className={"btn btn-default " + (this.getRestClass())} onClick={this.selectRest}>
							REST documentation (<a href={this.getRestSpecsZipLink()} onClick={(e) => this.preventDefault(e)}>as ZIP</a>)</div>
						<div className={"btn btn-default " + (this.getSoapClass())} onClick={this.selectSoap}>
							SOAP documentation (<a href={this.getSoapContractsZipLink()} onClick={(e) => this.preventDefault(e)}>as ZIP</a>)</div>
					</div>
				</div>
				<div className='row api-documentation__files-contents'>
					<div className={this.getFilesListClass()} id="api-documentation__code-preview">
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
		const codePrevieScrollTop = $('#api-documentation__code-preview').offset().top;
		const navbarHeight = $('.navbar').outerHeight();
		if (window.scrollY > codePrevieScrollTop - navbarHeight) {
			window.scrollTo(window.scrollX, codePrevieScrollTop - navbarHeight);
		}
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
		return <span>
					<ul>
						<li>This is API documentation, both REST and SOAP.</li>
						<li>
							Please be advised that as long as STAPI stays in alpha version, SOAP contracts and Swagger specifications can and will change.
							New versions of STAPI will be deployed without warning, breaking backward compatibility.
						</li>
						<li>
							You can also <strong>download documentation</strong>: <a href={this.getRestSpecsZipLink()}>REST</a> and <a
								href={this.getSoapContractsZipLink()}>SOAP</a>.
						</li>
						<li>
							It is possible to generate clients from both ZIP packages.
						</li>
						<li><a href="https://github.com/swagger-api/swagger-codegen">Swagger Code Generator</a> will generate clients for REST Swagger specs.
						</li>
						<li>
							Generating clients from SOAP depends on the target language. Google around for your tool.
						</li>
						<li>
							In case of any	problems with Swagger specs of SOAP contracts, please <a
								href="https://github.com/cezarykluczynski/stapi/issues">report a bug</a>.
						</li>
						<li>If you plan on mantaing STAPI client in language other than Java, please <a
							href="https://github.com/cezarykluczynski/stapi/issues">open an issue</a> and let others now.
							Client will be listed on STAPI website in the future.
						</li>
					</ul>
				</span>
	}

	getRestSpecsZipLink() {
		return this.getLinkPrefix() + '/api/v1/rest/common/download/zip/rest';
	}

	getSoapContractsZipLink() {
		return this.getLinkPrefix() + '/api/v1/rest/common/download/zip/soap';
	}

	preventDefault(event) {
		event.stopPropagation();
		event.nativeEvent.stopImmediatePropagation();
	}

	getLinkPrefix() {
		return location.href.includes('localhost:3000') ? 'http://localhost:8686' : '';
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
