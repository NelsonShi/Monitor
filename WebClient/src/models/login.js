import * as loginService from '../services/login'
import { routerRedux } from 'dva/router';

export default {

    namespace: 'login',
 
    state: {
        loginStatus:{logined:false,loginSuccess:false}
    },

    reducers: {
      setIslogin(state,{payload:{loginStatus}}){
        return{...state,loginStatus}
      }
    },

    effects: {
      *login({ payload: value }, { call, put,select}) {
        const result = yield call(loginService.Login,value.user)     
        if(result.data!=null){ 
            yield put({
              type:'setIslogin',
              payload:{
                loginStatus: {logined:true,loginSuccess:true}              
              }
            })
            sessionStorage.setItem('hasLogin', true);
            sessionStorage.setItem('userId',result.data.bpUserId)
            sessionStorage.setItem('username', result.data.loginName);
            yield put((routerRedux.push('/Resource')));
       }else{
            yield put({
              type:'setIslogin',
              payload:{
                loginStatus: {logined:true,loginSuccess:false}              
              }
            })
       }  
      },

      *logout({payload:value},{call,put,select}){
        yield put({
          type:'setIslogin',
          payload:{
            loginStatus: {logined:false,loginSuccess:false}              
          }
        })
        sessionStorage.clear();
        yield put((routerRedux.push('/login')));
      }
    },

    
    subscriptions: {
     
    }

  };
  