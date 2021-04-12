export const state = () => ({
  users: [],
  approvers: [
    {
      level: 0,
      name: '',
      email: '',
      phone: ''
    }
  ],
  request: {
    approveRequests: [
      {
        approverId: 0,
        level: 0
      }
    ],
    vacationRequest: {
      dates: [
        ''
      ],
      reason: ''
    }
  },
  myRequests: [
    {
      applicationId: 0,
      dates: [],
      reason: '',
      level: 0,
      approveCount: 0,
      state: '',
      approves: [
        {
          approveId: 0,
          approved: true,
          level: 0,
          user: {
            email: '',
            lastLoginAt: '',
            loginCount: 0,
            name: '',
            phone: '',
            roles: [],
            userId: 0
          }
        }
      ]
    }
  ]
})

export const mutations = {
  setUsers: (state, { users }) => {
    state.users = users
  },
  setApprovers: (state, { approvers }) => {
    state.approvers = approvers
  },
  setApproveRequests: (state, { approveRequests }) => {
    state.request.approveRequests = approveRequests
  },
  setDates: (state, { dates }) => {
    state.request.vacationRequest.dates = dates
  },
  setReason: (state, { reason }) => {
    state.request.vacationRequest.reason = reason
  },
  setMyRequests: (state, { myRequests }) => {
    state.myRequests = myRequests
  },
  requestComplete: (state) => {
    state.request = {}
    state.myRequests = []
    state.users = []
    state.approvers = []
  }
}

export const actions = {
  setUsers ({ commit }, { users }) {
    commit('setUsers', { users })
  },
  setApprovers ({ commit }, { approvers }) {
    commit('setApprovers', { approvers })
  },
  setApproveRequests ({ commit }, { approveRequests }) {
    commit('setApproveRequests', { approveRequests })
  },
  setDates ({ commit }, { dates }) {
    commit('setDates', { dates })
  },
  setReason ({ commit }, { reason }) {
    commit('setReason', { reason })
  },
  setMyRequests ({ commit }, { myRequests }) {
    commit('setMyRequests', { myRequests })
  },
  requestComplete ({ commit }) {
    commit('requestComplete')
  }
}
