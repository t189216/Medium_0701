<script lang="ts">
	import rq from '$lib/rq/rq.svelte';
</script>

<div>
	username: {rq.member.username}
	<br />
	createDate: {rq.member.createDate}
</div>