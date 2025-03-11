<template>
    <div class="max-w-md mx-auto p-4">
        <h2 class="text-2xl font-bold mb-4">Create Job</h2>
        <form @submit.prevent="submit" class="space-y-4">
            <div>
                <label for="name" class="label">Job Name</label>
                <input type="text" id="name" v-model="jobParameters.name" class="input input-bordered w-full"
                    required />
            </div>

            <div>
                <label for="dockerImage" class="label">Docker Image</label>
                <input type="text" id="dockerImage" v-model="jobParameters.dockerImage"
                    class="input input-bordered w-full" required />
            </div>

            <div>
                <label for="command" class="label">Start Up command</label>
                <input type="text" id="command" v-model="jobParameters.command" class="input input-bordered w-full" />
            </div>

            <div>
                <label for="environmentVariables" class="label">Environment Variables</label>
                <div class="flex space-x-2">
                    <input type="text" v-model="envKey" placeholder="Key" class="input input-bordered w-1/2" />
                    <input type="text" v-model="envValue" placeholder="Value" class="input input-bordered w-1/2" />
                    <button type="button" @click="addEnvironmentVariable" class="btn btn-primary">Add</button>
                </div>
                <ul class="mt-2">
                    <li v-for="(value, key) in jobParameters.environmentVariables" :key="key"
                        class="badge badge-md badge-outline badge-accent py-4 px-3 m-2">
                        {{ value[0] }} : {{ value[1] }}
                        <button type="button" @click="removeEnvironmentVariable(value[0])" class="">✖</button>
                    </li>
                </ul>
            </div>

            <div>
                <label for="executionType" class="label">Execution Type</label>
                <select id="executionType" v-model="jobParameters.executionType" class="select select-bordered w-full"
                    required>
                    <option value="ON_DEMAND">On Demand</option>
                    <option value="SCHEDULED">Scheduled</option>
                    <option value="WEBHOOK">Webhook</option>
                </select>
            </div>

            <div v-if="jobParameters.executionType === 'SCHEDULED'">
                <label for="schedule" class="label">Schedule (Cron format)</label>
                <input type="text" id="schedule" v-model="jobParameters.schedule" class="input input-bordered w-full" />
            </div>

            <button type="submit" class="btn btn-primary w-full">Create Job</button>
        </form>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { ExecutionType, type CreateJobRequest } from '@/types/ApiTypes';
import { isCronValid } from '@/util/CronValidator';
import jobApi from '@/api/JobsApi';
import router from '@/router';

interface IJonParameters {
    name?: string;
    dockerImage?: string;
    command?: string;
    environmentVariables?: Map<string, string>;
    executionType?: ExecutionType;
    schedule?: string | null;
};

const jobParameters = ref<IJonParameters>({
    environmentVariables: new Map<string, string>(),
    schedule: '* * * * *'
})

const envKey = ref<string | null>();
const envValue = ref<string | null>();

const addEnvironmentVariable = () => {
    if (envKey.value && envValue.value) {
        jobParameters.value.environmentVariables?.set(envKey.value, envValue.value);
        envKey.value = null;
        envValue.value = null;
    }
};

const removeEnvironmentVariable = (key: string) => {
    jobParameters.value.environmentVariables?.delete(key);
};

const submit = async () => {
    const collectedParams = jobParameters.value

    if (collectedParams.executionType == ExecutionType.SCHEDULED) {
        const cronValid = isCronValid(collectedParams.schedule!!)
    }

    const createJobRequest: CreateJobRequest = {
        name: collectedParams.name!!,
        dockerImage: collectedParams.dockerImage!!,
        command: collectedParams.command!!.split(" "),
        environmentVariables: collectedParams.environmentVariables,
        executionType: collectedParams.executionType!!,
        schedule: collectedParams.executionType == ExecutionType.SCHEDULED ? collectedParams.schedule!! : null
    }

    const createdJob = await jobApi.createJob(createJobRequest)

    router.push(`/tasks/${createdJob.id}`)

}

</script>