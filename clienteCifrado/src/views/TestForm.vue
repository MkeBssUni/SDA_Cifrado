<script>
import sjcl from 'sjcl';

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
        /* encryptData(data) {
            const password = 'S4AUC3B0YZS3ND0';

            // Función para encriptar un campo
            const encryptField = (field) => {
                const encryptedField = sjcl.encrypt(password, String(field), { mode: 'ecb' });
                const parsedResult = JSON.parse(encryptedField);
                return parsedResult.ct;
            };

            const formattedData = {
                name: encryptField(data.name),
                email: encryptField(data.email),
                password: encryptField(data.password),
                motivation: encryptField(data.motivation),
            };

            return formattedData;
        },
        decryptData(encryptedData) {
            // Utiliza SJCL para desencriptar los datos
            const password = 'S4AUC3B0YZS3ND0';
            const decryptedData = sjcl.decrypt(password, JSON.stringify({ ct: encryptedData, iv: '' }), { mode: 'ecb' });

            return JSON.parse(decryptedData);
        }, */

        async sendData() {
            try {
                // Encripta los datos antes de enviarlos al backend
                const encryptedData = this.encryptData(this.form);
                console.log(encryptedData);
            } catch (error) {
                console.error('Error al enviar los datos:', error);
            }
        },
        encryptData(data) {
            const password = 'S4AUC3B0YZS3ND0';

            // Función para encriptar un campo
            const encryptField = (field) => {
                const encryptedField = sjcl.encrypt(password, String(field), { mode: 'ecb' });
                return encryptedField;
            };

            const formattedData = {
                name: encryptField(data.name),
                email: encryptField(data.email),
                password: encryptField(data.password),
                motivation: encryptField(data.motivation),
            };

            return formattedData;
        },
        decryptData(encryptedData) {
            // Utiliza SJCL para desencriptar los datos
            const password = 'S4AUC3B0YZS3ND0';
            const decryptedData = sjcl.decrypt(password, encryptedData);

            // Modifica el resultado para que sea compatible con el backend
            return JSON.stringify({ ct: decryptedData, iv: '' });
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
