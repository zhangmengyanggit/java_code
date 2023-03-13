import request from '@/utils/request'

// 查询政策管理列表
export function listOriginalpolicy(query) {
  return request({
  
    url: '/scienceandtechnology/originalpolicy/list',
    method: 'get',
    params: query
  })
}

// 查询政策管理详细
export function getOriginalpolicy(id) {
  return request({
    url: '/scienceandtechnology/originalpolicy/' + id,
    method: 'get'
  })
}

// 新增政策管理
export function addOriginalpolicy(data) {
  return request({
    url: '/scienceandtechnology/originalpolicy',
    method: 'post',
    data: data
  })
}

// 修改政策管理
export function updateOriginalpolicy(data) {
  return request({
    url: '/scienceandtechnology/originalpolicy',
    method: 'put',
    data: data
  })
}

// 删除政策管理
export function delOriginalpolicy(id) {
  return request({
    url: '/scienceandtechnology/originalpolicy/' + id,
    method: 'delete'
  })
}
