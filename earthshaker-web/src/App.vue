<template>
  <div id="app">
    <GmapMap
      :center="center"
      :zoom="7"
      style="width: 100%; height: 500px"
    >
      <GmapMarker
        v-for="earthquake in earthquakes"
        :key="earthquake.id"
        :position="{ lat: earthquake.geometry.coordinates[1], lng: earthquake.geometry.coordinates[0] }"
        @click="showInfo(earthquake)"
      />
    </GmapMap>
    <div v-if="selectedEarthquake">
      <h2>Details</h2>
      <p>{{ selectedEarthquake.properties.title }}</p>
      <p>{{ selectedEarthquake.properties.mag }} magnitude</p>
      <p>{{ new Date(selectedEarthquake.properties.time) }}</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { defineComponent } from 'vue';
import { GmapMap, GmapMarker } from 'vue-google-maps';

export default defineComponent({
  components: {
    GmapMap,
    GmapMarker,
  },
  data() {
    return {
      center: { lat: 37.7749, lng: -122.4194 },
      earthquakes: [],
      selectedEarthquake: null,
    };
  },
  created() {
    axios.get('http://localhost:8080/earthquakes')
      .then(response => {
        this.earthquakes = response.data.features;
      });
  },
  methods: {
    showInfo(earthquake) {
      this.selectedEarthquake = earthquake;
    },
  },
});
</script>
