<template>
  <div class="navbar">
    <hamburger :toggle-click="toggleSideBar"
               :is-active="sidebar.opened"
               class="hamburger-container" />

    <breadcrumb class="breadcrumb-container" />

    <div class="right-menu">
      <template v-if="device!=='mobile'">
        <error-log class="errLog-container right-menu-item" />

        <el-tooltip content="全屏"
                    effect="dark"
                    placement="bottom">
          <screenfull class="screenfull right-menu-item" />
        </el-tooltip>

        <el-tooltip content="换肤"
                    effect="dark"
                    placement="bottom">
          <theme-picker class="theme-switch right-menu-item" />
        </el-tooltip>
      </template>

      <el-dropdown class="avatar-container right-menu-item"
                   trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar?avatar+'?imageView2/1/w/80/h/80':'https://request404-1304131034.cos.ap-guangzhou.myqcloud.com/website-portrait/avatar.gif'"
               alt=""
               class="user-avatar">
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/">
            <el-dropdown-item>
              {{ username }}
            </el-dropdown-item>
            <el-dropdown-item divided>
              首页
            </el-dropdown-item>
          </router-link>
          <a target="_blank"
             href="https://github.com/SiGuiyang/vue-shop-admin/">
            <el-dropdown-item>
              项目地址
            </el-dropdown-item>
          </a>
          <el-dropdown-item divided>
            <span style="display:block;"
                  @click="logout">注销</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import ErrorLog from '@/components/ErrorLog'
import Screenfull from '@/components/Screenfull'
import ThemePicker from '@/components/ThemePicker'

export default {
  components: {
    Breadcrumb,
    Hamburger,
    ErrorLog,
    Screenfull,
    ThemePicker
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'username',
      'avatar',
      'device'
    ])
  },
  methods: {
    toggleSideBar () {
      this.$store.dispatch('toggleSideBar')
    },
    logout () {
      this.$store.dispatch('LogOut').then(() => {
        location.reload()// In order to re-instantiate the vue-router object to avoid bugs
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;

  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }

  .breadcrumb-container {
    float: left;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      margin: 0 8px;
    }

    .screenfull {
      height: 20px;
    }

    .international {
      vertical-align: top;
    }

    .theme-switch {
      vertical-align: 15px;
    }

    .avatar-container {
      height: 50px;
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 50%;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
