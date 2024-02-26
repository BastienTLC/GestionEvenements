<template>
  <CustomTabMenu :selectedIndex="activeIndex" />
</template>

<script>
import CustomTabMenu from "@/components/CustomTabMenu.vue";
import { defineComponent, ref } from "vue";

export default defineComponent({
  components: {CustomTabMenu},
  setup() {
    const activeIndex = ref(2);
    return {activeIndex};
  }
});
</script>
