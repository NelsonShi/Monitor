import * as processService from "../services/process";

export default {
  namespace: "process",

  state: {
    list: [],
    page: 0,
    processCount: 0,
    errorCount: 0,
    undefinedCount: 0,
    currentP:{}
  },

  reducers: {
    save(state, { payload: { list } }) {
      return { ...state, list };
    },
    saveCards(
      state,
      { payload: { processCount, errorCount, undefinedCount } }
    ) {
      return { ...state, processCount, errorCount, undefinedCount };
    },
    saveErrors(state,{payload:{currentP}}){
      return { ...state,currentP};
    }
  },
  effects: {
    *fetch({ payload: value }, { call, put }) {
      // 模拟网络请求
      const result = yield call(processService.findErrorProcess, value);
      let processCount = 0;
      let undefinedCount = 0;
      let errorCount = 0;
      if (result.data.length > 0) {
        processCount = result.data.length;
        result.data.forEach(item => {
          errorCount += item.errorCount;
          undefinedCount += item.undefinedCount;
        });
      }
      yield put({
        type: "saveCards", //reducers中的方法名
        payload: {
          processCount: processCount,
          errorCount: errorCount,
          undefinedCount: undefinedCount //网络返回的要保留的数据
        }
      });
      yield put({
        type: "save", //reducers中的方法名
        payload: {
          list: result.data //网络返回的要保留的数据
        }
      });
    },

    *loadError({ payload: value }, {select,put }) {
      // 模拟网络请求
      let id=value.id;
      const list = yield select(state=>state.process.list)
      if(list!=null&&list.length>0){
        const p=list.filter(item => item.processId === id)
        if(p!=null&&p.length>0){
        yield put({
          type: "saveErrors", //reducers中的方法名
          payload: {
            currentP:p[0]
          }
        });}
      }
    }
  },

  subscriptions: {
    setup({ dispatch, history }) {
      // eslint-disable-line
    }
  }
};
