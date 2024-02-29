import axios from "axios";
import { apiConfig } from "../config/apiConfig.js";


const API_URL = apiConfig.evenement;

export default {
    getAllEvenements() {
        return axios.get(`${API_URL}`);
    },
    getEvenementById(id) {
        return axios.get(`${API_URL}/${id}`);
    },
    createEvenement(evenementData) {
        return axios.post(`${API_URL}`, evenementData);
    },
    updateEvenement(id, evenementData) {
        return axios.put(`${API_URL}/${id}`, evenementData);
    },
    deleteEvenement(id) {
        return axios.delete(`${API_URL}/${id}`);
    },
    getEvenementMembers(id) {
        return axios.get(`${API_URL}/${id}/participants`);
    },
    getEvenementLieu(id) {
        return axios.get(`${API_URL}/${id}/lieu`);
    }
}
