import request from '@/utils/request'

// 查询企业列表
export function listEnterprise(query) {
  return request({
    url: '/scienceandtechnology/enterprise/list',
    method: 'get',
    params: query
  })
}

// 查询企业详细
export function getEnterprise(id) {
  return request({
    url: '/scienceandtechnology/enterprise/' + id,
    method: 'get'
  })
}

// 新增企业
export function addEnterprise(data) {
  return request({
    url: '/scienceandtechnology/enterprise',
    method: 'post',
    data: data
  })
}

// 修改企业
export function updateEnterprise(data) {
  return request({
    url: '/scienceandtechnology/enterprise',
    method: 'put',
    data: data
  })
}

// 删除企业
export function delEnterprise(id) {
  return request({
    url: '/scienceandtechnology/enterprise/' + id,
    method: 'delete'
  })
}
//获取市
export function getCity(CityId,level) {
  return request({
    url: '/common/getCityList?cityId=' + CityId + "&level=" + level,
    method: 'get'
  })
}
// 根据字典类型查询字典数据信息
export function getDicts(dictType) {
  return request({
    url: '/system/dict/data/type/' + dictType,
    method: 'get'
  })
}
// 查询所有标签
export function getTags() {
  return request({
    url: '/scienceandtechnology/tag/getTagsAll' ,
    method: 'get'
  })
}