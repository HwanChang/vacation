export const state = () => ({
  token: '',
  user: {
    userId: 0,
    email: '',
    name: '',
    roles: [],
    phone: ''
  }
})

export const mutations = {
  setUser: (state, { data }) => {
    state.token = `Bearer ${data.token}`
    state.user = data.user
  },
  logout: (state) => {
    state.token = ''
    state.user = {}
  }
}

export const actions = {
  async signup ({ commit }, { email, password, name, phone }) {
    return await this.$axios.post('v1/user/join', { email, password, name, phone })
      .then(({ data }) => {
        this.$axios.defaults.headers.common['x-access-token'] = `Bearer ${data.token}`
        commit('setUser', { data })
      })
  },
  async login ({ commit }, { principal, credentials }) {
    return await this.$axios.post('auth', { principal, credentials })
      .then(({ data }) => {
        this.$axios.defaults.headers.common['x-access-token'] = `Bearer ${data.token}`
        commit('setUser', { data })
      })
  },
  logout ({ commit }) {
    this.$axios.defaults.headers.common['x-access-token'] = null
    commit('logout')
  }
}
