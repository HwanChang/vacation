export const state = () => ({
  snackbar: false,
  snackbarText: '',
  snackbarColor: 'info'
})

export const mutations = {
  setSnackbar: (state, { snackbarText, snackbarColor }) => {
    state.snackbar = true
    state.snackbarText = snackbarText
    state.snackbarColor = snackbarColor
  }
}

export const actions = {
  showSnackbar ({ commit }, { snackbarText, snackbarColor }) {
    commit('setSnackbar', { snackbarText, snackbarColor })
  }
}
