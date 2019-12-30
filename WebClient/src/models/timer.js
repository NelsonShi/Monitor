import * as resourceService from "../services/resource";

export default {
  namespace: "timer",

  state: {
    bots: [],
    vos: []
  },

  reducers: {
    getbots(state, { payload: { bots } }) {
      return { ...state, bots };
    },
    save(state, { payload: { vos } }) {
      return { ...state, vos };
    }
  },

  effects: {
    *getBots({ payload: value }, { call, put ,select}) {
      const result = yield call(resourceService.findAllBots, value);
      yield put({
        type: "getbots", //reducers中的方法名
        payload: {
          bots: result.data //网络返回的要保留的数据
        }
      });      
      yield put({
        type: "resource/freshResourceConnectedStatus", 
        payload: {
          bots: result.data 
        }
      });
    },
    *fleshResource({ payload: value }, { call, put }) {
      // 模拟网络请求
      const result = yield call(
        resourceService.findAllResourcesSecondly,
        value
      );
      yield put({
        type: "resource/fleshList", //reducers中的方法名
        payload: {
          value: result.data //网络返回的要保留的数据
        }
      });
    }
  },

  subscriptions: {
    setup({ dispatch, history }) {
      // eslint-disable-line
    }
  }
};
