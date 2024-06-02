<template>
  <div id="app">
    <button @click="handleSignOut" v-if="isLoggedIn">Abmelden</button>
    <header>
      <h1 class="title">EarthShaker</h1>
    </header>
    <router-view></router-view>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { getAuth, onAuthStateChanged, signOut } from 'firebase/auth'
import { useRouter } from 'vue-router'

const isLoggedIn = ref(false)
const router = useRouter()

let auth;
onMounted(() => {
  auth = getAuth()
  onAuthStateChanged(auth, (user) => {
    if (user) {
      isLoggedIn.value = true
    } else {
      isLoggedIn.value = false
    }
  })
})
const handleSignOut = () => {
  signOut(auth).then(() => {
    router.push("/sign-in")
  })
}
</script>

<style>
.title {
  justify-content: center;
  display: flex;
  font-weight: bold;
  font-size: 56px;
}
</style>