<template>
  <div>
    <header>
      <h1>EarthShaker</h1>
    </header>
    <div class="filters">
      <label>Date:</label>
      <input type="date" v-model="dateFilter">

      <label>Tsunami:</label>
      <input type="checkbox" v-model="tsunamiFilter">

      <div>
        <h2>Stärke</h2>
        <label>&lt; 3</label>
        <input type="checkbox" v-model="MagTo3">
        <label>3 - 3.9</label>
        <input type="checkbox" v-model="Mag3">
        <label>4 - 4.9</label>
        <input type="checkbox" v-model="Mag4">
        <label>5 - 5.9</label>
        <input type="checkbox" v-model="Mag5">
        <label>6 - 6.9</label>
        <input type="checkbox" v-model="Mag6">
        <label>7+</label>
        <input type="checkbox" v-model="Mag7plus">

      </div>
    </div>
    <GMapMap :center="center" :zoom="2" map-type-id="terrain" style="width: 100%; height: 90vh">
      <GMapMarker :key="marker.id" v-for="marker in filteredMarkers" :position="marker.position" />
    </GMapMap>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, watch } from 'vue';
import { lookUp } from 'geojson-places';
const result = lookUp(41.652349, -4.728602);
console.log(result);
export default {
  name: 'App',
  data() {
    return {
      center: { lat: 51.093048, lng: 6.842120 },
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

    };
  },
  created() {
    axios.get('http://localhost:8082/earthquakes')
      .then(response => {
        this.earthquakeData = response.data;
        this.processEarthquakeData();
      })
      .catch(error => {
        console.error('Error fetching earthquake data:', error);
      });

    // Überwache Änderungen im tsunamiFilter und aktualisiere die Komponente
    watch(this.tsunamiFilter, (newVal) => {
      this.$forceUpdate();
    });
  },
  computed: {
    filteredMarkers() {
      if (!this.earthquakeData) return [];

      return this.markers.filter(marker => {
        var filterSatisfied
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

        // Check if the magnitude falls within any of the selected ranges
        if ((this.MagTo3 && magnitude <= 3) ||
          (this.Mag3 && magnitude >= 3 && magnitude <= 3.9) ||
          (this.Mag4 && magnitude >= 4 && magnitude <= 4.9) ||
          (this.Mag5 && magnitude >= 5 && magnitude <= 5.9) ||
          (this.Mag6 && magnitude >= 6 && magnitude <= 6.9) ||
          (this.Mag7plus && magnitude >= 7)) {
          return true; // Include the marker if it falls within any selected range
        }
        else {
          return false;
        }
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



          const marker = {
            id: id,
            position: {
              lat: latitude,
              lng: longitude
            },
            time: time,
            tsunami: tsunami,
            magnitude: magnitude,
          };
          this.markers.push(marker);
        });
      }
    },
    async performReverseGeocoding() {
    try {
      const result = await lookUp(41.652349, -4.728602);
      console.log(result);
      // Handle the result here
    } catch (error) {
      console.error('Error performing reverse geocoding:', error);
    }
  }
  }
};
</script>

<style>
.filters {
  margin-bottom: 20px;
}
</style>
