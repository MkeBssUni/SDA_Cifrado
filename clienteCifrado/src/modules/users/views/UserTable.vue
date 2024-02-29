<template>
    <div>
        <b-container fluid>
            <b-row>
                <b-col cols="12" class="p-5">
                    <b-card>
                        <template #header>
                            <div class="d-flex justify-content-between align-items-center">
                                <h4 class="mb-0">Lista de usuarios</h4>
                                <b-button v-b-modal.modal-register variant="outline-success"
                                    class="d-flex align-items-center justify-content-center">
                                    <span>Registrar usuario</span>
                                    <b-icon icon="plus-circle" class="ms-2"></b-icon>
                                </b-button>
                            </div>
                        </template>
                        <b-card-body class="px-5">
                            <b-row>
                                <b-col cols="12">
                                    <b-table id="my-table" :fields="fields" :items="users" :per-page="perPage"
                                        :current-page="currentPage" responsive="sm" small stripped hover></b-table>
                                </b-col>
                                <b-col cols="12">
                                    <label for="perPage">Selecciona la cantidad de registros que deseas mostrar:</label>
                                    <b-form-select :options="pageOptions" v-model="perPage"
                                        class="mx-3 my-3 p-1 form-select d-inline-block" style="width: 5%;"></b-form-select>
                                </b-col>
                                <b-col cols="12" class="text-center">
                                    <b-pagination v-model="currentPage" :total-rows="rows" :per-page="perPage"
                                        aria-controls="my-table" align="center"></b-pagination>
                                    <small class="mt-3">Página actual: {{ currentPage }}</small><br />
                                    <small class="mt-3">Total de usuarios: {{ rows }}</small>
                                </b-col>
                            </b-row>
                        </b-card-body>
                    </b-card>
                </b-col>
            </b-row>
        </b-container>
        <b-modal hide-footer id="modal-register" title="Registrar nuevo usuario">
            <b-form>
                <b-row>
                    <b-col cols="12" class="mb-2">
                        <b-form-group label="Nombre:" label-for="name" :state="!showErrors.name"
                            :invalid-feedback="errorMessages.name">
                            <b-form-input id="name" type="text" v-model.trim="form.name"></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12" class="my-2">
                        <b-form-group label="Correo:" label-for="username" :state="!showErrors.username"
                            :invalid-feedback="errorMessages.username">
                            <b-form-input id="username" type="text" v-model.trim="form.username"></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12" class="my-2">
                        <b-form-group label="Contraseña:" label-for="password" :state="!showErrors.password"
                            :invalid-feedback="errorMessages.password">
                            <b-form-input id="password" type="password" v-model.trim="form.password"></b-form-input>
                        </b-form-group>
                    </b-col>
                    <b-col cols="12" class="my-2">
                        <b-form-group label="Motivación:" label-for="motivationForm" :state="!showErrors.motivation"
                            :invalid-feedback="errorMessages.motivation">
                            <b-form-textarea id="motivationForm" v-model.trim="form.motivation"></b-form-textarea>
                        </b-form-group>
                    </b-col>
                    <b-col cols="6" class="my-2">
                        <b-button block class="mt-4 d-flex justify-content-between align-items-center"
                            variant="outline-success" @click="saveUser()" style="width: 100%;">
                            Registrar
                            <b-icon icon="plus-circle" font-scale="1"></b-icon>
                        </b-button>
                    </b-col>
                    <b-col cols="6" class="my-2">
                        <b-button block class="mt-4 d-flex justify-content-between align-items-center"
                            variant="outline-danger" type="reset" @click="resetForm()" style="width: 100%;">
                            Cancelar
                            <b-icon icon="x-circle" font-scale="1"></b-icon>
                        </b-button>
                    </b-col>
                </b-row>
            </b-form>

        </b-modal>
    </div>
</template>

<script>
import instance from "../../../config/axios";
import { encrypt, decrypt } from "../../../kernel/hash/hashFunctions";

export default {
    data() {
        return {
            perPage: 5,
            currentPage: 1,
            pageOptions: [5, 10, 15, 20],
            fields: [
                { key: "name", label: "Nombre" },
                { key: "username", label: "Usuario" },
                { key: "usernameDecrypted", label: "Usuario descifrado" },
                { key: "motivation", label: "Motivacion" }
            ],
            users: [{}],
            form: {
                name: "",
                username: "",
                password: "",
                motivation: "",
            },
            errorMessages: {
                name: "",
                username: "",
                password: "",
                motivation: "",
            },
            showErrors: {
                name: false,
                username: false,
                password: false,
                motivation: false,
            },
        };
    },
    methods: {
        /* async getUsers() {
            try {
                await instance.get("/users/paged/")
                    .then((response) => {
                        this.users = response.data.data.content;
                        this.users.forEach((user) => {
                            user.usernameDecrypted = decrypt(user.username);
                            if (user.username.length > 10) {
                                user.username = user.username.substring(0, 10) + "...";
                            }
                        });
                    });

            } catch (error) {
                console.log(error);
            }
        }, */
        async getUsers() {
            try {
                const response = await instance.get("/users/paged/");
                this.users = response.data.data.content;

                // Crear un array de promesas usando map para descifrar los usernames
                const decryptPromises = this.users.map(async (user) => {
                    // Descifrar el username aquí y actualizar el usuario directamente
                    user.usernameDecrypted = await decrypt(user.username);

                    if (user.username.length > 10) {
                        user.username = user.username.substring(0, 10) + "...";
                    }
                    return user; // Retornar el usuario actualizado
                });

                // Esperar a que todas las promesas se resuelvan
                this.users = await Promise.all(decryptPromises);
            } catch (error) {
                console.log('Error al obtener los usuarios:', error);
            }
        },
        validateForm() {
            let isValid = true;
            if (this.form.name === "") {
                this.errorMessages.name = "El nombre es requerido";
                this.showErrors.name = true;
                isValid = false;
            } else {
                this.errorMessages.name = "";
                this.showErrors.name = false;
            }
            if (this.form.username === "") {
                this.errorMessages.username = "El usuario es requerido";
                this.showErrors.username = true;
                isValid = false;
            } else {
                this.errorMessages.username = "";
                this.showErrors.username = false;
            }
            if (this.form.password === "") {
                this.errorMessages.password = "La contraseña es requerida";
                this.showErrors.password = true;
                isValid = false;
            } else {
                this.errorMessages.password = "";
                this.showErrors.password = false;
            }
            if (this.form.motivation === "") {
                this.errorMessages.motivation = "La motivación es requerida";
                this.showErrors.motivation = true;
                isValid = false;
            } else {
                this.errorMessages.motivation = "";
                this.showErrors.motivation = false;
            }
            return isValid;
        },
        async saveUser() {
            if (this.validateForm()) {
                
                this.form.username = await encrypt(this.form.username);
                this.form.password = await encrypt(this.form.password);
                this.form.motivation = await encrypt(this.form.motivation);
                this.form.name = await encrypt(this.form.name);

                await instance.post("/users/", this.form).then((response) => {
                    console.log(response.data.data);
                    this.getUsers();
                }).catch((error) => {
                    console.log(error);
                });
                this.$bvModal.hide("modal-register");
            } else {
                alert("Favor de llenar los campos requeridos");
            }
        },
        resetForm() {
            this.form = {
                name: "",
                username: "",
                password: "",
                motivation: "",
            };
            this.errorMessages = {
                name: "",
                username: "",
                password: "",
                motivation: "",
            };
            this.showErrors = {
                name: false,
                username: false,
                password: false,
                motivation: false,
            };
        },
    },
    computed: {
        rows() {
            return this.users.length;
        },
    },
    mounted() {
        this.perPage = 5;
        this.getUsers();
    },
};
</script>

<style scoped></style>