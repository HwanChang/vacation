export const state = () => ({
  confirmRequests: [
    {
      application: {
        applicationId: 0,
        approveCount: 0,
        level: 0,
        state: '',
        dates: [],
        reason: ''
      },
      confirmed: false,
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
  setConfirmRequests: (state, { confirmRequests }) => {
    state.confirmRequests = confirmRequests
  }
}

export const actions = {
  setConfirmRequests ({ commit }, { confirmRequests }) {
    commit('setConfirmRequests', { confirmRequests })
  }
}
