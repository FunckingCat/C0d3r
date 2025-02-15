import apiClient from "./API";
import type { User } from "@/types/ApiTypes";
import StorageService from "@/util/StorageService";

const userApi = {
	getCurrentUser: async (): Promise<User> => {
		const response = await apiClient.get("/api/v1/users/user");
		return response.data;
	},
};

export default userApi;
