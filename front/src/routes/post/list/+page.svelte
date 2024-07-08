<script lang="ts">
	import type { components, paths } from '$lib/types/api/v1/schema';
	import createClient from 'openapi-fetch';

	const client = createClient<paths>({
		baseUrl: import.meta.env.VITE_CORE_API_BASE_URL,
		credentials: 'include'
	});

	let posts: components['schemas']['PostDto'][] = $state([]);

	$effect(() => {
		(async () => {
			const { data, error } = await client.GET('/api/v1/posts', {});
			if (data) {
				posts = data.data.items;
			}
		})();
	});
</script>

<div>
	<h1>Posts</h1>
    
	<ul>
		{#each posts as post}
			<li>
				<a href="/post/{post.id}">{post.id}. {post.title}</a>
			</li>
		{/each}
	</ul>
</div>