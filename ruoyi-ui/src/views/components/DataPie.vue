<template>
  <div class="data-pie">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8" class="pie-col">
        <div id="pie-business" style="height: 300px;" />
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8" class="pie-col">
        <div id="pie-originalpolicy" style="height: 300px;" />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  data() {
    return {
    };
  },
  mounted() {
    this.createPie('pie-business', [
      { value: 1048, name: '已审批政策' },
      { value: 20, name: '未审批政策' }
    ]);
    this.createPie('pie-originalpolicy', [
      { value: 1048, name: '已解读政策' },
      { value: 735, name: '未解读政策' }
    ]);
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank")
    },
    createPie(key, data) {
      const chartDom = document.getElementById(key);
      console.log(this, chartDom);
      const myChart = echarts.init(chartDom);
      const option = {
        // title: {
        //   text: "Anscombe's quartet",
        //   left: 'center',
        //   top: '-2%'
        // },
        backgroundColor: '#fff',
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: '内容饼状图',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 40,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data
          }
        ]
      };
      myChart.setOption(option);
    }
  }
};
</script>

<style scoped lang="scss">
.data-pie {
  .pie-col {}
}
</style>

