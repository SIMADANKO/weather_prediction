<template>
    <el-main class="bg edit_wrap forecast">
      <el-form ref="form" :model="form" status-icon label-width="160px">
        <el-row class="row_e">
          <el-col :xs="24" :sm="12" v-for="(field, index) in inputFields" :key="index" class="el_form_item_warp">
            <el-form-item :label="field.label" :prop="field.prop">
              <el-input v-model.number="form[field.prop]" :placeholder="'请输入' + field.label"></el-input>
            </el-form-item>
          </el-col>
  
          <el-col :xs="24" :sm="24" class="el_form_btn_warp">
            <el-form-item>
              <el-col :xs="24" :sm="12" class="el_form_btn el_form_btn_1">
                <el-button style="width: 100%;" type="primary" @click="submit">提交</el-button>
              </el-col>
              <el-col :xs="24" :sm="12" class="el_form_btn el_form_btn_2">
                <el-button style="width: 100%;" @click="cancel">取消</el-button>
              </el-col>
            </el-form-item>
          </el-col>
  
          <el-col v-if="result" :xs="24" class="el_form_result">
            <div style="margin-top: 20px; font-size: 16px; color: #333;">
              {{ result }}
            </div>
          </el-col>
        </el-row>
      </el-form>
    </el-main>
  </template>
  
  <script>
  export default {
    data() {
      return {
        form: {
          month: 7,
          year: 2024,
          season: 2,
          district_code: 2,
          cluster: 1,
          rainfall_1m: 42.3,
          rainfall_3m: 45.2,
          rainfall_ma: 42.06666666666667,
          sin_month: 0.866,
          cos_month: 0.5
        },
        result: '',
        inputFields: [
          { label: '月份', prop: 'month' },
          { label: '年份', prop: 'year' },
          { label: '季节', prop: 'season' },
          { label: '区县代码', prop: 'district_code' },
          { label: '聚类', prop: 'cluster' },
          { label: '月前1个月降水量', prop: 'rainfall_1m' },
          { label: '月前3个月降水量', prop: 'rainfall_3m' },
          { label: '月移动平均降水量', prop: 'rainfall_ma' },
          { label: '月份周期特征sin', prop: 'sin_month' },
          { label: '月份周期特征cos', prop: 'cos_month' }
        ]
      };
    },
    methods: {
      submit() {
        console.log("Submit method triggered"); 
        const featureArray = [
    this.form.month,
    this.form.year,
    this.form.season,
    this.form.district_code,
    this.form.cluster,
    this.form.rainfall_3m,
    this.form.rainfall_1m,
    this.form.rainfall_ma,
    this.form.sin_month,
    this.form.cos_month
  ];

  const queryParams = new URLSearchParams({
    month: this.form.month,
    year: this.form.year,
    season: this.form.season,
    district_code: this.form.district_code,
    cluster: this.form.cluster,
    rainfall_3m: this.form.rainfall_3m,
    rainfall_1m: this.form.rainfall_1m,
    rainfall_ma: this.form.rainfall_ma,
    sin_month: this.form.sin_month,
    cos_month: this.form.cos_month
  }).toString();

  const url = `http://localhost:5000/api/predict?${queryParams}`;

  fetch(url)
    .then((response) => response.json())
    .then((data) => {
      console.log(data); // 打印返回的数据，确保包含 rainfall 字段
      if (data && data.rainfall !== undefined) {
        this.result = `预测：${this.form.year}年${this.form.month}月，该地区的降雨量为 ${data.rainfall} 毫米`;
      } else {
        this.result = '后端未返回正确的降雨量数据';
      }
    })
    .catch((err) => {
      this.result = '请求失败，请检查服务器是否开启';
      console.error(err);
    });
      },
      cancel() {
        this.$router.go(-1);
      }
    }
  };
  </script>
  
  <style scoped>
  .el_form_result {
    padding: 20px;
    background: #f7f7f7;
    border: 1px solid #ddd;
    border-radius: 6px;
  }
  </style>
  