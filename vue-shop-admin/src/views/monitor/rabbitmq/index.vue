<template>
  <div class="app-container">
    <iframe id="activity"
            ref="iframe"
            :src="url"
            frameborder="0" />
  </div>
</template>
<script>

export default {
  name: 'RabbitMQ',
  data () {
    return {
      url: 'http://192.168.19.128:15672'
    }
  },
  mounted () {
    const activity = document.getElementById('activity')
    const deviceWidth = document.documentElement.clientWidth
    const deviceHeight = document.documentElement.clientHeight
    activity.style.width = (Number(deviceWidth) - 220) + 'px' // 数字是页面布局宽度差值
    activity.style.height = (Number(deviceHeight) - 120) + 'px' // 数字是页面布局高度差
    // if (this.$route.query.url) {
    //   this.url = this.$route.query.url
    //   window.addEventListener('message', (e) => {
    //     setTimeout(() => {
    //       this.isPlay = true
    //     }, 100)
    //     if (this.isPlay) {
    //       console.log('e.data', e.data)
    //       const iframe = document.getElementById('iframe')
    //       iframe.src = this.url + '&token=' + e.data
    //     }
    //   })
    // }
  },
  created () {
    window.open('http://192.168.19.128:15672/')
  }
}
</script>
