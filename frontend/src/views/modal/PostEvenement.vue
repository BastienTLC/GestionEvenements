<script setup>
import { ref } from 'vue';
  defineProps({
    visible:{
      type: Boolean,
      required: true
    }
  });
const active = ref(1);
const completed = ref(false);
const products = ref();
const name = ref();
const email = ref();

</script>

<template>
  <Dialog :visible="visible" @update:visible="$emit('update:visible', $event)" modal header="Edit Profile" :style="{ width: '40rem' }">
    <Stepper v-model:activeStep="active">
      <StepperPanel>
        <template #header="{ index, clickCallback }">
          <button class="bg-transparent border-none inline-flex flex-column gap-2" @click="clickCallback">
                <span :class="['border-round border-2 w-3rem h-3rem inline-flex align-items-center justify-content-center', { 'bg-primary border-primary': index <= active, 'surface-border': index > active }]">
                    <i class="pi pi-info-circle" />
                </span>
          </button>
        </template>
        <template #content="{ nextCallback }">
          <div class="flex flex-column gap-2 mx-auto" style="min-height: 16rem; max-width: 20rem">
            <div class="text-center mt-3 mb-3 text-xl font-semibold">Créer votre évènement</div>
            <div class="field p-fluid">
              <IconField>
                <InputIcon>
                  <i class="pi pi-info" />
                </InputIcon>
                <InputText id="input" v-model="name" type="text" placeholder="Nom évènement" />
              </IconField>
            </div>
            <div class="field p-fluid">
              <IconField>
                <InputIcon>
                  <i class="pi pi-calendar" />
                </InputIcon>
                <Calendar id="calendar-24h" v-model="datetime24h" showTime hourFormat="24" placeholder="Date évènement" />
              </IconField>
            </div>
            <div class="field p-fluid">
              <InputIcon>
                <i class="pi pi-hourglass" />
              </InputIcon>
              <Calendar id="calendar-timeonly" v-model="time" timeOnly placeholder="Durée évènement" />
            </div>
            <div class="field p-fluid">
              <InputIcon>
                <i class="pi pi-users" />
              </InputIcon>
              <InputNumber v-model="value1" inputId="integeronly" placeholder="Nombre de personnes max" />
            </div>
          </div>

          <div class="flex pt-4 justify-content-end">
            <Button label="Next" icon="pi pi-arrow-right" iconPos="right" @click="nextCallback" />
          </div>
        </template>
      </StepperPanel>
      <StepperPanel>
        <template #header="{ index, clickCallback }">
          <button class="bg-transparent border-none inline-flex flex-column gap-2" @click="clickCallback">
                <span :class="['border-round border-2 w-3rem h-3rem inline-flex align-items-center justify-content-center', { 'bg-primary border-primary': index <= active, 'surface-border': index > active }]">
                    <i class="pi pi-building" />
                </span>
          </button>
        </template>
        <template #content="{ prevCallback, nextCallback }">
          <div class="flex flex-column gap-2 mx-auto" style="min-height: 16rem; max-width: 24rem">
            <div class="text-center mt-3 mb-3 text-xl font-semibold">Lieu de votre évènement </div>
            <div class="flex flex-wrap justify-content-center gap-3">
              <div class="field p-fluid">
                <InputIcon>
                  <i class="pi pi-home" />
                </InputIcon>
                <Dropdown v-model="selectedCity" :options="cities" optionLabel="name" placeholder="Select a City" class="w-full md:w-14rem" />
              </div>
            </div>
          </div>
          <div class="flex pt-4 justify-content-between">
            <Button label="Back" severity="secondary" icon="pi pi-arrow-left" @click="prevCallback" />
            <Button label="Next" icon="pi pi-arrow-right" iconPos="right" @click="nextCallback" />
          </div>
        </template>
      </StepperPanel>
      <StepperPanel>
        <template #header="{ index, clickCallback }">
          <button class="bg-transparent border-none inline-flex flex-column gap-2" @click="clickCallback">
                <span :class="['border-round border-2 w-3rem h-3rem inline-flex align-items-center justify-content-center', { 'bg-primary border-primary': index <= active, 'surface-border': index > active }]">
                    <i class="pi pi-id-card" />
                </span>
          </button>
        </template>
        <template #content="{ prevCallback }">
          <div class="flex flex-column gap-2 mx-auto" style="min-height: 16rem; max-width: 24rem">
            <div class="text-center mt-3 mb-3 text-xl font-semibold">Account created successfully</div>
            <div class="text-center">
              <img alt="logo" src="https://primefaces.org/cdn/primevue/images/stepper/content.svg" />
            </div>
          </div>
          <div class="flex pt-4 justify-content-start">
            <Button label="Back" severity="secondary" icon="pi pi-arrow-left" @click="prevCallback" />
          </div>
        </template>
      </StepperPanel>
    </Stepper>
    <div class="flex justify-content-end gap-2">
      <Button type="button" label="Cancel" severity="secondary" @click="$emit('update:visible', false)"></Button>
      <Button type="button" label="Save" @click="$emit('update:visible', false)"></Button>
    </div>
  </Dialog>
</template>

<style scoped>

</style>
