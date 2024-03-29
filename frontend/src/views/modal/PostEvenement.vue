  <script setup>
  import {ref, watchEffect} from 'vue';
  import EvenementService from "@/services/EvenementService.js";
  import { useToast } from "primevue/usetoast";
  const toast = useToast();
  import LieuService from "@/services/LieuService.js";
    defineProps({
      visible:{
        type: Boolean,
        required: true
      }
    });
  const errorMessages = ref([]);
  const active = ref(0);
  const lieux = ref({});
  const lieu = ref();
  const succesMessage = "Evenement créé avec succès";
  const postInput = ref({
    nom: "",
    dateEvenement: "",
    heure: "",
    duree: "",
    nombreMaxPersonnes: 0,
    lieuId: 0
  });

  const loadLieux = async () => {
    try {
      const response = await LieuService.getAllLieux();
      lieux.value = response.data;
    } catch (error) {
      console.error('Error loading evenements:', error);
    }
  };

  const postEvenement = async (input) => {
    try {
      const response = await EvenementService.createEvenement(input);
      console.log(response.data); // Affiche les données récupérées depuis l'API
      showSuccess();
    } catch (error) {
      console.error('Error loading evenements:', error);
      errorMessages.value.push(error.response.data);
      showError(errorMessages.value[0]);
    }
  };


  watchEffect(() => {
    loadLieux();
  });

  const createEvenement = async () => {
    if (!validateForm()) {
      errorMessages.value.push("Veuillez remplir tous les champs");
      showError();
      return false;
    } else {
      errorMessages.value = [];
      const input = convertInput();
      await postEvenement(input);
      return true;
    }
  };

  const validateForm = () => {
    const { nom, dateEvenement, duree, nombreMaxPersonnes } = postInput.value;
    return nom && dateEvenement && duree && nombreMaxPersonnes > 0 && lieu.value;
  };


  const convertInput = () => {
    const dateObj = new Date(postInput.value.dateEvenement);
    const hoursEvenement = dateObj.toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit', hour12: false });

    const dateOnly = new Date(postInput.value.dateEvenement);
    dateOnly.setHours(0, 0, 0, 0);

    const dureeI = postInput.value.duree;
    const heures = dureeI.getHours();
    const minutes = dureeI.getMinutes();
    const secondes = dureeI.getSeconds();
    const dureeMs = (heures * 60 * 60 + minutes * 60 + secondes) * 1000;
    console.log(dateOnly)

    return {
      nom: postInput.value.nom,
      dateEvenement: dateOnly,
      heure: hoursEvenement,
      duree: dureeMs,
      nombreMaxPersonnes: postInput.value.nombreMaxPersonnes,
      lieuId: lieu.value.id
    };
  };

  const showSuccess = () => {
    toast.add({ severity: 'success', summary: 'Success Message', detail: succesMessage, life: 3000 });
  };

  const showError = () => {
    toast.add({ severity: 'error', summary: 'Error Message', detail: errorMessages.value[0], life: 3000 });
  };


  </script>

  <template>
    <Dialog :visible="visible" @update:visible="$emit('update:visible', $event)" modal header="Création d'évènements" :style="{ width: '40rem' }">
      <Toast />
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
                  <Calendar id="calendar-24h" v-model="postInput.dateEvenement" showTime hourFormat="24" />
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
                <InputNumber v-model="postInput.nombreMaxPersonnes" inputId="integeronly" placeholder="Nombre de personnes max" />
              </div>
            </div>

            <div class="flex pt-4 justify-content-end">
              <Button label="Suivant" icon="pi pi-arrow-right" iconPos="right" @click="nextCallback" />
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
              <Button label="Retour" severity="secondary" icon="pi pi-arrow-left" @click="prevCallback" />
              <Button label="Valider" icon="pi pi-arrow-right" iconPos="right" @click="() => { if (createEvenement()) { nextCallback(); } else { prevCallback(); } }" />
            </div>
          </template>
        </StepperPanel>
        <StepperPanel>
          <template #header="{ index, clickCallback }">
            <button class="bg-transparent border-none inline-flex flex-column gap-2" @click="() => { if (createEvenement()) { clickCallback() } else { clickCallback() } }">
                  <span :class="['border-round border-2 w-3rem h-3rem inline-flex align-items-center justify-content-center', { 'bg-primary border-primary': index <= active, 'surface-border': index > active }]">
                      <i class="pi pi-id-card" />
                  </span>
            </button>
          </template>
          <template #content="{ prevCallback }">
            <div class="flex flex-column gap-2 mx-auto" style="min-height: 16rem; max-width: 24rem">
              <div class="text-center mt-3 mb-3 text-xl font-semibold">
                {{ errorMessages.length !== 0 ? errorMessages[0] : succesMessage }}
              </div>
              <div class="text-center">
                <img alt="logo" src="https://primefaces.org/cdn/primevue/images/stepper/content.svg" />
              </div>
            </div>
            <div class="flex pt-4 justify-content-between">
              <Button label="Back" severity="secondary" icon="pi pi-arrow-left" @click="prevCallback" />
              <Button label="Terminer" iconPos="right" @click="$emit('update:visible', false); $emit('modalClose')" :disabled="errorMessages.length !== 0" />
            </div>
          </template>
        </StepperPanel>
      </Stepper>
    </Dialog>
  </template>

  <style scoped>

  </style>
