import * as controService from "../services/control";
import { message} from 'antd';

export default {
  namespace: "control",

  state: {
    groups: [],
    availableResources: [],
    pengdingSessions:[],
    sessions:[],
    bpaStatus:[],
    modalVisiable:false,
    searchValue:{}
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
    },
    saveModalVisiable(state,{payload:{modalVisiable}}){
      return { ...state, modalVisiable };
    },
    saveSearchValue(state,{payload:{searchValue}}){
      return { ...state, searchValue };
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
      console.log('loadAvailableResources')
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
    *pending({payload:value},{call,put}){
      const result=yield call(controService.pendingProcess,value);
      yield put({
        type: "saveModalVisiable", //reducers中的方法名
        payload: {
          modalVisiable: false //网络返回的要保留的数据
        }
      });
      if(result.data.status===0){
        message.error(result.data.message,10);
      }else{
        message.success(result.data.message,3);
      }    
      yield put({type:'findPendingSessions'})
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
      const result=yield call(controService.findSessions,value);
      yield put({
        type: "saveSessions", //reducers中的方法名
        payload: {
          sessions: result.data //网络返回的要保留的数据
        }
      });
      yield put({
        type: "saveSearchValue", //reducers中的方法名
        payload: {
          searchValue: value //网络返回的要保留的数据
        }
      });
    },

    *reloadSessions({payload:value},{call,put}){
      console.log("reloadSessions",value)
      const result=yield call(controService.findSessions,value.searchValue);
      yield put({
        type: "saveSessions", //reducers中的方法名
        payload: {
          sessions: result.data //网络返回的要保留的数据
        }
      });
    },

    
    *start({payload:value},{call,put,select}){
      console.log('start session',value)
      const result=yield call(controService.startProcess,value);
      if(result.data.status===0){
        message.error(result.data.message,10);
      }else{
        message.success(result.data.message,3);
      }    
      yield put({type:'findPendingSessions'})
      const searchValue = yield select(state=>state.control.searchValue)
      yield put({type:'reloadSessions',payload:{
         searchValue
      }})
    },

    *delete({payload:value},{call,put,select}){
      const result=yield call(controService.deleteSession,value);
      if(result.data.status===0){
        message.error(result.data.message,10);
      }else{
        message.success(result.data.message,3);
      }    
      yield put({type:'findPendingSessions'})
      const searchValue = yield select(state=>state.control.searchValue)
      yield put({type:'reloadSessions',payload:{
         searchValue
      }})
    },

    *stop({payload:value},{call,put,select}){
      console.log('stop session',value)
      const result=yield call(controService.stopSession,value);
      if(result.data.status===0){
        message.error(result.data.message,10);
      }else{
        message.success(result.data.message,3);
      }    
      const searchValue = yield select(state=>state.control.searchValue)
      yield put({type:'reloadSessions',payload:{
         searchValue
      }})
    },


    *changeModalVisiable({ payload: value }, { call, put }){
      yield put({
        type: "saveModalVisiable", //reducers中的方法名
        payload: {
          modalVisiable: value.modalVisiable //网络返回的要保留的数据
        }
      });
    },

    *changeSearchValue({ payload: value }, { call, put }){
      console.log('changeSearchValue',value)
      yield put({
        type: "saveSearchValue", //reducers中的方法名
        payload: {
          searchValue: value //网络返回的要保留的数据
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
