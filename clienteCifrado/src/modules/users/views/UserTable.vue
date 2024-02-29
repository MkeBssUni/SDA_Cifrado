<template>
    <b-container fluid>
        <b-row>
            <b-col cols="12" class="p-5">
                <b-card>
                    <template #header>
                        <h4 class="mb-0">Lista de usuarios</h4>
                    </template>
                    <b-card-body class="px-5">
                        <b-row>
                            <b-col cols="12">
                                <b-table id="my-table" :fields="fields" :items="users" :per-page="perPage" :current-page="currentPage"
                                    responsive="sm" small stripped hover></b-table>
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
</template>

<script>
import instance from "../../../config/axios";
export default {
    data() {
        return {
            perPage: 5,
            currentPage: 1,
            pageOptions: [5, 10, 15, 20],
            fields: [                
                { key: "name", label: "Nombre" },
                { key: "username", label: "Usuario" },
                { key: "motivation", label: "Motivacion" }                
            ],
            users: [{}],
        };
    },
    methods: {
        
    },
    computed: {
        rows() {
            return this.users.length;
        },
    },
    mounted() {
        this.perPage = 5;
    },
};
</script>

<style scoped></style>