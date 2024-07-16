<script lang="ts">
	import Editor from '@toast-ui/editor';
	import '@toast-ui/editor/dist/toastui-editor.css';
    import '@toast-ui/editor/dist/theme/toastui-editor-dark.css';

	let div: HTMLDivElement;
    
	$effect(() => {
		const editor = new Editor({
			el: div,
			height: 'calc(100dvh - 48px)',
			initialEditType: 'markdown',
			previewStyle: 'vertical',
            theme: 'dark'
		});

		return () => {
			editor.destroy();
		};
	});
</script>

<div bind:this={div}></div>