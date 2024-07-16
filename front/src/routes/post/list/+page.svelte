<script lang="ts">
	import rq from '$lib/rq/rq.svelte';
</script>

<div>
	<h1>Posts</h1>

	<ul>
		{#await rq.apiEndPoints().GET('/api/v1/posts')}
			<li>Loading...</li>
		{:then { data }}
			{#each data!.data.items as post}
				<li>
					<a href="/post/{post.id}">{post.id}. {post.title}</a>
				</li>
			{/each}
		{/await}
	</ul>
</div>