<template>
  <v-container fluid grid-list-md fill-height>
    <v-row justify="center">
      <v-layout row wrap>
        <v-flex md10 offset-md1>
          <v-card width="800" class="mx-auto">
            <v-stepper
              v-model="level"
              vertical
            >
              <v-stepper-step
                :complete="level > 1"
                editable
                step="1"
              >
                휴가 날짜
              </v-stepper-step>
              <v-stepper-content step="1">
                <v-card
                  class="mb-12"
                  elevation="0"
                >
                  <v-dialog
                    ref="dialog"
                    v-model="modal"
                    :return-value.sync="dates"
                    persistent
                    width="290px"
                  >
                    <template #activator="{ on, attrs }">
                      <v-combobox
                        v-model="dates"
                        multiple
                        chips
                        small-chips
                        label="날짜 선택"
                        prepend-icon="mdi-calendar"
                        readonly
                        v-bind="attrs"
                        v-on="on"
                      />
                    </template>
                    <v-date-picker
                      v-model="dates"
                      scrollable
                      multiple
                    >
                      <v-spacer />
                      <v-btn
                        text
                        color="warning"
                        @click="modal = false"
                      >
                        Cancel
                      </v-btn>
                      <v-btn
                        text
                        color="warning"
                        @click="$refs.dialog.save(dates)"
                      >
                        OK
                      </v-btn>
                    </v-date-picker>
                  </v-dialog>
                </v-card>
                <v-btn
                  color="warning"
                  small
                  @click="level = 2"
                >
                  {{ next }}
                </v-btn>
              </v-stepper-content>
              <v-stepper-step
                :complete="level > 2"
                editable
                step="2"
              >
                휴가 사유
              </v-stepper-step>
              <v-stepper-content step="2">
                <v-card
                  class="mb-12"
                  elevation="0"
                >
                  <v-text-field
                    v-model="reason"
                    label="휴가 사유를 입력하세요."
                    :rules="rule.reason"
                    prepend-icon="mdi-clipboard-edit-outline"
                  />
                </v-card>
                <v-btn
                  color="warning"
                  small
                  @click="level = 1"
                >
                  {{ prev }}
                </v-btn>
                <v-btn
                  color="warning"
                  small
                  @click="level = 3"
                >
                  {{ next }}
                </v-btn>
              </v-stepper-content>
              <v-stepper-step
                :complete="level > 3"
                editable
                step="3"
              >
                결재선 선택
              </v-stepper-step>
              <v-stepper-content step="3">
                <v-card
                  class="mb-12"
                  elevation="0"
                >
                  <approve />
                </v-card>
                <v-btn
                  color="warning"
                  small
                  @click="level = 2"
                >
                  {{ prev }}
                </v-btn>
                <v-btn
                  color="warning"
                  small
                  @click="level = 4"
                >
                  완료
                </v-btn>
              </v-stepper-content>
              <v-stepper-step
                :complete="level > 4"
                editable
                step="4"
              >
                완료
              </v-stepper-step>
              <v-stepper-content step="4">
                <v-combobox
                  v-model="dates"
                  multiple
                  chips
                  small-chips
                  label="휴가 날짜"
                  prepend-icon="mdi-calendar"
                  readonly
                />
                <v-text-field
                  v-model="reason"
                  readonly
                  prepend-icon="mdi-clipboard-edit-outline"
                />
                <v-simple-table
                  dense
                  style="padding-top: 15px; padding-bottom: 15px"
                >
                  <template #default>
                    <thead>
                      <tr>
                        <th class="text-left">
                          결재 순서
                        </th>
                        <th class="text-left">
                          이름
                        </th>
                        <th class="text-left">
                          Email
                        </th>
                        <th class="text-left">
                          연락처
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr
                        v-for="approver in $store.state.request.approvers"
                        :key="$store.state.request.users[approver]"
                      >
                        <td>{{ $store.state.request.approvers.indexOf(approver) + 1 }}</td>
                        <td>{{ approver.name }}</td>
                        <td>{{ approver.email }}</td>
                        <td>{{ approver.phone }}</td>
                      </tr>
                    </tbody>
                  </template>
                </v-simple-table>
                <v-btn
                  width="100%"
                  color="warning"
                  @click="postRequest"
                >
                  신청
                </v-btn>
              </v-stepper-content>
            </v-stepper>
          </v-card>
        </v-flex>
      </v-layout>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data () {
    return {
      level: 1,
      next: '다음',
      prev: '이전',
      modal: false,
      dates: [],
      reason: '',
      rule: {
        reason: [
          v => !!v || '사유를 입력해주세요.'
        ]
      }
    }
  },
  watch: {
    dates (val) {
      this.$store.dispatch('request/setDates', { dates: val })
    },
    reason (val) {
      this.$store.dispatch('request/setReason', { reason: val })
    }
  },
  methods: {
    async postRequest () {
      await this.$axios.post('v1/application', this.$store.state.request.request).then((res) => {
        this.$router.push('/request/list')
        this.$store.dispatch('snackbar/showSnackbar', { snackbarText: '휴가 신청이 완료됐습니다.', snackbarColor: 'success' })
        this.$store.dispatch('request/requestComplete')
      }).catch((reason) => {
        this.$store.dispatch('request/requestComplete')
        this.$store.dispatch('snackbar/showSnackbar', { snackbarText: reason, snackbarColor: 'error' })
      })
    }
  }
}
</script>
