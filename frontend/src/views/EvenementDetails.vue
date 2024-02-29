<script setup>
import {ref, watchEffect} from 'vue';
import {useRoute} from 'vue-router';
import EvenementService from '@/services/EvenementService.js';
import CustomTabMenu from "@/components/CustomTabMenu.vue";
import {formatDate} from "@/utils/formatDate.js";

const route = useRoute();
const evenementId = ref(route.params.id);
const evenement = ref({});
const members = ref({});
const lieu = ref({});
const isLoading = ref(true);

const loadEvenement = async (id) => {
  try {
    const response = await EvenementService.getEvenementById(id);
    evenement.value = response.data;
  } catch (error) {
    console.error('Error loading evenements:', error);
  }
};

const loadEvenementLieu = async (id) => {
  try {
    const response = await EvenementService.getEvenementLieu(id);
    lieu.value = response.data;
    console.log(lieu.value);
  } catch (error) {
    console.error('Error loading evenements:', error);
  }
};

const loadEvenementMembers = async (id) => {
  try {
    const response = await EvenementService.getEvenementMembers(id);
    members.value = response.data;
    isLoading.value = false;
  } catch (error) {
    console.error('Error loading evenements:', error);
  }
};



const getAvatarLabel = (member) => {
  return `${member.nom[0]}${member.prenom[0]}`;
};

watchEffect(() => {
  loadEvenement(evenementId.value);
  loadEvenementLieu(evenementId.value)
  loadEvenementMembers(evenementId.value);

});
</script>

<template>
  <CustomTabMenu :selectedIndex="1"/>
  <!-- Afficher les détails de l'événement -->
  <div v-if="isLoading">
    <ProgressSpinner />
  </div>
  <div v-else>
    <div class="flex flex-column justify-content-around align-items-center">
      <Card style="width: 25rem; overflow: hidden">
        <template #header>
          <img alt="user header" src="https://pbs.twimg.com/profile_images/1587790498684698625/MeI2W4h5_400x400.jpg" />
        </template>
        <template #title>{{evenement.nom}}</template>
        <template #subtitle>{{ `${evenement.nombreMaxPersonne}/${lieu.capacite_accueil}` }}</template>
        <template #footer>
        </template>
      </Card>
      <TabView>
        <TabPanel header="Information">
          <div class="flex flex-column gap-4">
            <div class="flex flex-column gap-2">
              <span class="font-medium text-secondary text-sm">Date</span>
              <span class="text-lg font-medium text-900">{{ formatDate(evenement.date) }}</span>
            </div>
            <Divider />
            <div class="flex flex-column gap-2">
              <span class="font-medium text-secondary text-sm">Lieu</span>
              <span class="text-lg font-medium text-900">{{ lieu.nom }}</span>
            </div>
            <Divider />
            <div class="flex flex-column gap-2">
              <span class="font-medium text-secondary text-sm">Durée</span>
              <span class="text-lg font-medium text-900">{{ evenement.duree }}</span>
            </div>
            <Divider />
            <div class="flex flex-column gap-2">
              <span class="font-medium text-secondary text-sm">Description</span>
              <span class="text-lg font-medium text-900">{{ evenement.description }}</span>
            </div>
          </div>
        </TabPanel>
        <TabPanel header="Les Participants">
          <TabPanel header="Les horaires">
            <DataTable :value="members" tableStyle="min-width: 50rem">
              <Column header="Picture">
                <template #body="slotProps">
                  <Avatar :label="getAvatarLabel(slotProps.data)" class="mr-2" size="medium" shape="circle" />
                </template>
              </Column>
              <Column field="nom" header="Nom"></Column>
              <Column field="prenom" header="Prenom"></Column>
              <Column field="age" header="Age"></Column>
            </DataTable>
          </TabPanel>
        </TabPanel>
        <TabPanel header="Les Commentaires">
          <p class="m-0">
            At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui
            officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus.
          </p>
        </TabPanel>
      </TabView>
    </div>
  </div>
</template>

<style scoped>
/* Styles spécifiques au composant */
</style>
