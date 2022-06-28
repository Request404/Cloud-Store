import service from '@/utils/request'

// 获取table data
export function postBlacklistPage (data) {
  return service({
    url: '/risk/blacklist/page',
    method: 'post',
    data
  })
}

// 新增
export function postBlacklist (data) {
  return service({
    url: '/risk/blacklist',
    method: 'post',
    data
  })
}

// 修改
export function putBlacklist (data) {
  return service({
    url: '/risk/blacklist',
    method: 'put',
    data
  })
}

// 删除
export function deleteBlacklist (data) {
  return service({
    url: `/risk/blacklist/${data.id}`,
    method: 'delete'
  })
}
