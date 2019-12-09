import request from '../utils/request';



export function findErrorProcess () {
  return request('/bpServer/resource/errorProcessVos'); //get方法请求
}
