import apiClient from "./API";
import type { Job, CreateJobRequest } from "../types/ApiTypes";

const jobApi = {
	getAllJobs: async (): Promise<Job[]> => {
		const response = await apiClient.get("/api/v1/job");
		return response.data;
	},
	getJobById: async (jobId: number): Promise<Job> => {
		const response = await apiClient.get(`/api/v1/job/${jobId}`)
		return response.data;
	},
	createJob: async (createJobRequest: CreateJobRequest): Promise<Job> => {
		console.log("API call: Create job", createJobRequest)
		const response = await apiClient.post("/api/v1/job", createJobRequest);
		console.log("Call response", response)
		return response.data;
	},
	rerunJob: async (jobId: number): Promise<void> => {
		const response = await apiClient.post(`/api/v1/job/rerun/${jobId}`);
		return response.data;
	},
	delete: async (jobId: number): Promise<void> => {
		const response = await apiClient.post(`/api/v1/job/delete/${jobId}`);
		return response.data;
	},
	cancelJob: async (jobId: number): Promise<void> => {
		const response = await apiClient.post(`/api/v1/job/cancel/${jobId}`);
		return response.data;
	},
	triggerWebhook: async (jobId: number): Promise<void> => {
		const response = await apiClient.post(`/api/v1/job/webhook/run/${jobId}`);
		return response.data;
	},
};

export default jobApi;
