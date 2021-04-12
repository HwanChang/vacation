<template>
  <v-container fluid grid-list-md fill-height>
    <v-row justify="center">
      <v-layout row wrap>
        <v-flex md10 offset-md1>
          <v-card width="1200" class="mx-auto">
            <base-material-card
              color="warning"
              class="px-5 py-3"
            >
              <template #heading>
                <div class="display-2 font-weight-light">
                  Request List
                </div>
                <div class="subtitle-1 font-weight-light">
                  휴가 신청 내역을 확인하세요.
                </div>
              </template>
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
                      결재 현황
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="myRequest in $store.state.request.myRequests"
                    :key="myRequest.applicationId"
                  >
                    <td>{{ myRequest.applicationId }}</td>
                    <td>
                      <v-chip
                        v-for="date in myRequest.dates"
                        :key="date"
                        class="ma-2"
                        small
                        color="brown lighten-1"
                        text-color="white"
                      >
                        <span>{{ date }}</span>
                      </v-chip>
                    </td>
                    <td><span style="font-size: 15px">{{ myRequest.reason }}</span></td>
                    <td>
                      <v-chip
                        v-if="myRequest.level !== myRequest.approveCount"
                        color="purple lighten-3"
                        class="ma-2"
                        small
                        text-color="white"
                      >
                        <span>{{ myRequest.level }}단계</span>
                      </v-chip>
                      <v-chip
                        v-if="myRequest.level === myRequest.approveCount"
                        color="purple darken-2"
                        class="ma-2"
                        small
                        text-color="white"
                      >
                        <span>{{ myRequest.level }}단계</span>
                      </v-chip>
                      /
                      <v-chip
                        color="purple darken-2"
                        class="ma-2"
                        small
                        text-color="white"
                      >
                        <span>{{ myRequest.approveCount }}단계</span>
                      </v-chip>
                    </td>
                    <td>
                      <chip
                        v-if="myRequest.state === '진행'"
                        color="warning"
                        :state="myRequest.state"
                      />
                      <chip
                        v-else-if="myRequest.state === '완료'"
                        color="success"
                        :state="myRequest.state"
                      />
                      <chip
                        v-else-if="myRequest.state === '반려'"
                        color="error"
                        :state="myRequest.state"
                      />
                      <chip
                        v-else-if="myRequest.state === '취소'"
                        color="secondary"
                        :state="myRequest.state"
                      />
                    </td>
                    <td>
                      <approvers
                        :approve-list="{approves: myRequest.approves}"
                        :level="myRequest.level"
                      />
                    </td>
                  </tr>
                </tbody>
              </v-simple-table>
            </base-material-card>
          </v-card>
        </v-flex>
      </v-layout>
    </v-row>
  </v-container>
</template>

<script>
import Approvers from '../../components/Approvers'
export default {
  components: { Approvers },
  mounted () {
    this.$axios.get('v1/application').then((res) => {
      this.$store.dispatch('request/setMyRequests', { myRequests: res.data })
    }).catch((reason) => {
      this.$store.dispatch('snackbar/showSnackbar', { snackbarText: reason, snackbarColor: 'error' })
    })
  }
}
</script>
