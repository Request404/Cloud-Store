<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.phone"
                placeholder="手机号码"
                style="width: 200px;"
                class="filter-item"
                @keyup.enter.native="handleFilter" />
      <el-button v-waves
                 class="filter-item"
                 type="primary"
                 icon="el-icon-search"
                 @click="handleFilter">
        搜索
      </el-button>
      <el-button v-permission="'PAGER_SYSTEM_USER_CREATE'"
                 class="filter-item"
                 style="margin-left: 10px;"
                 type="primary"
                 icon="el-icon-edit"
                 @click="handleCreate">
        新增
      </el-button>
      <el-button v-permission="'PAGER_SYSTEM_USER_CREATE'"
                 :loading="downloadLoading"
                 class="filter-item"
                 style="margin-left: 10px;"
                 type="warning"
                 icon="el-icon-download"
                 @click="handleExport">
        导出
      </el-button>
    </div>
    <el-table v-loading="listLoading"
              :data="list"
              stripe
              fit
              highlight-current-row
              @row-click="tableSelectRows"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection"
                       width="55">
      </el-table-column>
      <el-table-column label="手机号码"
                       align="left">
        <template slot-scope="scope">
          <span>{{ scope.row.phone }}</span>
        </template>
      </el-table-column>
      <el-table-column label="姓名"
                       align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="拥有角色"
                       align="center">
        <template slot-scope="scope">
          <span>{{ getRoles(scope.row) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态"
                       class-name="status-col"
                       align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.state | statusFilter">
            {{ scope.row.state ? '禁用' : '启用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作人"
                       align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.updateUser }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间"
                       align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作"
                       width="180"
                       fixed="right"
                       class-name="small-padding fixed-width"
                       align="center">
        <template slot-scope="scope">
          <el-button v-permission="'PAGER_SYSTEM_USER_MODIFY'"
                     type="primary"
                     size="small"
                     icon="el-icon-edit"
                     @click="handleModify(scope.row)">
            编辑
          </el-button>
          <el-button v-if="scope.row.state"
                     v-permission="'PAGER_SYSTEM_USER_MODIFY'"
                     type="success"
                     size="small"
                     icon="el-icon-circle-check"
                     @click="handleStatus(scope.row, false)">
            启用
          </el-button>
          <el-button v-else
                     v-permission="'PAGER_SYSTEM_USER_MODIFY'"
                     type="danger"
                     size="small"
                     icon="el-icon-circle-close"
                     @click="handleStatus(scope.row, true)">
            禁用
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>listQuery.pageSize"
                :total="total"
                :page.sync="listQuery.page"
                :limit.sync="listQuery.pageSize"
                @pagination="getUserList" />
    <i-form ref="dataForm"
            :form-data="formData" />
  </div>
</template>

<script>
import { fetchList, putModify } from '@/api/sysuser'
import { commonDownload } from '@/utils/download'
import waves from '@/directive/waves' // Waves directive
import permission from '@/directive/permission'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import IForm from './form'

export default {
  name: 'UserManage',
  components: { Pagination, IForm },
  directives: { waves, permission },
  filters: {
    statusFilter (status) {
      const statusMap = {
        false: 'success',
        true: 'danger'
      }
      return statusMap[status]
    }
  },
  data () {
    return {
      list: null,
      downloadLoading: false,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        pageSize: 10,
        phone: undefined
      },
      tableSelectData: {},
      multipleSelection: [],
      statusOptions: ['published', 'draft', 'deleted'],
      formData: {
        avatar: undefined,
        deleteStatus: false,
        id: undefined,
        password: undefined,
        phone: undefined,
        roleIds: [],
        sysName: undefined,
        username: undefined
      }
    }
  },
  created () {
    this.getUserList()
  },
  methods: {
    tableSelectRows (row, event, column) {
      this.tableSelectData = row
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
      console.log(this.multipleSelection)

    },
    getUserList () {
      this.listLoading = true
      fetchList(this.listQuery).then(response => {
        this.list = response.data
        this.total = response.total
        this.list.forEach(k => {
          this.roleIds = JSON.parse(k.roleIds)
        })
        setTimeout(() => {
          this.listLoading = false
        }, 1.5 * 1000)
      }).catch(() => {
        this.listLoading = false
      })
    },
    getRoles (row) { // 拥有的角色
      let content = ''
      if (row.roles !== null) {
        for (let i = 0; i < row.roles.length; i++) {
          content += row.roles[i].roleName + ' | '
        }
      }
      return content
    },
    handleFilter () { // 查询列表
      this.listQuery.page = 1
      this.getUserList()
    },
    handleCreate () { // 创建
      const _this = this.$refs['dataForm']
      _this.dialogStatus = 'create'
      _this.passwordVisiable = true
      _this.dialogFormVisible = true
      this.restForm()
    },
    handleModify (row) { // 编辑弹框
      this.formData = Object.assign({}, row) // copy obj
      this.formData.roleIds = row.roles.map(item => item.id)
      const _this = this.$refs['dataForm']
      _this.dialogStatus = 'update'
      _this.passwordVisiable = false
      _this.dialogFormVisible = true
    },
    handleStatus (row, state) {
      putModify({ id: row.id, state: state }).then(() => {
        this.$message({
          message: '更新成功',
          type: 'success'
        })
        this.getUserList()
      })
    },
    restForm () {
      this.formData = {
        avatar: undefined,
        deleteStatus: false,
        id: undefined,
        password: undefined,
        phone: undefined,
        roleIds: [],
        sysName: undefined,
        username: undefined
      }
    },
    handleExport () {
      const param = {
        fileName: '用户信息.xlsx',
        ids: this.multipleSelection.map(item => item.id),
        type: 'excel'
      }
      this.downloadLoading = true
      commonDownload('/admin/system/user/download', param)
      this.downloadLoading = false
    }
  }
}
</script>
