import Vue from 'vue'
import App from './App.vue'
<<<<<<< Updated upstream
=======
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'



//axios
import axios from 'axios'
import VueAxios from 'vue-axios'

// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)
//Axios
Vue.use(VueAxios, axios)
>>>>>>> Stashed changes



import './assets/main.css'

new Vue({
  render: (h) => h(App)
}).$mount('#app')
