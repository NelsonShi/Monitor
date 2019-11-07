import * as resourceService from '../services/resource'

export default {

    namespace: 'resource',
 
    state: {
        list: [],
        total: 0,
        page: 0,
    },

    reducers: {
      save(state, { payload: { list } }) {
        return { ...state, list }
      },     
      getbots(state, { payload: { bots } }){
        return{...state,bots}
      }
    },
    effects: {
      *fetch({ payload: value }, { call, put }) {
        // 模拟网络请求
        const result = yield call(resourceService.findAllResources, value)
        yield put({
          type: 'save',  //reducers中的方法名
          payload:{
            list: result.data  //网络返回的要保留的数据
          }
        })
      },
      *fleshList({ payload: value }, { call, put }){
        yield put({
          type: 'save',  //reducers中的方法名
          payload:{
            list: value.value  //网络返回的要保留的数据
          }
        })
      }
    },
    subscriptions: {
      setup({ dispatch, history }) {  // eslint-disable-line
      },
    }

  };
  