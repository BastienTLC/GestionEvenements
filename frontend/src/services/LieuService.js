import axios from "axios";
import { apiConfig } from "../config/apiConfig.js";

const API_URL = apiConfig.lieu;

export default {
    getAllLieux() {
        return axios.get(`${apiConfig.lieu}`);
    },
    getLieuById(id) {
        return axios.get(`${apiConfig.lieu}/${id}`);
    },
    createLieu(lieuData) {
        return axios.post(`${apiConfig.lieu}`, lieuData);
    },
    updateLieu(id, lieuData) {
        return axios.put(`${apiConfig.lieu}/${id}`, lieuData);
    },
    deleteLieu(id) {
        return axios.delete(`${apiConfig.lieu}/${id}`);
    }
}
