import apiClient from "./API";
import type { LoginRequest, LoginResponse, ResetPasswordRequest } from "../types/ApiTypes";
import StorageService from "@/util/StorageService";
import { useAuthStore } from "@/stores/authStore.ts";

const AuthService = {
	login: async (credentials: LoginRequest): Promise<LoginResponse> => {
		const response = await apiClient.post<LoginResponse>(
			"/public/api/v1/authentication/log-in",
			credentials
		);
		StorageService.saveTokens(
			response.data.accessToken,
			response.data.refreshToken
		);
		useAuthStore().setAuthorized(true)
		return response.data;
	},

	register: async (credentials: LoginRequest): Promise<LoginResponse> => {
		console.log("Register", credentials)
		const response = await apiClient.post<LoginResponse>(
			"/public/api/v1/registration",
			credentials
		);
		console.log("Register response", response)
		StorageService.saveTokens(
			response.data.accessToken,
			response.data.refreshToken
		);
		console.log("Tokens saved")
		useAuthStore().setAuthorized(true)
		console.log("Authorized set")
		return response.data;
	},

	ressetPassword: async (credentials: ResetPasswordRequest): Promise<void> => {
		await apiClient.post<LoginResponse>(
			"/public/api/v1/authentication/reset-password",
			credentials
		);
		return
	},

	logout: () => {
		StorageService.clearTokens();
	},
};

export default AuthService;
