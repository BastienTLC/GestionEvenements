<template>
  <div class="card">
    <TabMenu :model="items" @tab-change="navigateTo" :activeIndex="selectedIndex" />
  </div>
  <Dialog v-model:visible="visible" modal header="Connexion" :style="{ width: '50rem' }">
    <div class="edit-container">
      <div class="login-field">
        <div class="login-field">
          <label>Username</label>
          <InputText id="username" type="text" />
        </div>
        <div class="login-field">
          <label>Password</label>
          <InputText id="password" type="password" />
        </div>
        <Button label="Login" icon="pi pi-user"></Button>
      </div>
      <div class="divider-container">
        <Divider layout="vertical"><b>OR</b></Divider>
      </div>
      <div class="signin-container">
        <div class="login-field">
          <label>Email</label>
          <InputText id="username" type="text"/>
        </div>
        <div class="login-field">
          <label>Username</label>
          <InputText id="username" type="text"/>
        </div>
        <div class="login-field">
          <label>Password</label>
          <InputText id="password" type="password"/>
        </div>
        <Button label="Signup" icon="pi pi-user"></Button>
      </div>
    </div>
  </Dialog>
</template>

<script setup>
import { ref, toRefs } from "vue";
import { useRouter } from "vue-router";

const props = defineProps(
  {
    selectedIndex: {
      type: Number,
      default: 0
    }
  }
);

const router = useRouter(); // Initialiser le routeur
const visible = ref(false);

const items = ref([
  { label: 'Dashboard', icon: 'pi pi-home', route: '/'},
  { label: 'Evenement', icon: 'pi pi-users', route:'/evenements' },
  { label: 'About', icon: 'pi pi-list', route:'/about' },
  { label: 'Connexion', icon: 'pi pi-user', route:'/connexion' }
]);

const navigateTo = (route) => {
  if (route.index === 3) {
    visible.value = true;
    return;
  }
  router.push(items.value[route.index].route);
};
</script>

<style scoped>
.edit-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.login-field {
  margin-bottom: 1rem;
}

.login-field label {
  display: block;
}

.signin-container,
.login-container {
  flex: 1;
}

.divider-container {
  margin: 0 1rem;
}

@media (max-width: 768px) {
  .edit-container {
    flex-direction: column;
    align-items: stretch;
  }

  .divider-container {
    margin: 1rem 0;
  }
}

</style>
