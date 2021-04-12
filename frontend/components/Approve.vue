<template>
  <v-card
    class="mb-12"
    elevation="0"
  >
    <v-dialog
      ref="dialog"
      v-model="modal"
      :return-value.sync="approvers"
      persistent
      scrollable
      width="500px"
    >
      <template #activator="{ on, attrs }">
        <v-btn
          color="accent"
          dark
          small
          prepend-icon="mdi-account-check-outline"
          v-bind="attrs"
          v-on="on"
        >
          결재자 조회
        </v-btn>
      </template>
      <v-card
        class="mx-auto"
        max-width="100%"
      >
        <v-toolbar
          color="accent"
          dark
        >
          <v-toolbar-title>결재자</v-toolbar-title>
        </v-toolbar>

        <v-list two-line>
          <v-list-item-group
            v-model="approvers"
            active-class="accent--text"
            multiple
          >
            <template v-for="(user, index) in users">
              <v-list-item :key="user.email">
                <template #default="{ active }">
                  <v-list-item-content>
                    <v-list-item-title v-text="user.name" />
                    <v-list-item-subtitle
                      class="text--primary"
                      v-text="user.email"
                    />
                    <v-list-item-action-text v-text="user.phone" />
                  </v-list-item-content>
                  <v-list-item-action>
                    <v-chip
                      v-if="active"
                      class="ma-2"
                      color="warning"
                      text-color="white"
                    >
                      <span>{{ approvers.indexOf(index) + 1 }}번 결재자</span>
                    </v-chip>
                  </v-list-item-action>
                </template>
              </v-list-item>

              <v-divider
                v-if="index < users.length - 1"
                :key="index"
              />
            </template>
          </v-list-item-group>
        </v-list>
        <v-card-actions>
          <v-spacer />
          <v-btn
            color="blue darken-1"
            text
            @click="modal = false"
          >
            Close
          </v-btn>
          <v-btn
            color="blue darken-1"
            text
            @click="setApprovers"
          >
            Save
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-simple-table dense style="padding-top: 15px">
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
            v-for="approver in approvers"
            :key="users[approver].email"
          >
            <td>{{ approvers.indexOf(approver) + 1 }}</td>
            <td>{{ users[approver].name }}</td>
            <td>{{ users[approver].email }}</td>
            <td>{{ users[approver].phone }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </v-card>
</template>

<script>
export default {
  data () {
    return {
      level: 1,
      steps: 2,
      approvers: [],
      modal: false,
      users: []
    }
  },
  watch: {
    modal (val) {
      if (val) {
        this.findUsers()
      }
    }
  },
  methods: {
    findUsers () {
      this.$axios.get('v1/users').then((res) => {
        this.users = res.data
        this.$store.dispatch('request/setUsers', { users: this.users })
      })
    },
    setApprovers () {
      const approvers = []
      const approveRequests = []
      this.approvers.forEach((approver) => {
        approvers.push({
          level: this.approvers.indexOf(approver) + 1,
          name: this.users[approver].name,
          email: this.users[approver].email,
          phone: this.users[approver].phone
        })
        approveRequests.push({
          approverId: this.users[approver].userId,
          level: this.approvers.indexOf(approver) + 1
        })
      })
      this.$store.dispatch('request/setApprovers', { approvers })
      this.$store.dispatch('request/setApproveRequests', { approveRequests })
      this.$refs.dialog.save(this.approvers)
    }
  }
}
</script>
