import axios from "axios";
import {apiConfig} from "@/config/apiConfig.js";

const API_URL = apiConfig.membre;

export default {
    getAllMembres() {
        return axios.get(`${API_URL}`);
    },
    getMembreById(id) {
        return axios.get(`${API_URL}/${id}`);
    },
    createMembre(membreData) {
        return axios.post(`${API_URL}`, membreData);
    },
    updateMembre(id, membreData) {
        return axios.put(`${API_URL}/${id}`, membreData);
    },
    deleteMembre(id) {
        return axios.delete(`${API_URL}/${id}`);
    }
}