import { defineStore } from 'pinia';
import { ref } from 'vue';

export type AlertLevel = 'info' | 'warn' | 'error' | 'success';

export interface Alert {
    id: string;
    level: AlertLevel;
    title: string;
    text?: string;
    duration: number;
    timeoutId?: ReturnType<typeof setTimeout>;
}

export interface NewAlert {
    level: AlertLevel;
    title: string;
    text?: string;
    duration?: number;
}

export const useAlertStore = defineStore('alertStore', () => {

    const alerts = ref<Alert[]>([]);
    const defaultDurationSeconds = 5;

    const generateId = (): string => {
        return Date.now().toString(36) + Math.random().toString(36).substring(2, 5);
    };

    const removeAlert = (id: string) => {
        const index = alerts.value.findIndex(alert => alert.id === id);
        if (index !== -1) {
            const alertToRemove = alerts.value[index];
            if (alertToRemove.timeoutId) {
                clearTimeout(alertToRemove.timeoutId);
            }
            alerts.value.splice(index, 1);
        }
    };

    const addAlert = (newAlert: NewAlert) => {
        const id = generateId();
        const durationMs = (newAlert.duration ?? defaultDurationSeconds) * 1000;

        const alertToAdd: Alert = {
            ...newAlert,
            id,
            duration: durationMs,
        };

        const timeoutId = setTimeout(() => {
            removeAlert(id);
        }, durationMs);

        alertToAdd.timeoutId = timeoutId;

        alerts.value.push(alertToAdd);
    };

    return {
        alerts,
        addAlert,
        removeAlert,
    };
});