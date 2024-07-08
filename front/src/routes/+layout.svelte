<script lang="ts">
	import '$lib/app.css';
	const { children } = $props();
</script>

<main>
	{@render children()}
</main>