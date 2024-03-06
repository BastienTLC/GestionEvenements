<template>
  <CustomTabMenu :selectedIndex="activeIndex" />
  <div class="fixed-button-container z-5">
    <Button icon="pi pi-plus" rounded aria-label="Filter" size="large" class="p-button-lg" @click="CreateEventVisible = true" />
  </div>
  <div v-if="isLoading">
    <ProgressSpinner />
  </div>
  <div v-else>
    <DataView :value="evenements">
      <template #list="slotProps">
        <div class="grid grid-nogutter">
          <div v-for="(item, index) in slotProps.items" @contextmenu="onRightClick($event, item)" :key="index" class="col-12">
            <div class="flex flex-column sm:flex-row sm:align-items-center p-4 gap-3" :class="{ 'border-top-1 surface-border': index !== 0 }">
              <div class="md:w-10rem relative">
                <img class="block xl:block mx-auto border-round w-full" :src="`https://pbs.twimg.com/profile_images/1587790498684698625/MeI2W4h5_400x400.jpg`" :alt="item.name" />
              </div>
              <div class="flex flex-column md:flex-row justify-content-between md:align-items-center flex-1 gap-4">
                <div class="flex flex-row md:flex-column justify-content-between align-items-start gap-2">
                  <div>
                    <span class="font-medium text-secondary text-sm">{{ item.nom }}</span>
                    <div class="text-lg font-medium text-900 mt-2">{{ item.nom }}</div>
                  </div>
                  <div class="surface-100 p-1" style="border-radius: 30px">
                    <div class="surface-0 flex align-items-center gap-2 justify-content-center py-1 px-2" style="border-radius: 30px; box-shadow: 0px 1px 2px 0px rgba(0, 0, 0, 0.04), 0px 1px 2px 0px rgba(0, 0, 0, 0.06)">
                      <span class="text-900 font-medium text-sm">{{ item.rating }}</span>
                      <i class="pi pi-star-fill text-yellow-500"></i>
                    </div>
                  </div>
                </div>
                <div class="flex flex-column md:align-items-end gap-5">
                  <span class="text-xl font-semibold text-900">{{ formatDate(item.dateEvenement) }}</span>
                  <div class="flex flex-row-reverse md:flex-row gap-2">
                    <Button icon="pi pi-shopping-cart" label="Rejoindre" :disabled="item.inventoryStatus === 'FULL'" class="flex-auto md:flex-initial white-space-nowrap"></Button>
                    <Button icon="pi pi-shopping-cart" label="Information" @click="openEventDetails(item.id)" class="flex-auto md:flex-initial white-space-nowrap"></Button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <ContextMenu ref="menu" :model="items" />
        </div>
      </template>
    </DataView>
  </div>
  <div class="modal-container">
    <PostEvenement v-if="CreateEventVisible" v-model:visible="CreateEventVisible" @modalClose="handleModalClose" />
    <EditEvenement v-if="EditEventVisible" v-model:visible="EditEventVisible" :evenementToEdit="selectedEvenement" @modalClose="handleModalClose" />
  </div>
</template>

<script setup>
import CustomTabMenu from "@/components/CustomTabMenu.vue";
import {ref, watchEffect} from "vue";
import { useRouter } from "vue-router";
import EvenementService from "@/services/EvenementService.js";
import { formatDate} from "@/utils/formatDate.js";
import PostEvenement from "@/views/modal/PostEvenement.vue";
import EditEvenement from "@/views/modal/EditEvenement.vue";

const router = useRouter();
//Postion du tab
const activeIndex = ref(1);
const evenements = ref([]);
// Evenement en cours de chargement
const isLoading = ref(true);
// Modal
const CreateEventVisible = ref(false);
const EditEventVisible = ref(false);
// ContextMenu
const menu = ref();
// Id de l'evenement selectionné
const selectedEvenement = ref(null);
// Menu items
const items = ref([
  { label: 'Editer', icon: 'pi pi-file-edit', command: () => EditEventVisible.value = true},
  { label: 'Supprimer', icon: 'pi pi-trash', command: () => deleteEvenement() }
]);


const loadEvenements = async () => {
  try {
    const response = await EvenementService.getAllEvenements();
    console.log(response.data); // Affiche les données récupérées depuis l'API
    evenements.value = response.data;
    isLoading.value = false;
  } catch (error) {
    console.error('Error loading evenements:', error);
  }
};

const deleteEvenement = async () => {
  try {
    const response = await EvenementService.deleteEvenement(selectedEvenement.value.id);
    loadEvenements();
  } catch (error) {
    console.error('Error deleting evenement:', error);
  }
};

watchEffect(() => {
  loadEvenements();
});

const openEventDetails = (eventId) => {
  router.push(`/evenements/${eventId}`);
};

const handleModalClose = () => {
  loadEvenements();
};
const onRightClick = (event, evenement) => {
  selectedEvenement.value = evenement;
  menu.value.show(event);
};

</script>
<style scoped>
.fixed-button-container {
  position: fixed;
  bottom: 1rem;
  right: 1rem;
}
.p-button-icon-only{
  width: 6rem;
  height: 6rem;
}
</style>

