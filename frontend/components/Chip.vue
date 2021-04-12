<template>
  <v-chip
    :color="color"
    class="ma-2"
    text-color="white"
  >
    <span>{{ state }}</span>
  </v-chip>
</template>

<script>
export default {
  props: {
    state: {
      type: String,
      default: ''
    },
    color: {
      type: String,
      default: ''
    }
  }
}
</script>

<style scoped>

</style>
