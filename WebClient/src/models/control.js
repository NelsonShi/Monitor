import * as controService from "../services/control";

export default {
  namespace: "control",

  state: {
    groups: [],
    availableResources: [],
    pengdingSessions:[],
    sessions:[],
    bpaStatus:[]
  },

  reducers: {
    save(state, { payload: { groups } }) {
      return { ...state, groups };
    },
    saveStatus(state, { payload: { bpaStatus } }) {
      return { ...state, bpaStatus };
    },
    saveAvailable(state, { payload: { availableResources } }) {
      return { ...state, availableResources };
    },
    savePendingSessions(state, { payload: { pengdingSessions } }) {
      return { ...state, pengdingSessions };
    },
    saveSessions(state, { payload: { sessions } }) {
      return { ...state, sessions };
    }
  },
  effects: {
    *fetch({ payload: value }, { call, put }) {
      // 模拟网络请求
      const result = yield call(controService.findGroupProcess, value);
      yield put({
        type: "save", //reducers中的方法名
        payload: {
          groups: result.data //网络返回的要保留的数据
        }
      });
    },
    *loadStatus({ payload: value }, { call, put }) {
      // 模拟网络请求
      const result = yield call(controService.findStatus, value);
      yield put({
        type: "saveStatus", //reducers中的方法名
        payload: {
          bpaStatus: result.data //网络返回的要保留的数据
        }
      });
    },
    *loadAvailableResources({ payload: value }, { call, put }) {
      const result = yield call(controService.findResourceTimeAvailable, value);
      yield put({
        type: "saveAvailable", //reducers中的方法名
        payload: {
          availableResources: result.data //网络返回的要保留的数据
        }
      });
    },

    *clearAvailableRs({ payload: value }, { call, put }) {
      let empty = [];
      yield put({
        type: "saveAvailable", //reducers中的方法名
        payload: {
          availableResources: empty //网络返回的要保留的数据
        }
      });
    },
    *pending({payload:value},{call}){
      const result=yield call(controService.pendingProcess,value);
      console.log('pending Process result',result)
    },
    *findPendingSessions({payload:value},{call,put}){
      const result=yield call(controService.findPendingSessions,value);
      yield put({
        type: "savePendingSessions", //reducers中的方法名
        payload: {
          pengdingSessions: result.data //网络返回的要保留的数据
        }
      });
    },
    *loadSessions({payload:value},{call,put}){
      console.log('loadSessions',value)
      const result=yield call(controService.findSessions,value);
      yield put({
        type: "saveSessions", //reducers中的方法名
        payload: {
          sessions: result.data //网络返回的要保留的数据
        }
      });
    },
    
    *start({payload:value},{call}){
      console.log('start session',value)
      const result=yield call(controService.startProcess,value);
      console.log(result.data);
    }

  },

  subscriptions: {
    setup({ dispatch, history }) {
      // eslint-disable-line
    }
  }
};
