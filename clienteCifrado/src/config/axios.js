import axios from "axios";


const SERVER_URL = 'http://localhost:8080/api';

const instance = axios.create({
    baseURL:SERVER_URL,
    timeout:30_000
});

export default instance;