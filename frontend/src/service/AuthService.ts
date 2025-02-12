import apiClient from "./API";
import type { LoginRequest, LoginResponse } from "../types/ApiTypes";
import StorageService from "@/util/StorageService";

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
		return response.data;
	},

	register: async (credentials: LoginRequest): Promise<LoginResponse> => {
		const response = await apiClient.post<LoginResponse>(
			"/public/api/v1/registration",
			credentials
		);
		StorageService.saveTokens(
			response.data.accessToken,
			response.data.refreshToken
		);

		return response.data;
	},

	logout: () => {
		StorageService.clearTokens();
	},
};

export default AuthService;
