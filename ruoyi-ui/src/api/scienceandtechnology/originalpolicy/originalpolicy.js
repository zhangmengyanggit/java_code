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

// 查询所有标签
export function getTags() {
  return request({
    url: '/scienceandtechnology/tag/getTagsAll',
    method: 'get'
  })
}
 
// 查询政策解读详细
export function getInterpretationPolicy(id) {
  return request({
    url: '/scienceandtechnology/originalpolicy/getInterpretationPolicy/' + id,
    method: 'get'
  })
}
//获取市
export function getCity(cityId) {
  return request({
    url: '/common/getCityList?cityId=' + cityId,
    method: 'get'
  })
}
//获取部门
export function getDepartment(parentId){
  return request({
    url: '/common/getDepartment?parentId=' + parentId,
    method: 'get'
  })
}
//新增政策解读
export function addInterpretationPolicy(data) {
  return request({
    url: '/scienceandtechnology/originalpolicy/addInterpretationPolicy',
    method: 'post',
    data: data
  })
}

//修改政策解读
export function editInterpretationPolicy(data) {
  return request({
    url: '/scienceandtechnology/originalpolicy/editInterpretationPolicy',
    method: 'post',
    data: data
  })
}
