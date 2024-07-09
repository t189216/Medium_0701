<script lang="ts">
	import rq from '$lib/rq/rq.svelte';
	import type { components } from '$lib/types/api/v1/schema';

	let posts: components['schemas']['PostListItemDto'][] = $state([]);
    
	rq.effect(async () => {
		const { data } = await rq.apiEndPoints().GET('/api/v1/posts/mine');
		if (data) {
			posts = data.data.items;
		}
	});
</script>

<div>
	<h1>My Posts</h1>

	<ul>
		{#each posts as post}
			<li>
				<a href="/post/{post.id}">{post.id}. {post.title}</a>
			</li>
		{/each}
	</ul>
</div>