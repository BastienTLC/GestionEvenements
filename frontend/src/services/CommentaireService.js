import axios from "axios";
import {apiConfig} from "@/config/apiConfig.js";

export default {
    getAllComments() {
        return axios.get(`${apiConfig.commentaire}`);
    },
    getCommentById(id) {
        return axios.get(`${apiConfig.commentaire}/${id}`);
    },
    getCommentsByEvenementId(id) {
        return axios.get(`${apiConfig.commentaire}/evenement/${id}`);
    },
    createComment(commentData) {
        return axios.post(`${apiConfig.commentaire}`, commentData);
    },
    updateComment(id, commentData) {
        return axios.put(`${apiConfig.commentaire}/${id}`, commentData);
    },
    deleteComment(id) {
        return axios.delete(`${apiConfig.commentaire}/${id}`);
    }
};
