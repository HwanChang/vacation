<template>
  <v-container>
    <v-layout>
      <v-flex>
        <v-card width="400" class="mx-auto mt-5">
          <v-card-title class="pb-2">
            <h1>Sign Up</h1>
          </v-card-title>
          <v-card-text>
            <v-form>
              <v-text-field
                v-model="email"
                :rules="emailRules"
                label="Email address"
                prepend-icon="mdi-email"
                :append-icon="checkEmail ? 'mdi-checkbox-multiple-marked' : 'mdi-checkbox-multiple-blank-outline'"
                @click:append="checkMailExists"
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
              <v-text-field
                v-model="confirmPassword"
                :rules="passwordConfirmRules"
                :type="showConfirmPassword ? 'text' : 'password'"
                :counter="15"
                label="Confirm password"
                prepend-icon="mdi-lock"
                :append-icon="showConfirmPassword ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="showConfirmPassword = !showConfirmPassword"
              />
              <v-text-field
                v-model="name"
                :rules="nameRules"
                :counter="10"
                label="User name"
                prepend-icon="mdi-account-circle"
              />
              <v-text-field
                v-model="phone"
                :rules="phoneRules"
                label="Phone"
                prepend-icon="mdi-cellphone"
              />
              <v-btn
                class="mt-4"
                color="success"
                block
                :disabled="!(checkEmail && (password === confirmPassword) && password && name && phone)"
                @click="signUp"
              >
                {{ signup }}
              </v-btn>
            </v-form>
          </v-card-text>
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
      signup: 'Create account',
      email: '',
      emailRules: [
        v => !!v || '이메일을 입력해주세요.',
        v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || '이메일 형식에 맞지 않습니다.'
      ],
      checkEmail: false,
      password: '',
      passwordRules: [
        v => !!v || '패스워드를 입력해주세요.',
        v => (v.length >= 8 && v.length <= 15) || '패스워드는 8자 이상 15자 이하로 입력해주세요.'
      ],
      showPassword: false,
      confirmPassword: '',
      passwordConfirmRules: [
        v => !!v || '패스워드를 입력해주세요.',
        v => (v.length >= 8 && v.length <= 15) || '패스워드는 8자 이상 15자 이하로 입력해주세요.',
        v => (v.password === v.confirmPassword) || '패스워드가 일치하지 않습니다.'
      ],
      showConfirmPassword: false,
      name: '',
      nameRules: [
        v => !!v || '이름을 입력해주세요.'
      ],
      phone: '',
      phoneRules: [
        v => !!v || '핸드폰 번호를 입력해주세요.',
        v => /^\d{3}-\d{3,4}-\d{4}$/.test(v) || '핸드폰 번호 형식에 맞지 않습니다.'
      ],
      joinResponse: {
        token: '',
        user: {
          userId: '',
          email: '',
          name: '',
          phone: '',
          loginCount: '',
          lastLoginAt: ''
        }
      }
    }
  },
  head: {
    title: '회원가입'
  },
  methods: {
    async checkMailExists () {
      await this.$axios.post('v1/user/exists', { email: this.email }).then((res) => {
        this.checkEmail = !res.data
        if (this.checkEmail) {
          this.$store.dispatch('snackbar/showSnackbar', { snackbarText: '사용 가능한 이메일입니다.', snackbarColor: 'success' })
        } else {
          this.$store.dispatch('snackbar/showSnackbar', { snackbarText: '사용할 수 없는 이메일입니다.', snackbarColor: 'error' })
        }
      }).catch((reason) => {
        this.$store.dispatch('snackbar/showSnackbar', { snackbarText: reason, snackbarColor: 'error' })
      })
    },
    signUp () {
      this.$store.dispatch('user/signup', { email: this.email, password: this.password, name: this.name, phone: this.phone })
        .then(() => this.$router.push('/profile'))
        .catch(({ message }) => {
          this.$store.dispatch('snackbar/showSnackbar', { snackbarText: message, snackbarColor: 'error' })
        })
    }
  }
}
</script>
