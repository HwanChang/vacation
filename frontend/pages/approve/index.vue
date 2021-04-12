<template>
  <v-container fluid grid-list-md fill-height>
    <v-row justify="center">
      <v-layout row wrap>
        <v-flex md10 offset-md1>
          <v-card width="1200" class="mx-auto">
            <base-material-card
              color="indigo darken-2"
              class="px-5 py-3"
            >
              <template #heading>
                <div class="display-2 font-weight-light">
                  Approve
                </div>
                <div class="subtitle-1 font-weight-light">
                  결재할 신청서를 확인하세요.
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
                        결재
                      </th>
                      <th class="primary--text">
                        결재 현황
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr
                      v-for="myApproveRequest in $store.state.approve.myApproveRequests"
                      :key="myApproveRequest.applicationId"
                    >
                      <td>{{ myApproveRequest.applicationId }}</td>
                      <td>
                        <v-chip
                          v-for="date in myApproveRequest.dates"
                          :key="date"
                          class="ma-2"
                          small
                          color="brown lighten-1"
                          text-color="white"
                        >
                          <span>{{ date }}</span>
                        </v-chip>
                      </td>
                      <td><span style="font-size: 15px">{{ myApproveRequest.reason }}</span></td>
                      <td>
                        <v-chip
                          v-if="myApproveRequest.level !== myApproveRequest.approveCount"
                          color="purple lighten-3"
                          class="ma-2"
                          small
                          text-color="white"
                        >
                          <span>{{ myApproveRequest.level }}단계</span>
                        </v-chip>
                        <v-chip
                          v-if="myApproveRequest.level === myApproveRequest.approveCount"
                          color="purple darken-2"
                          class="ma-2"
                          small
                          text-color="white"
                        >
                          <span>{{ myApproveRequest.level }}단계</span>
                        </v-chip>
                        /
                        <v-chip
                          color="purple darken-2"
                          class="ma-2"
                          small
                          text-color="white"
                        >
                          <span>{{ myApproveRequest.approveCount }}단계</span>
                        </v-chip>
                      </td>
                      <td>
                        <chip
                          v-if="myApproveRequest.state === '진행'"
                          color="warning"
                          :state="myApproveRequest.state"
                        />
                        <chip
                          v-else-if="myApproveRequest.state === '완료'"
                          color="success"
                          :state="myApproveRequest.state"
                        />
                        <chip
                          v-else-if="myApproveRequest.state === '반려'"
                          color="error"
                          :state="myApproveRequest.state"
                        />
                        <chip
                          v-else-if="myApproveRequest.state === '취소'"
                          color="secondary"
                          :state="myApproveRequest.state"
                        />
                      </td>
                      <td>
                        <v-btn
                          color="success"
                          elevation="10"
                          @click="approve(myApproveRequest.applicationId)"
                        >
                          결재
                        </v-btn>
                      </td>
                      <td>
                        <approvers
                          :approve-list="{approves: myApproveRequest.approves}"
                          :level="myApproveRequest.level"
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
    title: '결재'
  },
  mounted () {
    this.mount()
  },
  methods: {
    async approve (applicationId) {
      await this.$axios.patch(`v1/application/${applicationId}/approve`).then((res) => {
        this.$store.dispatch('snackbar/showSnackbar', { snackbarText: '결재가 완료됐습니다.', snackbarColor: 'success' })
        this.mount()
      }).catch((reason) => {
        this.$store.dispatch('snackbar/showSnackbar', { snackbarText: reason, snackbarColor: 'error' })
      })
    },
    async mount () {
      await this.$axios.get('v1/approve/application').then((res) => {
        this.$store.dispatch('approve/setMyApproveRequests', { myApproveRequests: res.data })
      }).catch((reason) => {
        this.$store.dispatch('snackbar/showSnackbar', { snackbarText: reason, snackbarColor: 'error' })
      })
    }
  }
}
</script>
