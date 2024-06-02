import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import VueGoogleMaps from '@fawmi/vue-google-maps';
import App from './App.vue'
import router from './router'
// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyD4w7nxUYjOnJw1YG-k7Lv9PMBNOlwipNs",
  authDomain: "earthshaker-af945.firebaseapp.com",
  projectId: "earthshaker-af945",
  storageBucket: "earthshaker-af945.appspot.com",
  messagingSenderId: "252671849498",
  appId: "1:252671849498:web:89e8385194dfd4d2f28da3",
  measurementId: "G-ST3SDYZH15"
};

initializeApp(firebaseConfig);

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.use(VueGoogleMaps, {
  load: {
    key: 'AIzaSyAVkn4KLClswYYJTf2bQg4ndeZxVFxwFbU', // Ersetzen Sie durch Ihren API-Schlüssel
  },
}).mount('#app');
