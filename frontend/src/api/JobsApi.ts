import apiClient from "./API";
import type { Job, CreateJobRequest } from "../types/ApiTypes";
import { useAlertStore } from '@/stores/alertStore';

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
		const alertStore = useAlertStore()
		alertStore.addAlert({
			level: 'success',
			title: `Job crated`,
		})
		return response.data;
	},
	rerunJob: async (jobId: number): Promise<void> => {
		const response = await apiClient.post(`/api/v1/job/rerun/${jobId}`);
		const alertStore = useAlertStore()
		alertStore.addAlert({
			level: 'info',
			title: `Job ${jobId} re-run pending`,
		})
		return response.data;
	},
	delete: async (jobId: number): Promise<void> => {
		const response = await apiClient.post(`/api/v1/job/delete/${jobId}`);
		const alertStore = useAlertStore()
		alertStore.addAlert({
			level: 'info',
			title: `Job ${jobId} deleted`,
		})
		return response.data;
	},
	cancelJob: async (jobId: number): Promise<void> => {
		const response = await apiClient.post(`/api/v1/job/cancel/${jobId}`);
		const alertStore = useAlertStore()
		alertStore.addAlert({
			level: 'info',
			title: `Job ${jobId} cancelled`,
		})
		return response.data;
	},
	triggerWebhook: async (jobId: number): Promise<void> => {
		const response = await apiClient.post(`/api/v1/job/webhook/run/${jobId}`);
		const alertStore = useAlertStore()
		alertStore.addAlert({
			level: 'info',
			title: `Webhook ${jobId} triggered`,
		})
		return response.data;
	},
};

export default jobApi;
