<template>
  <v-app
    style="
      height: 50%;
      width: 100%;
      position: absolute;
      background-color: #424242;
    "
  >
    <v-main>
      <v-container fluid fill-height>
        <nuxt />
        <snackbar />
      </v-container>
    </v-main>
    <v-footer
      :absolute="!fixed"
      app
      color="white"
    >
      <v-spacer />
      <span>&copy; {{ new Date().getFullYear() }} <a href="https://github.com/HwanChang" target="_blank">@HwanChang</a></span>
    </v-footer>
  </v-app>
</template>

<script>
export default {
  data () {
    return {
      title: '휴가 관리 시스템',
      fixed: false
    }
  },
  created () {
    if (localStorage.getItem('token')) {
      this.$router.push('/profile')
    }
  }
}
</script>
