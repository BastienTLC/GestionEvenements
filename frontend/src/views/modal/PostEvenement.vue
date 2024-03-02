<script setup>
import {ref, watchEffect} from 'vue';
import EvenementService from "@/services/EvenementService.js";
import LieuService from "@/services/LieuService.js";
  defineProps({
    visible:{
      type: Boolean,
      required: true
    }
  });
const active = ref(1);
const completed = ref(false);
const lieux = ref({});
const lieu = ref();
const evenementErrorCode = ref("");
const postInput = ref({
  nom: "",
  date: "",
  duree: "",
  nombreMaxPersonne: 0,
  idLieu: 0
});

const loadLieux = async () => {
  try {
    const response = await LieuService.getAllLieux();
    console.log(response.data); // Affiche les données récupérées depuis l'API
    lieux.value = response.data;
  } catch (error) {
    console.error('Error loading evenements:', error);
  }
};

const checkEvenement = async () => {
  try {
    const response = await EvenementService.checkEvenement(postInput.value);
    evenementErrorCode.value = response.data;
  } catch (error) {
    console.error('Error loading evenements:', error);
  }
};

watchEffect(() => {
  loadLieux();
});

const checkPostInput = () => {

  if (postInput.value.nom === "" || postInput.value.date === "" || postInput.value.duree === "" || postInput.value.nombreMaxPersonne === 0 || lieu.value === null) {
    console.log("Veuillez remplir tous les champs");
    return false;
  } else {
    postInput.value.idLieu = lieu.value.id;
    checkEvenement();
    if (evenementErrorCode.value !== "") {
      console.log("Evenement existant");
      return false;
    }
    return true;
  }
};


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
                <span>Nom de l'évènement</span>
                <InputText id="input" v-model="postInput.nom" type="text" />
              </IconField>
            </div>
            <div class="field p-fluid">
              <IconField>
                <InputIcon>
                  <i class="pi pi-calendar" />
                </InputIcon>
                <span>Date de l'évènement</span>
                <Calendar id="calendar-24h" v-model="postInput.date" showTime hourFormat="24" />
              </IconField>
            </div>
            <div class="field p-fluid">
              <InputIcon>
                <i class="pi pi-hourglass" />
              </InputIcon>
              <span>Durée de l'évènement</span>
              <Calendar id="calendar-timeonly" v-model="postInput.duree" timeOnly />
            </div>
            <div class="field p-fluid">
              <InputIcon>
                <i class="pi pi-users" />
              </InputIcon>
              <span>Nombre de personnes max</span>
              <InputNumber v-model="postInput.nombreMaxPersonne" inputId="integeronly" placeholder="Nombre de personnes max" />
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
                <span>Lieu de l'évènement</span>
                <Dropdown v-model="lieu" :options="lieux" optionLabel="nom" placeholder="Select a City" class="w-full md:w-14rem" />
              </div>
            </div>
          </div>
          <div class="flex pt-4 justify-content-between">
            <Button label="Back" severity="secondary" icon="pi pi-arrow-left" @click="prevCallback" />
            <Button label="Next" icon="pi pi-arrow-right" iconPos="right" @click="() => { if (checkPostInput()) { nextCallback(); } else { prevCallback(); } }" />
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
