import axios from 'axios';
import StorageService from "@/util/StorageService.ts";

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
apiClient.interceptors.response.use((response) => response, async (error) => {
  if (error.response?.status === 401) {
    // Optionally handle token refresh here
    console.warn('Unauthorized request. Redirecting to login...');
  }
  return Promise.reject(error);
});

export default apiClient;
