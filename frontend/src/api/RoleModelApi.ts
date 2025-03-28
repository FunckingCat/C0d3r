import apiClient from "./API";
import type { GroupDescription, User, PermissionRequest, GroupTokenResponse, CreateGroupRequest, JoinGroupRequest, ExcludeRequest } from "@/types/ApiTypes";

const roleModelApi = {
    changePermission: async (data: PermissionRequest): Promise<any> => {
        const response = await apiClient.post("/api/v1/role-model/change-permission", data);
        return response.data;
    },
    createGroup: async (data: CreateGroupRequest): Promise<string> => {
        console.log("Create group RB", data)
        const response = await apiClient.post("/api/v1/role-model/create-group", data);
        return response.data;
    },
    excludeMemberFromGroup: async (data: any): Promise<any> => {
        const response = await apiClient.post("/api/v1/role-model/exclude-member-from-group", data);
        return response.data;
    },
    getGroup: async (groupId: string): Promise<GroupDescription> => {
        const response = await apiClient.get(`/api/v1/role-model/group/${groupId}`);
        return response.data;
    },
    getJoinGroupToken: async (groupId: string): Promise<GroupTokenResponse> => {
        const response = await apiClient.get(`/api/v1/role-model/get-join-group-token/${groupId}`);
        return response.data;
    },
    joinGroup: async (data: JoinGroupRequest): Promise<void> => {
        const response = await apiClient.post("/api/v1/role-model/join-group", data);
        return response.data;
    },
    leaveGroup: async (groupId: string): Promise<void> => {
        const response = await apiClient.post(`/api/v1/role-model/leave-group/${groupId}`);
        return response.data;
    },
    refreshJoinGroupToken: async (groupId: string): Promise<any> => {
        const response = await apiClient.post(`/api/v1/role-model/refresh-join-group-token/${groupId}`);
        return response.data;
    },
    excludeMember: async (excludeRequest: ExcludeRequest): Promise<void> => {
        const response = await apiClient.post(`/api/v1/role-model/exclude-member-from-group`, excludeRequest);
        return response.data;
    },
};

export default roleModelApi;