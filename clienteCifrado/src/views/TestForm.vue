<script>

const secretKey= 'sauceboyz2'

export default {
    data() {
        return {
            form: {
                name: '',
                email: '',
                password: '',
                motivation: '',
            },
            errorMessages: {
                name: '',
                email: '',
                password: '',
                motivation: '',
            },
            showErrors: {
                name: false,
                email: false,
                password: false,
                motivation: false,
            }
        }
    },
    methods:{
        craeteToken(){
            console.log("va a crear el token")
            const token = jwt.sign(this.form, secretKey);
            console.log(token);
            console.log("acá está el token descifrfado",decode(token));
        },
        printForm(){
            console.log("este es el form",this.form);
            this.craeteToken()
        }
    }

}
</script>
<template>
    <div>
        <b-row>
                <b-col cols="12">
                    <b-form-group label="Nombre:" label-for="nameForm" :state="!showErrors.name"
                        :invalid-feedback="errorMessages.name">
                        <b-form-input id="nameForm" type="text" v-model.trim="form.name"></b-form-input>
                    </b-form-group>
                </b-col>
                <b-col cols="12">
                    <b-form-group label="Correo:" label-for="emailForm" :state="!showErrors.email"
                        :invalid-feedback="errorMessages.email">
                        <b-form-input id="emailForm" type="email" v-model.trim="form.email"></b-form-input>
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
                        @click="printForm"  
                        style="width: 100%;">
                        Agregar
                        <b-icon icon="plus-circle" font-scale="1"></b-icon>
                    </b-button>
                </b-col>
            </b-row>
</div></template>
