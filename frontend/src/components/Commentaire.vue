<script setup>
import {defineProps, ref} from 'vue';
import CommentaireService from "@/services/CommentaireService.js";
import {useToast} from "primevue/usetoast";
import MembreService from "@/services/MembreService.js";

const toast = useToast();

const props = defineProps({
  membreId: {
    type: Number,
    required: true
  },
  evenementId: {
    type: Number,
    required: true
  }
});

const comments = ref([]);
const newComment = ref({
  message: '',
  member_id: props.membreId,
  event_id: props.evenementId
});
const currentMembre = ref({});

const loadComments = async () => {
  try {
    const response = await CommentaireService.getCommentsByEvenementId(newComment.value.event_id);
    comments.value = response.data;
    if (comments.value.length === 0) {
      return;
    }
    comments.value.forEach(async (comment) => {
      console.log(comment.member_id);
      comment.membre = await loadMembre(comment.member_id);
    });
    console.log(comments.value)
  } catch (error) {
    toast.add({severity: 'error', summary: 'Error loading comments', detail: error.message});
  }
};

const loadMembre = async (id) => {
  try {
    const response = await MembreService.getMembreById(id);
    currentMembre.value = response.data;
  } catch (error) {
    toast.add({severity: 'error', summary: 'Error loading comments', detail: error.message});
  }
  return currentMembre.value;
};
const addComment = async () => {
  try {
    console.log(newComment.value);
    const response = await CommentaireService.createComment(newComment.value);
    toast.add({severity: 'success', summary: 'Success', detail: 'Commentaire ajouté avec succes'});
    await loadComments();
  } catch (error) {
    toast.add({severity: 'error', summary: 'Error adding comment', detail: error.message});
    console.error('Error adding comment:', error);
  }
};

loadComments();



</script>

<template>
  <div>
    <Toast />
    <DataTable :value="comments">
      <Column field="membre.nom" header="ID"></Column>
      <Column field="message" header="Comment"></Column>
    </DataTable>

    <InputText v-model="newComment.message" placeholder="Enter a comment" />
    <Button label="Submit" @click="addComment" />
  </div>
</template>

<style scoped>
/* Add your styles here */
</style>
