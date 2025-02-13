import apiClient from "./API";
import type { Job } from "../types/ApiTypes";
import StorageService from "@/util/StorageService";

const AuthService = {
	getAllJobs: async (): Promise<Job[]> => {
		const response = await apiClient.get("/api/v1/job");
		return response.data;
	},
};

export default AuthService;
