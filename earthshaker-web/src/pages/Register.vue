<template>
    <h1 class="box-title">Erstelle einen Account</h1>
    <br>
    <p><input class="input-fields" type="text" placeholder="E-Mail" v-model="email"></p>
    <br>
    <p><input class="input-fields" type="password" placeholder="Password" v-model="password"></p>
    <br>
    <p><button @click="register">Registireren</button></p>
    <p><button @click="signInWIthGoogle">Mit Google Anmelden</button></p>
    <br>
    <a href="/sign-in">Hast du schon einen Account? Hier Anmelden</a>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { getAuth, createUserWithEmailAndPassword, GoogleAuthProvider, signInWithPopup } from 'firebase/auth'
import { useRouter } from 'vue-router'

const email = ref("")
const password = ref("")
const router = useRouter()

const register = () => {
    createUserWithEmailAndPassword(getAuth(), email.value, password.value)
        .then((data) => {
            console.log("Erfolgreich Registriert");
            router.push('/')
        })
}

const signInWIthGoogle = () => {
    const provider = new GoogleAuthProvider()
    signInWithPopup(getAuth(), provider).then((result) => {
        router.push("/")
    })
}
</script>

<style>
.box-title {
    margin-top: 50px;
}

.input-fields {
    border: 1px solid lightgray;
    border-radius: 6px;
}
</style>