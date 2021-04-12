export const state = () => ({
  myApproveRequests: [
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
  setMyApproveRequests: (state, { myApproveRequests }) => {
    state.myApproveRequests = myApproveRequests
  }
}

export const actions = {
  setMyApproveRequests ({ commit }, { myApproveRequests }) {
    commit('setMyApproveRequests', { myApproveRequests })
  }
}
