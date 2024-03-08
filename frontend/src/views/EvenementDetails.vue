<script setup>
import {computed, ref, watchEffect} from 'vue';
import {useRoute} from 'vue-router';
import EvenementService from '@/services/EvenementService.js';
import CustomTabMenu from "@/components/CustomTabMenu.vue";
import {formatDate, formatDuree} from "@/utils/formatDate.js";
import Commentaire from "@/components/Commentaire.vue";
import {membreLogin} from "@/config/apiConfig.js";

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

const isMemberRegistered = computed(() => {
  return members.value.some(membre => membre.id === membreLogin.id);
});


watchEffect(() => {
  loadEvenement(evenementId.value);
  loadEvenementLieu(evenementId.value)
  loadEvenementMembers(evenementId.value);

});
</script>

<template>
  <CustomTabMenu :selectedIndex="membreLogin.id"/>
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
        <template #subtitle>{{ `${evenement.nombreMaxPersonnes}/${lieu.capacite_accueil}` }}</template>
        <template #footer>
        </template>
      </Card>
      <TabView>
        <TabPanel header="Information">
          <div class="flex flex-column gap-4">
            <div class="flex flex-column gap-2">
              <span class="font-medium text-secondary text-sm">Date</span>
              <span class="text-lg font-medium text-900">{{ `${formatDate(evenement.dateEvenement)} ${evenement.heure}` }}</span>
            </div>
            <Divider />
            <div class="flex flex-column gap-2">
              <span class="font-medium text-secondary text-sm">Lieu</span>
              <span class="text-lg font-medium text-900">{{ lieu.nom }}</span>
            </div>
            <Divider />
            <div class="flex flex-column gap-2">
              <span class="font-medium text-secondary text-sm">Durée</span>
              <span class="text-lg font-medium text-900">{{ formatDuree(evenement.duree) }}</span>
            </div>
            <Divider />
            <div class="flex flex-column gap-2">
              <span class="font-medium text-secondary text-sm">Description</span>
              <span class="text-lg font-medium text-900">{{ evenement.description }}</span>
            </div>
            <Divider />
            <div v-if="isMemberRegistered" class="flex justify-content-center gap-2">
              <Button label="Se desinscrire" class="p-button-danger" />
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
          <Commentaire
            :membreId="1"
            :evenementId="Number(evenementId)"
          />
        </TabPanel>
      </TabView>
    </div>
  </div>
</template>

<style scoped>
/* Styles spécifiques au composant */
</style>
