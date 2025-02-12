const StorageService = {
    saveTokens: (accessToken: string, refreshToken: string) => {
      localStorage.setItem('access_token', accessToken);
      localStorage.setItem('refresh_token', refreshToken);
    },
  
    clearTokens: () => {
      localStorage.removeItem('access_token');
      localStorage.removeItem('refresh_token');
    },
  
    getAccessToken: (): string | null => localStorage.getItem('access_token'),
  
    getRefreshToken: (): string | null => localStorage.getItem('refresh_token'),
  };
  
  export default StorageService;
  