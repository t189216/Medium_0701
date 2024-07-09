import type { PageLoad } from './$types';
import rq from '$lib/rq/rq.svelte';

export const load: PageLoad = async ({ params }) => {
	const { data, error } = await rq.apiEndPoints().GET(`/api/v1/posts/{id}`, {
		params: {
			path: {
				id: parseInt(params.id)
			}
		}
	});

	return {
		post: data!.data.item
	};
};