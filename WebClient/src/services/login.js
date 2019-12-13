import request from '../utils/request';

export function Login (user) { 
   return request('/bpServer/user/login',{
    method: 'post',
    headers: {
      'Content-Type': 'application/json; charset=utf-8'
    },
    body: JSON.stringify(
      user
    )
  })
  
}
