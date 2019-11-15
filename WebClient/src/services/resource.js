import request from '../utils/request';

// export function findAllDonates() {
//   return request('http://127.0.0.1:7001/donates');
// }



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
