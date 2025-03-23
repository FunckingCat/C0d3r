export type LoginRequest = {
	username: string;
	password: string;
};

export type LoginResponse = {
	accessToken: string;
	refreshToken: string;
	expiresIn: number;
};

export type RegisterRequest = {
	username: string;
	password: string;
};

export type RegisterResponse = {
	accessToken: string;
	refreshToken: string;
	expiresIn: number;
};

export interface CreateJobRequest {
    name: string;
    dockerImage: string;
    command: string[];
    environmentVariables?: Map<string, string>;
    executionType: ExecutionType;
    schedule?: string | null;
};

export interface User {
	id: string,
	username: string,
	roles: Role[],
	groups: Group[],
}

export interface Group {
	name: string;
	id: string;
	permissions: Permission[];
}

export enum Permission { RUN, VIEW, EDIT, ADMIN };

export enum Role {
	USER, ADMIN
}

export interface Member {
	id: string;
	username: string;
	permissions: Permission[]; 
}
  
export interface GroupDescription {
	id: string;
	name: string;
	members: Member[];
}

export enum ActionType {
	GRANT = 'GRANT',
	REVOKE = 'REVOKE'
}

export interface PermissionRequest {
	action: ActionType;
	memberId: string;
	groupId: string;
	permission: string;
}

export interface GroupTokenResponse {
	groupId: string;
	token: string;
}

export interface CreateGroupRequest {
	name: string;
}

export interface JoinGroupRequest {
	token: string;
}

export interface Job {
	id?: number | null;
	userId: string;
	name: string;
	createdAt?: string | null;
	dockerImage: string;
	command: string[];
	environmentVariables: Record<string, string>;
	executionType: ExecutionType;
	schedule?: string | null;
	status?: JobStatus | null;
	ordinal?: number | null;
	deleted?: boolean | null;
	executionResults?: ExecutionResult[] | null;
};

export interface ExecutionResult {
	id?: number | null;
	jobId: number;
	startedAt: string;
	finishedAt?: string | null;
	status: ExecutionStatus;
	logs?: string[] | null;
	exitCode?: number | null;
	errorMessage?: string | null;
	recordedAt: string;
	originalJobName?: string | null;
};

export enum JobStatus {
	PENDING = "PENDING",
	RUNNING = "RUNNING",
	COMPLETED = "COMPLETED",
	FAILED = "FAILED",
	CANCELLED = "CANCELLED",
}

export enum ExecutionStatus {
	PENDING = "PENDING",
	RUNNING = "RUNNING",
	COMPLETED = "COMPLETED",
	FAILED = "FAILED",
	CANCELLED = "CANCELLED",
}

export enum ExecutionType {
	ON_DEMAND = "ON_DEMAND",
	SCHEDULED = "SCHEDULED",
	WEBHOOK = "WEBHOOK",
}
