<script lang="ts">
	import rq from '$lib/rq/rq.svelte';
	
	async function submitLoginForm(this: HTMLFormElement) {
		const form: HTMLFormElement = this;
		form.username.value = form.username.value.trim();
		if (form.username.value.length === 0) {
			rq.msgError('Username is required');
			form.username.focus();
			return;
		}
		form.password.value = form.password.value.trim();
		if (form.password.value.length === 0) {
			rq.msgError('Password is required');
			form.password.focus();
			return;
		}
		const { data, error } = await rq.apiEndPoints().POST('/api/v1/members/login', {
			body: {
				username: form.username.value,
				password: form.password.value
			}
		});
		if (data) {
			rq.msgInfo(data.msg);
			rq.setLogined(data.data.item);
			rq.goto('/');
		} else if (error) {
			rq.msgError(error.msg);
		}
	}
</script>

<form on:submit|preventDefault={submitLoginForm}>
	<input type="text" name="username" placeholder="Username" />
	<input type="password" name="password" placeholder="Password" />
	<button type="submit">Login</button>
	<a href={rq.getKakaoLoginUrl()}>카카오 로그인</a>
</form>