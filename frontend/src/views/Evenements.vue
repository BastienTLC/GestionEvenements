<template>
  <CustomTabMenu :selectedIndex="activeIndex" />

</template>

<script>
import CustomTabMenu from "@/components/CustomTabMenu.vue";
import { defineComponent, ref } from "vue";
import EvenementService from "@/services/EvenementService.js";

export default defineComponent({
  components: { CustomTabMenu },
  setup() {
    const activeIndex = ref(1);
    const loadEvenements = async () => {
      try {
        const response = await EvenementService.getAllEvenements();
        console.log(response.data); // Affiche les données récupérées depuis l'API
      } catch (error) {
        console.error('Error loading evenements:', error);
      }
    };
    loadEvenements();

    return { activeIndex };
  }
});
</script>
