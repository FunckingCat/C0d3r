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

export type Job = {
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

export type ExecutionResult = {
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
