<template>
    <h1 class="box-title">Anmelden</h1>
    <br>
    <p><input class="input-fields" type="text" placeholder="E-Mail" v-model="email"></p>
    <br>
    <p><input class="input-fields" type="password" placeholder="Password" v-model="password"></p>
    <br>
    <p v-if="errorMsg">{{ errorMsg }}</p>
    <p><button @click="register">Anmelden</button></p>
    <p><button @click="signInWIthGoogle">Mit Google Anmelden</button></p>
    <br>
    <a href="/register">Keinen Account? Hier Registireren</a>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { getAuth, signInWithEmailAndPassword, GoogleAuthProvider, signInWithPopup } from 'firebase/auth'
import { useRouter } from 'vue-router'
const email = ref("")
const password = ref("")
const router = useRouter()
const errorMsg = ref()

const register = () => {
    signInWithEmailAndPassword(getAuth(), email.value, password.value)
        .then((data) => {
            console.log("Erfolgreich angemeldet");
            router.push('/')
        })
        .catch((error) => {
            switch (error.code) {
                case "auth/invalid-email":
                    errorMsg.value = "Falsche Email"
                    break;
                case "auth/user-not-found":
                    errorMsg.value = "Konto nicht gefunden"
                    break;
                case "auth/wrong-password":
                    errorMsg.value = "Falsches Passwort"
                    break;
                default:
                    errorMsg.value = "Email oder Passwort sind Falsch"
                    break;
            }
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