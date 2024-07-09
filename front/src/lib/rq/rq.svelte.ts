import { goto } from '$app/navigation';

import type { components, paths } from '$lib/types/api/v1/schema';
import createClient from 'openapi-fetch';

import toastr from 'toastr';
import 'toastr/build/toastr.css';

class Rq {
	public member: components['schemas']['MemberDto'];
	private shouldLogoutPagePaths = ['/member/login', '/member/join'];
	private shouldLoginPagePaths = ['/post/myList'];

	constructor() {
		let id = $state(0);
		let username = $state('');
		let createDate = $state('');
		let modifyDate = $state('');
		let authorities: string[] = $state([]);

		this.member = {
			get id() {
				return id;
			},
			set id(value: number) {
				id = value;
			},
			get createDate() {
				return createDate;
			},
			set createDate(value: string) {
				createDate = value;
			},
			get modifyDate() {
				return modifyDate;
			},
			set modifyDate(value: string) {
				modifyDate = value;
			},
			get username() {
				return username;
			},
			set username(value: string) {
				username = value;
			},
			get authorities() {
				return authorities;
			},
			set authorities(value: string[]) {
				authorities = value;
			}
		};
	}

	public apiEndPoints() {
		return createClient<paths>({
			baseUrl: import.meta.env.VITE_CORE_API_BASE_URL,
			credentials: 'include'
		});
	}

	public msgInfo(message: string) {
		toastr.info(message);
	}

	public msgError(message: string) {
		toastr.error(message);
	}

	public goto(path: string) {
		goto(path);
	}

	public setLogined(member: components['schemas']['MemberDto']) {
		this.member.id = member.id;
		this.member.createDate = member.createDate;
		this.member.modifyDate = member.modifyDate;
		this.member.username = member.username;
		this.member.authorities = member.authorities;
	}

	public setLogout() {
		this.member.id = 0;
		this.member.createDate = '';
		this.member.modifyDate = '';
		this.member.username = '';
		this.member.authorities = [];
	}

	public isLogin() {
		return this.member.id !== 0;
	}

	public isLogout() {
		return !this.isLogin();
	}

	public async initAuth() {
		const { data } = await this.apiEndPoints().GET('/api/v1/members/me');

		if (data) {
			this.setLogined(data.data.item);
		}

		this.checkAuth();
	}

	public async logout() {
		const {} = await this.apiEndPoints().POST('/api/v1/members/logout');

		this.setLogout();

		this.goToMain();
	}

	public shouldLogoutPage() {
		return this.shouldLogoutPagePaths.includes(window.location.pathname);
	}

	public shouldLoginPage() {
		return this.shouldLoginPagePaths.includes(window.location.pathname);
	}

	public checkAuth() {
		if (this.isLogin()) {
			const needToGoMainPage = this.shouldLogoutPage();

			if (needToGoMainPage) {
				this.goToMain();
			}
		} else {
			const needToGoLoginPage = this.shouldLoginPage();

			if (needToGoLoginPage) {
				this.goToLoginPage();
			}
		}
	}

	public goToMain() {
		this.goto('/');
	}

	public goToLoginPage() {
		this.goto('/member/login');
	}

	public effect(fn: () => void) {
		$effect(() => {
			fn();
		});
	}
}

const rq = new Rq();

export default rq;