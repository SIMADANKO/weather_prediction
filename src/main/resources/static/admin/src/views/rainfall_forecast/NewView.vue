<template>
  <div>
    <h1>降雨量预测与可视化系统</h1>
    
    <el-tabs v-model="activeTab" type="card">
      <!-- 单月降雨量预测 -->
      <el-tab-pane label="单月降雨量预测" name="singleMonth">
        <el-form ref="singleMonthForm" :model="singleMonthForm" status-icon label-width="160px">
          <el-row class="row_e">
            <el-col :xs="24" :sm="12" class="el_form_item_warp">
              <el-form-item label="年份" prop="year">
                <el-input v-model.number="singleMonthForm.year" placeholder="请输入年份"></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" class="el_form_item_warp">
              <el-form-item label="月份" prop="month">
                <el-select v-model="singleMonthForm.month" placeholder="请选择月份" style="width: 100%;">
                  <el-option v-for="i in 12" :key="i" :label="i + '月'" :value="i"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" class="el_form_item_warp">
              <el-form-item label="区县" prop="districtName">
                <el-select v-model="singleMonthForm.districtName" placeholder="请选择区县" style="width: 100%;">
                  <el-option v-for="district in districts" :key="district.name" :label="district.name" :value="district.name"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :xs="24" :sm="24" class="el_form_btn_warp">
              <el-form-item>
                <el-button type="primary" @click="submitSingleMonth">提交预测</el-button>
                <el-button @click="resetSingleMonth">重置</el-button>
              </el-form-item>
            </el-col>

            <el-col v-if="singleMonthResult" :xs="24" class="el_form_result">
              <div class="result-box">
                <h3>预测结果</h3>
                <div class="result-content">
                  {{ singleMonthResult }}
                </div>
              </div>
            </el-col>
          </el-row>
        </el-form>
      </el-tab-pane>

      <!-- 年度降雨量预测 -->
      <el-tab-pane label="年度降雨量预测" name="yearlyForecast">
        <el-form ref="yearlyForm" :model="yearlyForm" status-icon label-width="160px">
          <el-row class="row_e">
            <el-col :xs="24" :sm="12" class="el_form_item_warp">
              <el-form-item label="年份" prop="year">
                <el-input v-model.number="yearlyForm.year" placeholder="请输入年份"></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" class="el_form_item_warp">
              <el-form-item label="区县" prop="districtName">
                <el-select v-model="yearlyForm.districtName" placeholder="请选择区县" style="width: 100%;">
                  <el-option v-for="district in districts" :key="district.name" :label="district.name" :value="district.name"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            
            <el-col :xs="24" :sm="24" class="el_form_btn_warp">
              <el-form-item>
                <el-button type="primary" @click="predictYearlyRainfall">预测全年降雨量</el-button>
                <el-button @click="resetYearlyForm">重置</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

        <!-- 图表展示区域 -->
        <div v-if="showYearlyChart" ref="yearlyChart" class="chart-container" style="width: 100%; height: 400px;"></div>
      </el-tab-pane>

      <!-- 多地区对比 -->
      <el-tab-pane label="多地区对比" name="multiDistrict">
        <el-form ref="multiDistrictForm" :model="multiDistrictForm" status-icon label-width="160px">
          <el-row class="row_e">
            <el-col :xs="24" :sm="12" class="el_form_item_warp">
              <el-form-item label="年份" prop="year">
                <el-input v-model.number="multiDistrictForm.year" placeholder="请输入年份"></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="12" class="el_form_item_warp">
              <el-form-item label="月份" prop="month">
                <el-select v-model="multiDistrictForm.month" placeholder="请选择月份" style="width: 100%;">
                  <el-option v-for="i in 12" :key="i" :label="i + '月'" :value="i"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" class="el_form_item_warp">
              <el-form-item label="区县" prop="districtNames">
                <el-select 
                  v-model="multiDistrictForm.districtNames" 
                  multiple 
                  placeholder="请选择多个区县" 
                  style="width: 100%;">
                  <el-option 
                    v-for="district in districts" 
                    :key="district.name" 
                    :label="district.name" 
                    :value="district.name">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            
            <el-col :xs="24" :sm="24" class="el_form_btn_warp">
              <el-form-item>
                <el-button type="primary" @click="compareDistricts">对比预测</el-button>
                <el-button @click="resetMultiDistrictForm">重置</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

        <!-- 多地区对比图表 -->
        <div v-if="showMultiDistrictChart" ref="multiDistrictChart" class="chart-container" style="width: 100%; height: 400px;"></div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: 'RainfallForecastNew',
  data() {
    return {
      activeTab: 'singleMonth',
      // 单月预测
      singleMonthForm: {
        month: 7,
        year: 2024,
        districtName: '仁化县'
      },
      singleMonthResult: '',
      
      // 年度预测
      yearlyForm: {
        year: 2024,
        districtName: '仁化县'
      },
      yearlyRainfall: [],
      showYearlyChart: false,
      
      // 多地区对比
      multiDistrictForm: {
        year: 2024,
        month: 7,
        districtNames: ['仁化县', '武江区', '浈江区']
      },
      showMultiDistrictChart: false,
      districtRainfallData: [],
      
      // 区县数据 - 根据JSON文件
      districts: [
        { name: "乐昌市", code: 0, cluster: 1 },
        { name: "乳源瑶族自治县", code: 1, cluster: 1 },
        { name: "仁化县", code: 2, cluster: 1 },
        { name: "南雄市", code: 3, cluster: 1 },
        { name: "始兴县", code: 4, cluster: 0 },
        { name: "新丰县", code: 5, cluster: 0 },
        { name: "曲江区", code: 6, cluster: 0 },
        { name: "武江区", code: 7, cluster: 1 },
        { name: "浈江区", code: 8, cluster: 1 },
        { name: "翁源县", code: 9, cluster: 0 }
      ],
      
      // 存储图表实例
      myCharts: {}
    };
  },
  methods: {
    // 单月降雨量预测提交方法
    submitSingleMonth() {
      // 构建请求数据
      const requestData = {
        districtName: this.singleMonthForm.districtName,
        year: this.singleMonthForm.year,
        month: this.singleMonthForm.month
      };
      
      // 发送POST请求
      this.$axios.post('http://localhost:5000/api/rainfall/predict', requestData)
        .then(response => {
          const data = response.data;
          if (data && data.rainfall !== undefined) {
            this.singleMonthResult = `预测：${data.year}年${data.month}月，${data.districtName}的降雨量为 ${data.rainfall.toFixed(2)} 毫米`;
          } else {
            this.singleMonthResult = '后端未返回正确的降雨量数据';
          }
        })
        .catch(err => {
          if (err.response && err.response.data && err.response.data.error) {
            this.singleMonthResult = `请求失败: ${err.response.data.error}`;
          } else {
            this.singleMonthResult = '请求失败，请检查服务器是否开启';
          }
          console.error(err);
        });
    },
    
    // 重置单月表单
    resetSingleMonth() {
      this.$refs.singleMonthForm.resetFields();
      this.singleMonthResult = '';
    },
    
    // 年度降雨量预测
    predictYearlyRainfall() {
      const { year, districtName } = this.yearlyForm;
      this.yearlyRainfall = [];
      
      // 创建12个月份的预测任务
      const promiseArr = [];
      
      for (let month = 1; month <= 12; month++) {
        // 构建POST请求数据
        const requestData = {
          districtName: districtName,
          year: year,
          month: month
        };
        
        promiseArr.push(
          this.$axios.post('http://localhost:5000/api/rainfall/predict', requestData)
            .then(response => {
              const data = response.data;
              console.log(`月份${month}的预测结果:`, data);
              if (data && data.rainfall !== undefined) {
                return {
                  month: data.month,
                  rainfall: data.rainfall
                };
              }
              throw new Error('未返回正确的降雨量数据');
            })
        );
      }
      
      // 并行请求所有月份数据
      Promise.all(promiseArr)
        .then(results => {
          // 按月份排序
          results.sort((a, b) => a.month - b.month);
          this.yearlyRainfall = results;
          this.showYearlyChart = true;
          
          console.log('获取到年度数据:', results);
          
          // 使用setTimeout确保DOM已更新
          setTimeout(() => {
            this.renderYearlyChart();
            // 触发窗口resize事件以确保图表正确渲染
            window.dispatchEvent(new Event('resize'));
          }, 300);
        })
        .catch(err => {
          this.$message.error('年度降雨量预测失败: ' + err.message);
          console.error(err);
        });
    },
    
    // 重置年度表单
    resetYearlyForm() {
      this.$refs.yearlyForm.resetFields();
      this.yearlyRainfall = [];
      this.showYearlyChart = false;
    },
    
    // 渲染年度降雨量图表
    renderYearlyChart() {
      console.log('开始渲染年度图表', this.yearlyRainfall);
      
      // 获取DOM元素
      const chartDom = this.$refs.yearlyChart;
      if (!chartDom) {
        console.error('图表DOM元素未找到');
        return;
      }
      
      // 清除可能的旧实例
      if (this.myCharts.yearlyChart) {
        this.myCharts.yearlyChart.dispose();
      }
      
      // 初始化图表
      this.myCharts.yearlyChart = echarts.init(chartDom);
      
      // 构建数据
      const months = this.yearlyRainfall.map(item => item.month + '月');
      const rainfallData = this.yearlyRainfall.map(item => item.rainfall);
      
      // 设置图表选项
      const option = {
        title: {
          text: `${this.yearlyForm.year}年 ${this.yearlyForm.districtName} 月度降雨量预测`,
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          formatter: '{b}: {c} 毫米'
        },
        xAxis: {
          type: 'category',
          data: months,
          name: '月份'
        },
        yAxis: {
          type: 'value',
          name: '降雨量 (毫米)'
        },
        series: [{
          name: '降雨量',
          type: 'bar',
          data: rainfallData,
          itemStyle: {
            color: function(params) {
              // 使用简单颜色
              return '#5470c6';
            }
          }
        }]
      };
      
      // 设置选项并渲染
      this.myCharts.yearlyChart.setOption(option);
      console.log('图表渲染完成');
      
      // 窗口大小改变时，重新调整图表大小
      window.addEventListener('resize', () => {
        if (this.myCharts.yearlyChart) {
          this.myCharts.yearlyChart.resize();
        }
      });
    },
    
    // 多地区对比
    compareDistricts() {
      if (!this.multiDistrictForm.districtNames || this.multiDistrictForm.districtNames.length === 0) {
        this.$message.warning('请至少选择一个区县');
        return;
      }
      
      const { year, month, districtNames } = this.multiDistrictForm;
      this.districtRainfallData = [];
      
      // 创建多个区县的预测任务
      const promiseArr = [];
      
      for (const districtName of districtNames) {
        // 构建请求数据
        const requestData = {
          districtName: districtName,
          year: year,
          month: month
        };
        
        promiseArr.push(
          this.$axios.post('http://localhost:5000/api/rainfall/predict', requestData)
            .then(response => {
              const data = response.data;
              console.log(`区县${districtName}的预测结果:`, data);
              if (data && data.rainfall !== undefined) {
                return {
                  districtName: data.districtName,
                  rainfall: data.rainfall
                };
              }
              throw new Error(`${districtName}未返回正确的降雨量数据`);
            })
        );
      }
      
      // 并行请求所有区县数据
      Promise.all(promiseArr)
        .then(results => {
          this.districtRainfallData = results;
          this.showMultiDistrictChart = true;
          
          console.log('获取到多地区数据:', results);
          
          // 使用setTimeout确保DOM已更新
          setTimeout(() => {
            this.renderMultiDistrictChart();
            // 触发窗口resize事件以确保图表正确渲染
            window.dispatchEvent(new Event('resize'));
          }, 300);
        })
        .catch(err => {
          this.$message.error('多地区降雨量预测失败: ' + err.message);
          console.error(err);
        });
    },
    
    // 重置多地区表单
    resetMultiDistrictForm() {
      this.$refs.multiDistrictForm.resetFields();
      this.districtRainfallData = [];
      this.showMultiDistrictChart = false;
    },
    
    // 渲染多地区对比图表
    renderMultiDistrictChart() {
      console.log('开始渲染多地区对比图表', this.districtRainfallData);
      
      // 获取DOM元素
      const chartDom = this.$refs.multiDistrictChart;
      if (!chartDom) {
        console.error('多地区图表DOM元素未找到');
        return;
      }
      
      // 清除可能的旧实例
      if (this.myCharts.multiDistrictChart) {
        this.myCharts.multiDistrictChart.dispose();
      }
      
      // 初始化图表
      this.myCharts.multiDistrictChart = echarts.init(chartDom);
      
      // 构建数据
      const districtNames = this.districtRainfallData.map(item => item.districtName);
      const rainfallData = this.districtRainfallData.map(item => item.rainfall);
      
      // 设置图表选项
      const option = {
        title: {
          text: `${this.multiDistrictForm.year}年${this.multiDistrictForm.month}月 各区县降雨量对比`,
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          formatter: '{b}: {c} 毫米'
        },
        xAxis: {
          type: 'category',
          data: districtNames,
          name: '区县',
          axisLabel: {
            interval: 0,
            rotate: 30
          }
        },
        yAxis: {
          type: 'value',
          name: '降雨量 (毫米)'
        },
        series: [{
          name: '降雨量',
          type: 'bar',
          data: rainfallData,
          itemStyle: {
            color: function(params) {
              // 为不同区县生成不同颜色
              const colorList = [
                '#5470c6', '#91cc75', '#fac858', '#ee6666', '#73c0de',
                '#3ba272', '#fc8452', '#9a60b4', '#ea7ccc'
              ];
              return colorList[params.dataIndex % colorList.length];
            }
          }
        }]
      };
      
      // 设置选项并渲染
      this.myCharts.multiDistrictChart.setOption(option);
      console.log('多地区图表渲染完成');
      
      // 窗口大小改变时，重新调整图表大小
      window.addEventListener('resize', () => {
        if (this.myCharts.multiDistrictChart) {
          this.myCharts.multiDistrictChart.resize();
        }
      });
    }
  }
};
</script>

<style scoped>
h1 {
  text-align: center;
  margin: 20px 0;
  color: #2c3e50;
}

.el_form_item_warp {
  margin-bottom: 20px;
}

.el_form_btn_warp {
  margin-top: 20px;
  margin-bottom: 20px;
}

.el_form_result {
  margin-top: 30px;
}

.result-box {
  background-color: #fff;
  border-radius: 6px;
  padding: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.result-box h3 {
  margin-top: 0;
  color: #409EFF;
  border-bottom: 1px solid #EBEEF5;
  padding-bottom: 10px;
}

.result-content {
  font-size: 16px;
  color: #333;
  padding: 10px 0;
}

.chart-container {
  width: 100%;
  height: 400px;
  margin-top: 30px;
  background-color: #fff;
  border-radius: 6px;
  padding: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.el-tabs__header {
  margin-bottom: 25px;
}
</style>