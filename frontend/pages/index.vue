<template>
  <v-container fluid grid-list-md>
    <v-layout row wrap>
      <v-flex md10 offset-md1>
        <v-card width="400" class="mx-auto">
          <v-card-title>
            <v-container fluid>
              <v-row class="pb-8">
                <v-img
                  v-if="$vuetify.breakpoint.mdAndUp"
                  :src="require('static/logo_login.svg')"
                  max-width="300px"
                  max-height="50px"
                />
                <v-img
                  v-else-if="$vuetify.breakpoint.smAndUp"
                  :src="require('static/logo_login.svg')"
                  max-width="220px"
                  max-height="50px"
                />
                <v-img
                  v-else
                  :src="require('static/logo_login.svg')"
                  max-width="180px"
                  max-height="50px"
                />
              </v-row>
              <v-row>
                <h2>Login</h2>
              </v-row>
            </v-container>
          </v-card-title>
          <v-card-text>
            <v-form>
              <v-text-field
                v-model="email"
                :rules="emailRules"
                label="Email address"
                prepend-icon="mdi-email"
              />
              <v-text-field
                v-model="password"
                :rules="passwordRules"
                :type="showPassword ? 'text' : 'password'"
                :counter="15"
                label="Password"
                prepend-icon="mdi-lock"
                :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="showPassword = !showPassword"
              />
              <v-btn
                class="mt-4"
                color="success"
                block
                @click="signIn"
              >
                {{ signin }}
              </v-btn>
            </v-form>
          </v-card-text>
          <v-divider />
          <v-card-actions>
            <v-spacer />
            <router-link
              to="/signup"
              style="text-decoration: none"
            >
              {{ signup }}
            </router-link>.
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  layout: 'user',
  data () {
    return {
      signin: '로그인',
      signup: 'Create an account',
      email: '',
      emailRules: [
        v => !!v || '이메일을 입력해주세요.',
        v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || '이메일 형식에 맞지 않습니다.'
      ],
      password: '',
      passwordRules: [
        v => !!v || '패스워드를 입력해주세요.',
        v => (v.length >= 8 && v.length <= 15) || '패스워드는 8자 이상 15자 이하로 입력해주세요.'
      ],
      showPassword: false
    }
  },
  head: {
    title: '로그인'
  },
  methods: {
    signIn () {
      this.$store.dispatch('user/login', { principal: this.email, credentials: this.password })
        .then(() => this.$router.push('/profile'))
        .catch(({ message }) => {
          this.$store.dispatch('snackbar/showSnackbar', { snackbarText: message, snackbarColor: 'error' })
        })
    }
  }
}
</script>
