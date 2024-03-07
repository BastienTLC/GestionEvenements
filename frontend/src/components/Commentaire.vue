<script setup>
import {defineProps, ref} from 'vue';
import CommentaireService from "@/services/CommentaireService.js";
import {useToast} from "primevue/usetoast";

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

const loadComments = async () => {
  try {
    const response = await CommentaireService.getCommentsByEvenementId(newComment.value.event_id);
    comments.value = response.data;
  } catch (error) {
    console.error('Error loading comments:', error);
  }
};
const addComment = async () => {
  try {
    console.log(newComment.value);
    const response = await CommentaireService.createComment(newComment.value);
    await loadComments();
  } catch (error) {
    console.error('Error adding comment:', error);
  }
};

loadComments();



</script>

<template>
  <div>
    <Toast />
    <DataTable :value="comments">
      <Column field="member_id" header="ID"></Column>
      <Column field="message" header="Comment"></Column>
    </DataTable>

    <InputText v-model="newComment.message" placeholder="Enter a comment" />
    <Button label="Submit" @click="addComment" />
  </div>
</template>

<style scoped>
/* Add your styles here */
</style>
