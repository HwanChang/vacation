<template>
  <v-dialog
    v-model="dialog"
    width="800"
    persistent
    scrollable
  >
    <template #activator="{ on, attrs }">
      <v-btn
        color="indigo darken-2"
        elevation="10"
        dark
        v-bind="attrs"
        v-on="on"
      >
        결재 현황
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

      <v-list two-line disabled>
        <v-list-item-group
          v-model="currentApprover"
          color="error"
        >
          <template v-for="(approve, index) in $props.approveList.approves">
            <v-list-item :key="approve.approveId">
              <v-list-item-content>
                <v-list-item-title v-text="approve.user.name" />
                <v-list-item-subtitle
                  class="text--primary"
                  v-text="approve.user.email"
                />
                <v-list-item-action-text v-text="approve.user.phone" />
              </v-list-item-content>
              <v-list-item-action>
                <chip
                  v-if="approve.approved"
                  color="success"
                  :state="`${approve.level}단계 결재 완료`"
                />
                <chip
                  v-else-if="currentApprover === index"
                  color="error"
                  :state="`${approve.level}단계 진행 중`"
                />
              </v-list-item-action>
            </v-list-item>

            <v-divider
              v-if="index < approve.length - 1"
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
          @click="dialog = false"
        >
          확인
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import Chip from './Chip'
export default {
  components: { Chip },
  props: {
    approveList: {
      type: Object,
      default () {
        return {
          approves: []
        }
      }
    },
    level: {
      type: Number,
      default: 0
    },
    state: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      dialog: false
    }
  },
  computed: {
    currentApprover () {
      return (this.state === '완료') ? null : this.$props.level - 1
    }
  }
}
</script>
