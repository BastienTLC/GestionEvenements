<template>
  <CustomTabMenu :selectedIndex="activeIndex" />
  <DataView :value="evenements">
    <template #list="slotProps">
      <div class="grid grid-nogutter">
        <div v-for="(item, index) in slotProps.items" :key="index" class="col-12">
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
                <span class="text-xl font-semibold text-900">{{ item.date }}</span>
                <div class="flex flex-row-reverse md:flex-row gap-2">
                  <Button icon="pi pi-shopping-cart" label="Rejoindre" :disabled="item.inventoryStatus === 'OUTOFSTOCK'" class="flex-auto md:flex-initial white-space-nowrap"></Button>
                  <Button icon="pi pi-shopping-cart" label="Information" :disabled="item.inventoryStatus === 'OUTOFSTOCK'" @click="openEventDetails(item.id)" class="flex-auto md:flex-initial white-space-nowrap"></Button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </template>
  </DataView>
</template>

<script>
import CustomTabMenu from "@/components/CustomTabMenu.vue";
import { defineComponent, ref } from "vue";
import EvenementService from "@/services/EvenementService.js";
import {useRouter} from "vue-router";

export default defineComponent({
  components: { CustomTabMenu },
  setup() {
    const router = useRouter();
    const activeIndex = ref(1);
    const evenements = ref([]);
    const loadEvenements = async () => {
      try {
        const response = await EvenementService.getAllEvenements();
        console.log(response.data); // Affiche les données récupérées depuis l'API
        evenements.value = response.data;
      } catch (error) {
        console.error('Error loading evenements:', error);
      }
    };
    loadEvenements();

    const openEventDetails = (eventId) => {
      router.push(`/evenements/${eventId}`);
    };

    return { activeIndex, evenements, openEventDetails };
  }
});
</script>

