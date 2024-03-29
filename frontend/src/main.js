import './assets/main.css'
import { createApp } from 'vue'
import App from './App.vue'
import 'primevue/resources/themes/aura-dark-teal/theme.css'
import PrimeVue from 'primevue/config'
import {createRouter, createWebHistory} from "vue-router";
import 'primevue/resources/primevue.min.css'
import 'primeicons/primeicons.css'
import 'primeflex/primeflex.css'

import Home from './views/Home.vue'
import About from './views/Lieux.vue'
import Evenements from './views/Evenements.vue'
import EvenementDetails from "@/views/EvenementDetails.vue";


import Button from 'primevue/button'
import InputText from 'primevue/inputtext'
import TabMenu from 'primevue/tabmenu';
import Dialog from 'primevue/dialog';
import Divider from 'primevue/divider';
import DataView from "primevue/dataview";
import Skeleton from  "primevue/skeleton";
import TabView from 'primevue/tabview';
import TabPanel from 'primevue/tabpanel';
import Card from 'primevue/card';
import DataTable from "primevue/datatable";
import Column from "primevue/column";
import Avatar from "primevue/avatar";
import Fieldset from "primevue/fieldset";
import ProgressSpinner from 'primevue/progressspinner';
import Stepper from 'primevue/stepper';
import StepperPanel from 'primevue/stepperpanel';
import Calendar from "primevue/calendar";
import InputNumber from 'primevue/inputnumber';
import Dropdown from 'primevue/dropdown';
import Toast from 'primevue/toast';
import ContextMenu from 'primevue/contextmenu';
import ConfirmPopup from 'primevue/confirmpopup';
import ConfirmDialog from 'primevue/confirmdialog';
import ToastService from 'primevue/toastservice';
import ConfirmationService from 'primevue/confirmationservice';

import Lieux from "@/views/Lieux.vue";






const app = createApp(App)
app.use(PrimeVue, { ripple: true })

app.component('Button', Button)
app.component('InputText', InputText)
app.component('TabMenu', TabMenu)
app.component('Dialog', Dialog)
app.component('Divider', Divider)
app.component('DataView', DataView)
app.component('Skeleton', Skeleton)
app.component('TabView', TabView)
app.component('TabPanel', TabPanel)
app.component('Card', Card)
app.component('DataTable', DataTable)
app.component('Column', Column)
app.component('Avatar', Avatar)
app.component('Fieldset', Fieldset)
app.component('ProgressSpinner', ProgressSpinner)
app.component('Stepper', Stepper)
app.component('StepperPanel', StepperPanel)
app.component('Calendar', Calendar)
app.component('InputNumber', InputNumber)
app.component('Dropdown', Dropdown)
app.component('Toast', Toast)
app.component('ContextMenu', ContextMenu)
app.component('ConfirmPopup', ConfirmPopup)
app.component('ConfirmDialog', ConfirmDialog)
app.use(ToastService);
app.use(ConfirmationService);


const routes = [
  {path : "/" , component: Home},
  {path : "/lieux" , component: Lieux},
  {path : "/evenements" , component: Evenements},
  { path: "/evenements/:id", component: EvenementDetails },
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes
});
app.use(router)

app.mount('#app')
