import { defineStore } from "pinia";
import { ref, computed } from "vue";

interface IBreadCrumbs {
    crumbs: BeadCrumb[]
}

export interface BeadCrumb {
    name: string,
    link: string
}

export const useBreadCrumbStore = defineStore("breadCrumb", () => {

    const getDefaultState = () => ({
        crumbs: []
    });

    const state = ref<IBreadCrumbs>(getDefaultState());

    async function setCrumbs(crumbs: BeadCrumb[]) {
        state.value.crumbs = crumbs;
    }

    function reset() {
        state.value = getDefaultState()
    }

    return { 
        state, 
        crumbs: computed(() => state.value.crumbs),
        setCrumbs,
        reset
    };
});