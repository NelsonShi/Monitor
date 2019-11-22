import request from '../utils/request';

export function Login (params) { 
   return request('/localServer/login',{
    method: 'post',
    headers: {
      'Content-Type': 'application/json; charset=utf-8'
    },
    body: JSON.stringify({
      user:params
    })
  })
  
}
