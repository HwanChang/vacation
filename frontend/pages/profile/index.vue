<template>
  <v-container fluid grid-list-md fill-height>
    <v-row justify="center">
      <v-layout row wrap>
        <v-flex md10 offset-md1>
          <base-material-card
            width="800"
          >
            <template #heading>
              <div class="display-2 font-weight-light">
                User Profile
              </div>
              <div class="subtitle-1 font-weight-light">
                Update your profile
              </div>
            </template>
            <v-form>
              <v-container class="py-0">
                <v-row>
                  <v-col
                    cols="12"
                    md="12"
                  >
                    <v-text-field
                      v-model="$store.state.user.user.email"
                      prepend-icon="mdi-email"
                      disabled
                    />
                  </v-col>
                  <v-col
                    cols="12"
                    md="12"
                  >
                    <v-text-field
                      v-model="$store.state.user.user.name"
                      :rules="rule.name"
                      :counter="10"
                      prepend-icon="mdi-account-circle"
                    />
                  </v-col>
                  <v-col
                    cols="12"
                    md="12"
                  >
                    <v-text-field
                      v-model="$store.state.user.user.phone"
                      :rules="rule.phone"
                      prepend-icon="mdi-cellphone"
                    />
                  </v-col>
                  <v-col
                    cols="12"
                    class="text-right"
                  >
                    <v-btn
                      color="success"
                      class="mr-0"
                      @click="updateProfile"
                    >
                      Update Profile
                    </v-btn>
                  </v-col>
                </v-row>
              </v-container>
            </v-form>
          </base-material-card>
        </v-flex>
      </v-layout>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data () {
    return {
      title: {
        info: '내 정보',
        password: '패스워드 변경'
      },
      rule: {
        name: [
          v => !!v || '이름을 입력해주세요.'
        ],
        phone: [
          v => !!v || '핸드폰 번호를 입력해주세요.',
          v => /^\d{3}-\d{3,4}-\d{4}$/.test(v) || '핸드폰 번호 형식에 맞지 않습니다.'
        ]
      },
      button: {
        changeInfo: '정보 변경',
        changePassword: '패스워드 변경'
      }
    }
  },
  head: {
    title: '내 정보'
  },
  methods: {
    async updateProfile () {
      await this.$axios.patch('v1/user', { name: this.$store.state.user.user.name, phone: this.$store.state.user.user.phone }).then((res) => {
        this.$store.state.user.user.name = res.data.name
        this.$store.state.user.user.phone = res.data.phone
        this.$store.dispatch('snackbar/showSnackbar', { snackbarText: '프로필을 업데이트 했습니다.', snackbarColor: 'success' })
      }).catch((reason) => {
        this.$store.dispatch('snackbar/showSnackbar', { snackbarText: reason, snackbarColor: 'success' })
      })
    }
  }
}
</script>
