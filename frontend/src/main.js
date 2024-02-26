import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import 'primevue/resources/themes/aura-light-amber/theme.css'
import PrimeVue from 'primevue/config'
import {createRouter, createWebHistory} from "vue-router";
import 'primevue/resources/primevue.min.css'
import 'primeicons/primeicons.css'

import Home from './views/Home.vue'
import About from './views/About.vue'
import Evenements from './views/Evenements.vue'


import Button from 'primevue/button'
import InputText from 'primevue/inputtext'
import TabMenu from 'primevue/tabmenu';
import Dialog from 'primevue/dialog';
import Divider from 'primevue/divider';
import DataView from "primevue/dataview";
import Skeleton from  "primevue/skeleton";



const app = createApp(App)
app.use(PrimeVue, { ripple: true })

app.component('Button', Button)
app.component('InputText', InputText)
app.component('TabMenu', TabMenu)
app.component('Dialog', Dialog)
app.component('Divider', Divider)
app.component('DataView', DataView)
app.component('Skeleton', Skeleton)


const routes = [
  {path : "/" , component: Home},
  {path : "/about" , component: About},
  {path : "/evenements" , component: Evenements}
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes
});
app.use(router)

app.mount('#app')
