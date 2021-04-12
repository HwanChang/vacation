<template>
  <base-material-snackbar
    v-model="snackbar"
    :type="snackbarColor"
    v-bind="{
      top: true,
      right: true
    }"
  >
    {{ snackbarText }}
  </base-material-snackbar>
</template>

<script>
export default {
  data () {
    return {
      snackbar: false,
      snackbarText: '',
      snackbarColor: 'info'
    }
  },

  created () {
    this.$store.subscribe((mutation, state) => {
      if (mutation.type === 'snackbar/setSnackbar') {
        this.snackbar = true
        this.snackbarText = state.snackbar.snackbarText
        this.snackbarColor = state.snackbar.snackbarColor
      }
    })
  }
}
</script>
