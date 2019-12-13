import request from '../utils/request';

export function findAllResources () {
  return request('/bpServer/process/resourceList'); //get方法请求
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

export function findAllResourcesSecondly () {
  return request('/bpServer/process/resourceListForWeb'); //get方法请求
}
export function findResourceScheduleList () {
  return request('/bpServer/process/scheduleVos'); //get方法请求
}

export function findAllBots () {
  return request('/bpServer/process/bots'); //get方法请求
}

export function operation (params) {
  console.log(params)
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
