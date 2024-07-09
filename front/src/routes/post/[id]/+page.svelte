<script lang="ts">
	import { page } from '$app/stores';

	const post = $page.data.post;
</script>

<svelte:head>
	<title>글 {post.title}</title>
</svelte:head>

<div>
	<h1>{post.title}</h1>

	<p>{post.body}</p>
</div>