<template>
  <el-dialog :title="textMap[dialogStatus]"
             :visible.sync="dialogFormVisible"
             @open="handleOpen">
    <el-form ref="dataForm"
             :rules="rules"
             :model="formData"
             label-position="top"
             label-width="100px"
             width="50%">
      <el-form-item label="手机号码"
                    prop="phone">
        <el-input v-model="formData.phone"
                  placeholder="请设置" />
      </el-form-item>
      <el-form-item label="姓名"
                    prop="username">
        <el-input v-model="formData.username"
                  placeholder="请设置" />
      </el-form-item>
      <template v-if="passwordVisiable">
        <el-form-item label="密码"
                      prop="password">
          <el-input v-model="formData.password"
                    type="password"
                    placeholder="请设置" />
        </el-form-item>
      </template>
      <el-form-item label="拥有角色"
                    prop="roleIds">
        <el-select v-model="formData.roleIds"
                   class="filter-item"
                   multiple
                   placeholder="请选择">
          <el-option v-for="(item,index) in roleCodeOptions"
                     :key="index"
                     :label="item.roleName"
                     :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="头像"
                    prop="avatar">
        <div style="margin-bottom: 20px;">
          <Upload v-model="formData.avatar" />
        </div>
      </el-form-item>
    </el-form>
    <div slot="footer"
         class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取消</el-button>
      <el-button type="primary"
                 @click="dialogStatus==='create'?handleCreateData():handleUpdateData()">确认
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
import { postCreate, putModify } from '@/api/sysuser'
import { postRoleList } from '@/api/role'
import Upload from '@/components/Upload/singleImage3'

export default {
  name: 'Form',
  components: { Upload },
  props: {
    formData: {
      type: Object,
      default: () => ({
        sysName: '',
        username: ''
      })
    }
  },
  data () {
    return {
      dialogFormVisible: false,
      dialogPvVisible: false,
      passwordVisiable: false,
      dialogStatus: 'create',
      textMap: {
        update: '编辑',
        create: '新增'
      },
      roleCodeOptions: [],
      rules: {
        phone: [{ required: true, message: '手机号码不能为空', trigger: 'blur' }],
        username: [{ required: true, message: '姓名不能为空', trigger: 'blur' }],
        password: [{ required: true, message: '密码不能为空', trigger: 'blur' }],
        roleIds: [{ required: true, message: '角色不能为空', trigger: 'blur' }],
        avatar: [{ required: true, message: '用户头像不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {
    handleOpen () {
      postRoleList({}).then(response => { // 获取角色列表
        this.roleCodeOptions = response.data
      })
    },
    handleCreateData () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.formData.createUser = this.$store.state.user.username
          this.formData.loginCode = this.formData.username
          postCreate(this.formData).then(() => {
            this.dialogFormVisible = false
            this.$message({
              message: '创建成功',
              type: 'success'
            })
            this.$parent.getUserList()
          })
        }
      })
    },
    handleUpdateData () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const formDataData = Object.assign({}, this.formData)
          formDataData.createUser = this.$store.state.user.username
          putModify(formDataData).then(() => {
            this.dialogFormVisible = false
            this.$message({
              message: '更新成功',
              type: 'success'
            })
            this.$parent.getUserList()
          })
        }
      })
    }
  }
}
</script>

<style scoped>
</style>
