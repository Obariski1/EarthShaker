import { createApp } from 'vue';
import * as VueGoogleMaps from 'vue-google-maps';

const app = createApp(App);
app.use(VueGoogleMaps, {
  load: {
    key: 'YOUR_GOOGLE_MAPS_API_KEY',
  },
});
app.mount('#app');
