<script lang="ts">
	import rq from '$lib/rq/rq.svelte';
	import '$lib/app.css';
	const { children } = $props();
	import { untrack } from 'svelte';
	rq.effect(async () => {
		untrack(() => {
			rq.initAuth();
		});
	});
</script>

<header class="flex">
	<ul class="flex gap-2">
		<li>
			<a href="/" class="btn btn-link">홈</a>
		</li>
		<li>
			<a href="/post/list" class="btn btn-link">글</a>
		</li>
		{#if rq.isLogout()}
			<li>
				<a href="/member/login" class="btn btn-link">로그인</a>
			</li>
		{/if}
		{#if rq.isLogin()}
			<li>
				<a href="/post/myList" class="btn btn-link">내 글</a>
			</li>
			<li>
				<button on:click={() => rq.logout()} class="btn btn-link">로그아웃</button>
			</li>
		{/if}
	</ul>

	<div class="flex-1"></div>

	<div>
		<a href="/member/me" class="btn btn-link">
			{rq.member.username}
		</a>
	</div>
</header>

<main>
	{@render children()}
</main>