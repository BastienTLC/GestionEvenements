<template>
  <CustomTabMenu :selectedIndex="activeIndex" />
  <div class="fixed-button-container z-5">
    <Button icon="pi pi-plus" rounded aria-label="Filter" size="large" class="p-button-lg" @click="createLieu()" />
  </div>
  <div v-if="isLoading">
    <ProgressSpinner />
  </div>
  <div v-else>
    <DataTable :value="lieux" tableStyle="min-width: 50rem">
      <Column field="nom" header="Nom"></Column>
      <Column field="adresse" header="Adresse"></Column>
      <Column field="capacite_accueil" header="Capacité accueil"></Column>
      <Column  header="Supprimer/Editer">
        <template #body="slotProps">
          <div class="flex flex-row justify-content-around">
            <Button icon="pi pi-trash" class="p-button-rounded p-button-danger" @click="deleteLieu(slotProps.data.id)" />
            <Button icon="pi pi-pencil" class="p-button-rounded p-button-primary" @click="editLieu(slotProps.data.id)" />
          </div>
        </template>
      </Column>
    </DataTable>
  </div>
  <div class="modal-container">
    <Dialog v-model:visible="createLieuVisible" modal :header="isEditing ? 'Editer un lieu' : 'Ajouter un lieu'" :style="{ width: '50rem' }">
      <div class="flex align-items-center gap-3 mb-3">
        <label for="nomLieu" class="font-semibold w-6rem">Nom lieu</label>
        <InputText id="nomLieu" class="flex-auto" autocomplete="off" v-model="form.nom" />
      </div>
      <div class="flex align-items-center gap-3 mb-5">
        <label for="adresse" class="font-semibold w-6rem">Adresse</label>
        <InputText id="adresse" class="flex-auto" autocomplete="off" v-model="form.adresse" />
      </div>
      <div class="flex align-items-center gap-3 mb-5">
        <label for="capacite" class="font-semibold w-6rem">Capacité accueil</label>
        <InputNumber id="capacite" class="flex-auto" autocomplete="off" v-model="form.capacite_accueil" />
      </div>
      <div class="flex justify-content-end gap-2">
        <Button type="button" label="Cancel" severity="secondary" @click="createLieuVisible = false"></Button>
        <Button type="button" label="Save" @click="isEditing ? putLieu() : postLieu()"></Button>
      </div>
    </Dialog>
  </div>
</template>

<script setup>
import CustomTabMenu from "@/components/CustomTabMenu.vue";
import {ref} from "vue";
import { useRouter } from "vue-router";
import LieuxService from "@/services/LieuService.js";

const router = useRouter();
//Postion du tab
const activeIndex = ref(2);
const lieux = ref([]);
// Evenement en cours de chargement
const isLoading = ref(true);
//Evenement en cours de modification
const isEditing = ref(false);
const currentEditingId = ref(null);
// Modal
const createLieuVisible = ref(false);


// Données du formulaire
const form = ref({
  nom: '',
  adresse: '',
  capacite_accueil: 0
});

const loadLieux = async () => {
  try {
    const response = await LieuxService.getAllLieux();
    lieux.value = response.data;
    isLoading.value = false;
  } catch (error) {
    console.error('Error loading evenements:', error);
  }
};

const postLieu = async () => {
  try {
    console.log(form.value);
    const response = await LieuxService.createLieu(form.value);
    loadLieux();
    createLieuVisible.value = false;
  } catch (error) {
    console.error('Error posting lieu:', error);
  }
};

const putLieu = async () => {
  try {
    const response = await LieuxService.updateLieu(currentEditingId.value, form.value);
    loadLieux();
    createLieuVisible.value = false;
  } catch (error) {
    console.error('Error putting lieu:', error);
  }
};

const deleteLieu = async (id) => {
  try {
    const response = await LieuxService.deleteLieu(id);
    loadLieux();
  } catch (error) {
    console.error('Error deleting lieu:', error);
  }
};

const editLieu = async (id) => {
  isEditing.value = true;
  form.value = lieux.value.find(lieu => lieu.id === id);
  currentEditingId.value = id;
  createLieuVisible.value = true;
  console.log(form.value);
};

const createLieu = async () => {
  isEditing.value = false;
  console.log('Create lieu', form.value);
  createLieuVisible.value = true;
  form.value = {
    nom: '',
    adresse: '',
    capacite_accueil: 0
  };
};


loadLieux();


</script>
<style scoped>
.fixed-button-container {
  position: fixed;
  bottom: 1rem;
  right: 1rem;
}
.fixed-button-container > .p-button-icon-only{
  width: 6rem;
  height: 6rem;
}
</style>

