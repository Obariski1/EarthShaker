<template>
  <GMapMap
      :center="center"
      :zoom="7"
      map-type-id="terrain"
  >
    <GMapMarker
        :key="marker.id"
        v-for="marker in markers"
        :position="marker.position"
    />
  </GMapMap>
</template>
<script>
export default {
  name: 'App',
  data() {
    return {
      center: {lat: 51.093048, lng: 6.842120},
      markers: [
        {
          id: 'dfsldjl3r',
          position: {
            lat: 51.093048, lng: 6.842120
          },
        }
      ]
    }
  }
}
</script>