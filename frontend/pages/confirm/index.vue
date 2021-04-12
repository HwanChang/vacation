<template>
  <v-container fluid grid-list-md fill-height>
    <v-row justify="center">
      <v-layout row wrap>
        <v-flex md10 offset-md1>
          <v-card width="1200" class="mx-auto">
            <base-material-card
              color="blue-grey darken-2"
              class="px-5 py-3"
            >
              <template #heading>
                <div class="display-2 font-weight-light">
                  Confirm
                </div>
                <div class="subtitle-1 font-weight-light">
                  처리할 신청서를 확인하세요.
                </div>
              </template>
              <v-card-text>
                <v-simple-table>
                  <thead>
                    <tr>
                      <th class="primary--text">
                        신청서 번호
                      </th>
                      <th class="primary--text">
                        휴가 날짜
                      </th>
                      <th class="primary--text">
                        사유
                      </th>
                      <th class="primary--text">
                        진행 단계
                      </th>
                      <th class="primary--text">
                        결재 상태
                      </th>
                      <th class="primary--text">
                        처리 상태
                      </th>
                      <th class="primary--text">
                        처리
                      </th>
                      <th class="primary--text">
                        결재 현황
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr
                      v-for="confirmRequest in $store.state.confirm.confirmRequests"
                      :key="confirmRequest.application.applicationId"
                    >
                      <td>{{ confirmRequest.application.applicationId }}</td>
                      <td>
                        <v-chip
                          v-for="date in confirmRequest.application.dates"
                          :key="date"
                          class="ma-2"
                          small
                          color="brown lighten-1"
                          text-color="white"
                        >
                          <span>{{ date }}</span>
                        </v-chip>
                      </td>
                      <td><span style="font-size: 15px">{{ confirmRequest.application.reason }}</span></td>
                      <td>
                        <v-chip
                          v-if="confirmRequest.application.level !== confirmRequest.application.approveCount"
                          color="purple lighten-3"
                          class="ma-2"
                          small
                          text-color="white"
                        >
                          <span>{{ confirmRequest.application.level }}단계</span>
                        </v-chip>
                        <v-chip
                          v-if="confirmRequest.application.level === confirmRequest.application.approveCount"
                          color="purple darken-2"
                          class="ma-2"
                          small
                          text-color="white"
                        >
                          <span>{{ confirmRequest.application.level }}단계</span>
                        </v-chip>
                        /
                        <v-chip
                          color="purple darken-2"
                          class="ma-2"
                          small
                          text-color="white"
                        >
                          <span>{{ confirmRequest.application.approveCount }}단계</span>
                        </v-chip>
                      </td>
                      <td>
                        <chip
                          v-if="confirmRequest.application.state === '진행'"
                          color="warning"
                          :state="confirmRequest.application.state"
                        />
                        <chip
                          v-else-if="confirmRequest.application.state === '완료'"
                          color="success"
                          :state="confirmRequest.application.state"
                        />
                        <chip
                          v-else-if="confirmRequest.application.state === '반려'"
                          color="error"
                          :state="confirmRequest.application.state"
                        />
                        <chip
                          v-else-if="confirmRequest.application.state === '취소'"
                          color="secondary"
                          :state="confirmRequest.application.state"
                        />
                      </td>
                      <td>
                        <chip
                          v-if="confirmRequest.confirmed"
                          color="success"
                          :state="'처리 완료'"
                        />
                        <chip
                          v-else
                          color="error"
                          :state="'처리 예정'"
                        />
                      </td>
                      <td>
                        <v-btn
                          color="success"
                          elevation="10"
                          :disabled="confirmRequest.application.state !== '완료' || confirmRequest.confirmed"
                          @click="approve(confirmRequest.application.applicationId)"
                        >
                          처리
                        </v-btn>
                      </td>
                      <td>
                        <approvers
                          :approve-list="{approves: confirmRequest.approves}"
                          :level="confirmRequest.application.level"
                          :state="confirmRequest.application.state"
                        />
                      </td>
                    </tr>
                  </tbody>
                </v-simple-table>
              </v-card-text>
            </base-material-card>
          </v-card>
        </v-flex>
      </v-layout>
    </v-row>
  </v-container>
</template>

<script>
export default {
  head: {
    title: '처리'
  },
  mounted () {
    this.mount()
  },
  methods: {
    async approve (applicationId) {
      await this.$axios.patch(`v1/application/${applicationId}/confirm`).then((res) => {
        this.$store.dispatch('snackbar/showSnackbar', { snackbarText: '처리가 완료됐습니다.', snackbarColor: 'success' })
        this.mount()
      }).catch((reason) => {
        this.$store.dispatch('snackbar/showSnackbar', { snackbarText: reason, snackbarColor: 'error' })
      })
    },
    async mount () {
      await this.$axios.get('v1/confirm').then((res) => {
        this.$store.dispatch('confirm/setConfirmRequests', { confirmRequests: res.data })
      }).catch((reason) => {
        this.$store.dispatch('snackbar/showSnackbar', { snackbarText: reason, snackbarColor: 'error' })
      })
    }
  }
}
</script>
