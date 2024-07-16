<script lang="ts">
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

		const editor = new Editor({
			el: div,
			height: 'calc(100dvh - 48px)',
			initialEditType: 'markdown',
			previewStyle: 'vertical',
            theme: 'dark',
			language: 'ko-KR',
			plugins: [
				codeSyntaxHighlight,
				[chart, chartOptions],
				colorSyntax,
				tableMergedCell,
				[uml, umlOptions]
			]
		});

		return () => {
			editor.destroy();
		};
	});
</script>

<div bind:this={div}></div>