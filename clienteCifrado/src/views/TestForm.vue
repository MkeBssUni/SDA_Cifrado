<script>
export default {
    data() {
        return {
            form: {
                name: 'Miguel',
                email: 'pepe@mail.com',
                password: 'asdasdasd',
                motivation: 'asdadasdsad',
            },
            errorMessages: {
                name: '',
                username: '',
                password: '',
                motivation: '',
            },
            showErrors: {
                name: false,
                username: false,
                password: false,
                motivation: false,
            }
        }
    },
    methods: {
        async sendData() {
            try {
                // Encripta los datos antes de enviarlos al backend
                const encryptedData = await this.encrypt(this.form.name);
                console.log("nombre cifrado",encryptedData);
                const decryptedData = await this.decrypt(encryptedData);
                console.log("nombre descifrado",decryptedData);
            } catch (error) {
                console.error('Error al enviar los datos:', error);
            }
        },
        async encrypt(plaintext) {
            const encoder = new TextEncoder();
            const keyMaterial = await this.getKeyMaterial();
            const key = await window.crypto.subtle.importKey(
                "raw",
                keyMaterial,
                { name: "AES-CBC" },
                false,
                ["encrypt"]
            );
            const iv = new Uint8Array(16); // IV estático (en este ejemplo, todo ceros)
            const encrypted = await window.crypto.subtle.encrypt(
                {
                    name: "AES-CBC",
                    iv,
                },
                key,
                encoder.encode(plaintext)
            );

            return window.btoa(String.fromCharCode.apply(null, new Uint8Array(encrypted)));
        },

        async decrypt(ciphertext) {
            const decoder = new TextDecoder();
            const keyMaterial = await this.getKeyMaterial();
            const key = await window.crypto.subtle.importKey(
                "raw",
                keyMaterial,
                { name: "AES-CBC" },
                false,
                ["decrypt"]
            );
            const iv = new Uint8Array(16); // El mismo IV estático usado para cifrar
            const encryptedData = Uint8Array.from(atob(ciphertext), c => c.charCodeAt(0));

            const decrypted = await window.crypto.subtle.decrypt(
                {
                    name: "AES-CBC",
                    iv,
                },
                key,
                encryptedData
            );

            return decoder.decode(decrypted);
        },

        async getKeyMaterial() {
            const secretKey = 'S4AUC3B0YZS3ND0'; // Clave secreta estática
            const encoder = new TextEncoder();
            const hash = await window.crypto.subtle.digest(
                {
                    name: "SHA-256",
                },
                encoder.encode(secretKey)
            );

            // Utiliza solo los primeros 128 bits (16 bytes)
            return new Uint8Array(hash).slice(0, 16);
        },
    }

}
</script>
<template>
    <div>
        <b-row>
            <b-col cols="12">
                <b-form-group label="Nombre:" label-for="name" :state="!showErrors.name"
                    :invalid-feedback="errorMessages.name">
                    <b-form-input id="name" type="text" v-model.trim="form.name"></b-form-input>
                </b-form-group>
            </b-col>
            <b-col cols="12">
                <b-form-group label="Correo:" label-for="username" :state="!showErrors.email"
                    :invalid-feedback="errorMessages.username">
                    <b-form-input id="username" type="text" v-model.trim="form.email"></b-form-input>
                </b-form-group>
            </b-col>
            <b-col cols="12">
                <b-form-group label="Contraseña:" label-for="passwordForm" :state="!showErrors.password"
                    :invalid-feedback="errorMessages.password">
                    <b-form-input id="passwordForm" type="password" v-model.trim="form.password"></b-form-input>
                </b-form-group>
            </b-col>
            <b-col cols="12">
                <b-form-group label="Motivación:" label-for="motivationForm" :state="!showErrors.motivation"
                    :invalid-feedback="errorMessages.motivation">
                    <b-form-textarea id="motivationForm" v-model.trim="form.motivation"></b-form-textarea>
                </b-form-group>
            </b-col>
            <b-col cols="6">
                <b-button block class="mt-4 d-flex justify-content-between" variant="outline-success" type="submit"
                    :disabled="(showErrors.name || showErrors.email || showErrors.password || showErrors.motivation)"
                    @click="sendData" style="width: 100%;">
                    Agregar
                    <b-icon icon="plus-circle" font-scale="1"></b-icon>
                </b-button>
            </b-col>
        </b-row>
    </div>
</template>
