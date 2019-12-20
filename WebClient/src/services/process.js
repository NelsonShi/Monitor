import request from '../utils/request';



export function findErrorProcess (value) {
  return request('/bpServer/process/errorProcessVos?requestTimeZone='+value.requestTimeZone); //get方法请求
}
