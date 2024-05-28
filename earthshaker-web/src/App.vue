<template>
  <GMapMap
      :center="center"
      :zoom="7"
      map-type-id="terrain"
      style="width: 100%; height: 100vh"
  >
    <GMapMarker
        :key="marker.id"
        v-for="marker in markers"
        :position="marker.position"
    />
  </GMapMap>
</template>
<script>
import axios from 'axios';

export default {
  name: 'App',
  data() {
    return {
      center: { lat: 51.093048, lng: 6.842120 },
      markers: [],
      earthquakeData: null
    };
  },
  created() {
    axios.get('http://localhost:8080/earthquakes')
      .then(response => {
        // Die Antwort vom Backend enthält die Erdbebendaten
        this.earthquakeData = response.data;

        // Verarbeiten Sie die erhaltenen Daten und aktualisieren Sie die 'markers'-Daten im Frontend
        this.processEarthquakeData();
      })
      .catch(error => {
        console.error('Error fetching earthquake data:', error);
      });
  },
  methods: {
    processEarthquakeData() {
      if (this.earthquakeData) {
        // Extrahiere die Erdbeben aus den erhaltenen Daten
        const earthquakes = this.earthquakeData.features;

        // Iteriere über jedes Erdbeben und konvertiere es in das gewünschte Marker-Format
        earthquakes.forEach(earthquake => {
          const id = earthquake.id;
          const coordinates = earthquake.geometry.coordinates;
          const latitude = coordinates[1];
          const longitude = coordinates[0];

          // Erstelle den Marker im gewünschten Format
          const marker = {
            id: id,
            position: {
              lat: latitude,
              lng: longitude
            }
          };

          // Füge den Marker dem 'markers'-Array hinzu
          this.markers.push(marker);
        });
      }
    }
  }
};
</script>