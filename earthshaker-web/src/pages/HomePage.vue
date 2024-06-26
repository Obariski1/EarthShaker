<template>
  <div>
    <h2 class="filter-title">Filter</h2>
    <div class="filters">
      <div>
        <h2>Datum</h2>
        <input class="filter-input" type="date" v-model="dateFilter">
      </div>

      <div>
        <h2>Stärke</h2>
        <label>&lt;3</label>
        <input type="checkbox" v-model="MagTo3">
        <label>3-3.9</label>
        <input type="checkbox" v-model="Mag3">
        <label>4-4.9</label>
        <input type="checkbox" v-model="Mag4">
        <label>5-5.9</label>
        <input type="checkbox" v-model="Mag5">
        <label>6-6.9</label>
        <input type="checkbox" v-model="Mag6">
        <label>7+</label>
        <input type="checkbox" v-model="Mag7plus">
      </div>

      <div>
        <h2>Tsunami</h2>
        <input type="checkbox" v-model="tsunamiFilter">
      </div>

      <div>
        <h2>Stadt/Land</h2>
        <input class="filter-input" type="text" v-model="placeFilter" placeholder="Stadt/Land eingeben ...">
      </div>
    </div>
    <div class="map-box">
      <GMapMap :center="center" :zoom="2" map-type-id="terrain" class="map">
        <GMapMarker :key="marker.id" v-for="marker in filteredMarkers" :position="marker.position"
          @click="fetchEarthquakeData(marker)" />
        <GMapInfoWindow v-if="selectedMarker" :position="selectedMarker.position" :opened="infoWindowOpened">
          <div>
            <h3 class="pop-up-title" v-if="earthquakeData">{{ earthquakeData.title }}</h3>
            <p v-if="earthquakeData">Stadt: {{ earthquakeData.place }}</p>
            <p v-if="earthquakeData">Magnitude: {{ earthquakeData.mag }}</p>
            <p v-if="earthquakeData">Datum: {{ formattedDate }}</p>
            <p v-if="earthquakeData">Url: <a :href="earthquakeData.url" target="_blank">{{ earthquakeData.url }}</a>
            </p>
          </div>
        </GMapInfoWindow>
      </GMapMap>
    </div>

    <div class="footer-box">
      <div class="api-info">
        <p class="title-api">{{ titleApi }}</p>
        <p>Version Api: {{ verisonApi }} </p>
        <p>Status Api: {{ statusApi }} </p>
      </div>
      <div>
        <p>@ Elias Elmer, Giovanni Palermo, Maël Cabon</p>
      </div>
    </div>
    <div class="licence-box">
      <a href="/licences">Lizenzen von Drittanbietern</a>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { watch } from 'vue';

export default {
  name: 'HomePage',
  data() {
    return {
      center: { lat: 51.093048, lng: 6.842120 },
      zoom: 2,
      markers: [],
      earthquakeData: null,
      dateFilter: null,
      tsunamiFilter: null,
      MagTo3: true,
      Mag3: true,
      Mag4: true,
      Mag5: true,
      Mag6: true,
      Mag7plus: true,
      selectedMarker: null,
      infoWindowOpened: false,
      formattedDate: null,
      placeFilter: '',
      verisonApi: null,
      statusApi: null,
      titleApi: null,
    };
  },
  created() {
    axios.get('http://localhost:8082/earthquakes')
      .then(response => {
        const statusMapping = {
          200: 'OK',
          201: 'Erstellt',
          204: 'Kein Inhalt',
          400: 'Schlechte Anfrage',
          401: 'Nicht autorisiert',
          403: 'Verboten',
          404: 'Nicht gefunden',
          500: 'Interner Serverfehler'
        };
        this.earthquakeData = response.data;
        this.titleApi = response.data.metadata.title
        this.verisonApi = response.data.metadata.api;
        const statusCode = response.data.metadata.status;
        const statusWord = statusMapping[statusCode] || response.data.metadata.status;
        this.statusApi = statusWord;
        this.processEarthquakeData();
      })

    watch(this.tsunamiFilter, (newVal) => {
      this.$forceUpdate();
    });
  },
  computed: {
    filteredMarkers() {
      if (!this.earthquakeData) return [];

      return this.markers.filter(marker => {
        if (this.dateFilter) {
          const date = new Date(marker.time);
          const dateString = date.toISOString().split('T')[0];
          if (dateString !== this.dateFilter) {
            return false;
          }
        }

        if (this.tsunamiFilter && marker.tsunami !== 1) {
          return false;
        }

        const magnitude = marker.magnitude;

        return (
          (this.MagTo3 && magnitude <= 3) ||
          (this.Mag3 && magnitude >= 3 && magnitude <= 3.9) ||
          (this.Mag4 && magnitude >= 4 && magnitude <= 4.9) ||
          (this.Mag5 && magnitude >= 5 && magnitude <= 5.9) ||
          (this.Mag6 && magnitude >= 6 && magnitude <= 6.9) ||
          (this.Mag7plus && magnitude >= 7)
        ) && marker.place.toLowerCase().includes(this.placeFilter.toLowerCase());;

      });
    }
  },
  methods: {
    processEarthquakeData() {
      if (this.earthquakeData) {
        const earthquakes = this.earthquakeData.features;

        earthquakes.forEach(earthquake => {
          const id = earthquake.id;
          const coordinates = earthquake.geometry.coordinates;
          const latitude = coordinates[1];
          const longitude = coordinates[0];
          const time = earthquake.properties.time;
          const tsunami = earthquake.properties.tsunami;
          const magnitude = earthquake.properties.mag;
          const place = earthquake.properties.place;

          const marker = {
            id: id,
            position: {
              lat: latitude,
              lng: longitude
            },
            time: time,
            tsunami: tsunami,
            magnitude: magnitude,
            place: place
          };
          this.markers.push(marker);
        });
      }
    },
    async fetchEarthquakeData(marker) {
      this.infoWindowOpened = false;
      this.selectedMarker = marker;

      const response = await axios.get(`http://localhost:8082/earthquakes/${marker.id}`);
      this.earthquakeData = response.data.properties;

      const timeStamp = this.earthquakeData.time;
      const date = new Date(timeStamp)
      const formatDate = date.toLocaleDateString();
      this.formattedDate = formatDate

      this.infoWindowOpened = true;
    },
  }
};
</script>

<style>
.filter-title {
  font-size: 24px;
}

.filters {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 25px;
  background-color: rgba(231, 231, 231, 0.815);
  padding: 20px;
  border-radius: 15px;
}

.filter-input {
  border: 1px solid lightgray;
  border-radius: 6px;
}

.map-box {
  margin-bottom: 25px;
}

.map {
  width: 100%;
  height: 90vh
}

.pop-up-title {
  display: flex;
  justify-content: center;
}

.footer-box {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  margin-bottom: 25px;
  gap: 20px;
}

.api-info {
  background-color: rgba(231, 231, 231, 0.815);
  padding: 20px;
  border-radius: 15px;
}

.title-api {
  font-weight: bold;
}

.licence-box {
  display: flex;
  justify-content: center;
  margin-bottom: 10px;
}
</style>