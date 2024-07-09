<script lang="ts">
	import { page } from '$app/stores';
	import rq from '$lib/rq/rq.svelte';
	import type { components } from '$lib/types/api/v1/schema';

	let post: components['schemas']['PostDto'] | null = $state(null);

	rq.effect(async () => {
		const id = parseInt($page.params.id);
        
		const { data, error } = await rq.apiEndPoints().GET(`/api/v1/posts/{id}`, {
			params: {
				path: {
					id
				}
			}
		});
		if (data) {
			post = data.data.item;
		}
	});
</script>

{#if post}
	<h1>{post.title}</h1>
	<p>{post.body}</p>
{/if}