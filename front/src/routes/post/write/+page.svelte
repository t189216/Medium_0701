<script lang="ts">
	import { filterObjectKeys, getUrlParams, stripIndent } from '$lib/utils/common';

	import Editor from '@toast-ui/editor';
	import '@toast-ui/editor/dist/i18n/ko-kr';
	import '@toast-ui/editor/dist/toastui-editor.css';
    import '@toast-ui/editor/dist/theme/toastui-editor-dark.css';

	import 'prismjs/themes/prism.css';
	import '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight.css';
	import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight-all';

	import '@toast-ui/chart/dist/toastui-chart.css';
	import chart from '@toast-ui/editor-plugin-chart';

	import 'tui-color-picker/dist/tui-color-picker.css';
	import '@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css';
	import colorSyntax from '@toast-ui/editor-plugin-color-syntax';

	import '@toast-ui/editor-plugin-table-merged-cell/dist/toastui-editor-plugin-table-merged-cell.css';
	import tableMergedCell from '@toast-ui/editor-plugin-table-merged-cell';

	import uml from '@toast-ui/editor-plugin-uml';
	
	let div: HTMLDivElement;
    
	$effect(() => {
		const chartOptions = {
			minWidth: 100,
			maxWidth: 600,
			minHeight: 100,
			maxHeight: 300
		};

		const umlOptions = {
			rendererURL: 'http://www.plantuml.com/plantuml/svg/'
		};

		function configPlugin() {
			const toHTMLRenderers = {
				config(node: any) {
					return [
						{ type: 'openTag', tagName: 'div', outerNewLine: true },
						{ type: 'html', content: '' },
						{ type: 'closeTag', tagName: 'div', outerNewLine: true }
					];
				}
			};

			return { toHTMLRenderers };
		}

		function hidePlugin() {
			const toHTMLRenderers = {
				hide(node: any) {
					return [
						{ type: 'openTag', tagName: 'div', outerNewLine: true },
						{ type: 'html', content: '' },
						{ type: 'closeTag', tagName: 'div', outerNewLine: true }
					];
				}
			};

			return { toHTMLRenderers };
		}

		function youtubePlugin() {
			const toHTMLRenderers = {
				youtube(node: any) {
					const html = renderYoutube(node.literal);

					return [
						{ type: 'openTag', tagName: 'div', outerNewLine: true },
						{ type: 'html', content: html },
						{ type: 'closeTag', tagName: 'div', outerNewLine: true }
					];
				}
			};

			function renderYoutube(url: string) {
				url = url.replace('https://www.youtube.com/watch?v=', '');
				url = url.replace('http://www.youtube.com/watch?v=', '');
				url = url.replace('www.youtube.com/watch?v=', '');
				url = url.replace('youtube.com/watch?v=', '');
				url = url.replace('https://youtu.be/', '');
				url = url.replace('http://youtu.be/', '');
				url = url.replace('youtu.be/', '');

				let urlParams = getUrlParams(url);

				let width = '100%';

				let height = '100%';

				let maxWidth = '500';

				if (!urlParams['max-width'] && urlParams['ratio'] == '9/16') {
					urlParams['max-width'] = '300';
				}

				if (urlParams['max-width']) {
					maxWidth = urlParams['max-width'];
				}

				let ratio = 'aspect-[16/9]';

				let marginLeft = 'auto';

				if (urlParams['margin-left']) {
					marginLeft = urlParams['margin-left'];
				}
				
				let marginRight = 'auto';

				if (urlParams['margin-right']) {
					marginRight = urlParams['margin-right'];
				}

				let youtubeId = url;

				if (youtubeId.indexOf('?') !== -1) {
					let pos = url.indexOf('?');

					youtubeId = youtubeId.substring(0, pos);
				}

				return (
					'<div style="max-width:' +
					maxWidth +
					'px; margin-left:' +
					marginLeft +
					'; margin-right:' +
					marginRight +
					';" class="' +
					ratio +
					' relative"><iframe class="absolute top-0 left-0 w-full" width="' +
					width +
					'" height="' +
					height +
					'" src="https://www.youtube.com/embed/' +
					youtubeId +
					'" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe></div>'
				);
			}

			return { toHTMLRenderers };
		}

		function codepenPlugin() {
			const toHTMLRenderers = {
				codepen(node: any) {
					const html = renderCodepen(node.literal);
					return [
						{ type: 'openTag', tagName: 'div', outerNewLine: true },
						{ type: 'html', content: html },
						{ type: 'closeTag', tagName: 'div', outerNewLine: true }
					];
				}
			};
			
			function renderCodepen(url: string) {
				const urlParams = getUrlParams(url);

				let height = '400';

				if (urlParams.height) {
					height = urlParams.height;
				}

				let width = '100%';

				if (urlParams.width) {
					width = urlParams.width;
				}

				if (!width.includes('px') && !width.includes('%')) {
					width += 'px';
				}

				let iframeUri = url;

				if (iframeUri.indexOf('#') !== -1) {
					let pos = iframeUri.indexOf('#');
					iframeUri = iframeUri.substring(0, pos);
				}

				return (
					'<iframe height="' +
					height +
					'" style="width: ' +
					width +
					';" title="" src="' +
					iframeUri +
					'" allowtransparency="true" allowfullscreen="true"></iframe>'
				);
			}

			return { toHTMLRenderers };
		}

		const editor = new Editor({
			el: div,
			height: 'calc(100dvh - 48px)',
			initialEditType: 'markdown',
			previewStyle: 'tab',
            theme: 'dark',
			language: 'ko-KR',
			useCommandShortcut: false,
			initialValue: stripIndent(`
			$$config
			title: 제목
			open: true
			tags: #TAG1 #TAG2
			$$
			`).trim(),
			placeholder: stripIndent(`
			$$config
			title: 제목
			open: true
			tags: #TAG1 #TAG2
			$$
			`).trim(),
			plugins: [
				codeSyntaxHighlight,
				[chart, chartOptions],
				colorSyntax,
				tableMergedCell,
				[uml, umlOptions],
				configPlugin,
				hidePlugin,
				youtubePlugin,
				codepenPlugin
			],
			customHTMLRenderer: {
				heading(node: any, { entering, getChildrenText }: any) {
					return {
						type: entering ? 'openTag' : 'closeTag',
						tagName: `h${node.level}`,
						attributes: {
							id: getChildrenText(node).trim()
						}
					};
				},
				htmlBlock: {
					iframe(node: any) {
						const newAttrs = filterObjectKeys(node.attrs, [
							'src',
							'width',
							'height',
							'allow',
							'allowfullscreen',
							'frameborder',
							'scrolling'
						]);

						return [
							{
								type: 'openTag',
								tagName: 'iframe',
								outerNewLine: true,
								attributes: newAttrs
							},
							{ type: 'html', content: node.childrenHTML },
							{ type: 'closeTag', tagName: 'iframe', outerNewLine: false }
						];
					}
				}
			}
		});

		return () => {
			editor.destroy();
		};
	});
</script>

<div bind:this={div}></div>