export const state = () => ({
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
    state.user = data
  },
  setUserToken: (state, { data }) => {
    localStorage.setItem('token', `Bearer ${data.token}`)
    state.user = data.user
  },
  logout: (state) => {
    window.localStorage.clear()
    state.user = {}
  }
}

export const actions = {
  async signup ({ commit }, { email, password, name, phone }) {
    return await this.$axios.post('v1/user/join', { email, password, name, phone })
      .then(({ data }) => {
        commit('setUserToken', { data })
      })
  },
  async login ({ commit }, { principal, credentials }) {
    return await this.$axios.post('auth', { principal, credentials })
      .then(({ data }) => {
        commit('setUserToken', { data })
      })
  },
  setUser ({ commit }) {
    return this.$axios.get('v1/user')
      .then(({ data }) => {
        commit('setUser', { data })
      })
  },
  logout ({ commit }) {
    this.$axios.defaults.headers.common['x-access-token'] = null
    commit('logout')
  }
}
