import request from '../utils/request';



export function findGroupProcess (value) {
  return request('/bpServer/operation/processWithGroup')
}

export function findStatus (value) {
  return request('/bpServer/resource/bpaStatus')
}

export function findPendingSessions (value) {
  return request('/bpServer/operation/pendingSessions')
}

export function findSessions (value) {
  return request('/bpServer/operation/findSessions',{
    method: 'post',
    headers: {
      'Content-Type': 'application/json; charset=utf-8'
    },
    body: JSON.stringify(
      value
    )
  })
}

export function startProcess (value) {
  return request('/bpServer/operation/startSession',{
    method: 'post',
    headers: {
      'Content-Type': 'application/json; charset=utf-8'
    },
    body: JSON.stringify(
      value
    )
  })
}

export function deleteSession (value) {
  return request('/bpServer/operation/deleteSession',{
    method: 'post',
    headers: {
      'Content-Type': 'application/json; charset=utf-8'
    },
    body: JSON.stringify(
      value
    )
  })
}

export function stopSession (value) {
  return request('/bpServer/operation/stopSession',{
    method: 'post',
    headers: {
      'Content-Type': 'application/json; charset=utf-8'
    },
    body: JSON.stringify(
      value
    )
  })
}

export function findResourceTimeAvailable (value) {
  return request('/bpServer/operation/availableResource',{
    method: 'post',
    headers: {
      'Content-Type': 'application/json; charset=utf-8'
    },
    body: JSON.stringify(
      value
    )
  }); //post方法请求
}

export function pendingProcess (value) {
  return request('/bpServer/operation/pending',{
    method: 'post',
    headers: {
      'Content-Type': 'application/json; charset=utf-8'
    },
    body: JSON.stringify(
      value
    )
  }); //post方法请求
}
