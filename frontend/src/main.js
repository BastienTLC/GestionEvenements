import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router';
import './assets/main.css'
import App from './App.vue'
import Home from './views/Home.vue'
import About from './views/About.vue'
import Evenements from './views/Evenements.vue'

const routes = [
  {path : "/" , component: Home},
  {path : "/about" , component: About},
  {path : "/evenements" , component: Evenements}
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

createApp(App).use(router).mount("#app")
