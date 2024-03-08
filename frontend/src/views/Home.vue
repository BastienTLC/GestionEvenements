<template>
  <CustomTabMenu :selectedIndex="activeIndex" />
  <div class="p-grid p-fluid">
    <div class="p-col-12 p-lg-6">
      <Card>
        <template #title>
          Membres
        </template>
        <template #content>
        </template>
      </Card>
    </div>
    <div class="p-col-12 p-lg-6">
      <Card>
        <template #title>
          Événements
        </template>
        <template #content>
        </template>
      </Card>
    </div>
    <div class="p-col-12">
      <Card>
        <template #title>
          Inscriptions
        </template>
        <template #content>
        </template>
      </Card>
    </div>
    <div class="p-col-12">
      <Card>
        <template #title>
          Événements par Membre
        </template>
        <template #content>
          <!-- Ajoutez ici le composant pour afficher les événements par membre -->
        </template>
      </Card>
    </div>
    <div class="p-col-12">
      <Card>
        <template #title>
          Événements par Lieu
        </template>
        <template #content>
          <!-- Ajoutez ici le composant pour afficher les événements par lieu -->
        </template>
      </Card>
    </div>
  </div>
</template>

<script>
import CustomTabMenu from "@/components/CustomTabMenu.vue";
import { defineComponent, ref } from "vue";

export default defineComponent({
  components: {CustomTabMenu},
  setup() {
    const activeIndex = ref(0);
    return {activeIndex};
  }
});


</script>
