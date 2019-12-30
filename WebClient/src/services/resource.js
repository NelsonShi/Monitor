import request from '../utils/request';

export function findAllResources (value) { 
  return request('/bpServer/resource/resourceList?requestTimeZone='+value.requestTimeZone); //get方法请求
    /*
   return request(`接口地址`,{
    method: 'post',
    headers: {
      'Content-Type': 'application/json; charset=utf-8'
    },
    body: JSON.stringify({
      参数名：参数
    })
  })
  */
  
}

export function findAllResourcesSecondly (value) {
  return request('/bpServer/resource/resourceListForWeb?requestTimeZone='+value.requestTimeZone); //get方法请求
}
export function findResourceScheduleList (value) {
  return request('/bpServer/resource/scheduleVos',{
    method: 'post',
    headers: {
      'Content-Type': 'application/json; charset=utf-8'
    },
    body: JSON.stringify(
      value
    )
  }); //get方法请求
}

export function findAllBots () {
  return request('/bpServer/resource/bots'); //get方法请求
}

export function operation (params) {
  return request('/bpServer/operation/command',{
   method: 'post',
   headers: {
     'Content-Type': 'application/json; charset=utf-8'
   },
   body: JSON.stringify(
    params
   )
 })
 
}
