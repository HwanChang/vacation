<template>
  <v-app
    style="
      height: 50%;
      width: 100%;
      position: absolute;
      background-color: #424242;
    "
  >
    <v-navigation-drawer
      color="#222222"
      app
      clipped
    >
      <v-container fluid grid-list-md>
        <v-layout>
          <v-flex>
            <router-link
              to="/home"
            >
              <v-img
                :src="require('@/static/logo.svg')"
                max-width="220px"
                max-height="40px"
              />
            </router-link>
          </v-flex>
        </v-layout>
      </v-container>
      <v-divider />
      <v-list
        dense
        nav
      >
        <v-list-item
          v-for="(item, i) in filteredManager"
          :key="i"
          :to="item.to"
          router
          exact
          active-class="primary"
        >
          <v-list-item-action>
            <v-icon style="color: white">
              {{ item.icon }}
            </v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title
              style="color: white"
              v-text="item.title"
            />
          </v-list-item-content>
        </v-list-item>
      </v-list>
      <template #append>
        <v-list-item @click="logout">
          <v-list-item-icon>
            <v-icon
              color="white"
              v-text="'mdi-account-off-outline'"
            />
          </v-list-item-icon>
          <v-list-item-content
            class="white--text text--lighten-5"
          >
            로그아웃
          </v-list-item-content>
        </v-list-item>
      </template>
    </v-navigation-drawer>
    <v-main>
      <v-container fluid fill-height>
        <nuxt />
        <snackbar />
      </v-container>
    </v-main>
    <v-footer
      inset
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
  name: 'Default',
  props: {
    expandOnHover: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      title: '휴가 관리 시스템',
      clipped: true,
      drawer: false,
      fixed: false,
      miniVariant: false,
      right: true,
      items: [
        {
          icon: 'mdi-account',
          title: '내 정보',
          to: '/profile'
        },
        {
          icon: 'mdi-clipboard-text-multiple-outline',
          title: '휴가 신청 현황',
          to: '/request/list'
        },
        {
          icon: 'mdi-beach',
          title: '휴가 신청',
          to: '/request'
        },
        {
          title: '결재',
          icon: 'mdi-clipboard-text-outline',
          to: '/approve'
        },
        {
          title: '처리',
          icon: 'mdi-file-check-outline',
          to: '/confirm'
        }
      ]
    }
  },
  computed: {
    filteredManager () {
      return this.getUser()
    }
  },
  created () {
    if (localStorage.getItem('token')) {
      this.$axios.defaults.headers.common['x-access-token'] = localStorage.getItem('token')
    } else {
      this.$router.push('/')
    }
  },
  methods: {
    logout () {
      this.$store.dispatch('user/logout')
      this.$router.push('/')
    },
    getUser () {
      if (this.$store.state.user.user.roles.length !== 0) {
        return this.items.filter(item => ((item.to === '/confirm' && this.$store.state.user.user.roles.includes('MANAGER')) || (item.to !== '/confirm')))
      } else {
        this.$store.dispatch('user/setUser')
          .then(() => {
            return this.items.filter(item => ((item.to === '/confirm' && this.$store.state.user.user.roles.includes('MANAGER')) || (item.to !== '/confirm')))
          })
          .catch(({ message }) => {
            this.$store.dispatch('snackbar/showSnackbar', { snackbarText: message, snackbarColor: 'error' })
          })
      }
    }
  }
}
</script>
