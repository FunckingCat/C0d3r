import axios from 'axios';
import StorageService from "@/util/StorageService.ts";
import { useAlertStore } from '@/stores/alertStore';

const apiClient = axios.create({
  baseURL: 'http://localhost:8090',
  headers: {
    'Content-Type': 'application/json',
    'Referrer-Policy': 'no-referrer',
  },
});

// Request interceptor for attaching auth token
apiClient.interceptors.request.use((config) => {
  const token = StorageService.getAccessToken();
  if (token && !config.url?.includes("public")) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, (error) => Promise.reject(error));

// Response interceptor for handling errors (e.g., token expiration)
apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response?.status != 200) {
      const alertStore = useAlertStore()
      alertStore.addAlert({
        level: 'error',
        title: `${error.response?.status}: ${error.response?.data.message}`,
        text: error.response?.data.description,
      })
    }
    return Promise.reject(error);
  }
);

export default apiClient;
