<template>
  <el-main class="bg table_wrap">
    <el-form label-position="right" :model="query" class="form p_4" label-width="120">
      <el-row class="rows row1">

          
                                                              <el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
                      <el-form-item label="地区名称">
                                                      <el-input v-model="query.region_name"></el-input>
                                                </el-form-item>
                    </el-col>
                                                                                                                                                                                                                                                                </el-row>
      <el-row class="rows row2">
      	<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap search_btns">
         <el-col :xs="24" :sm="10" :lg="8" class="search_btn_1 search_btn_wrap_1 btns">
                              <el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
                <el-button @click="reset()" style="margin-right: 74px;" class="search_btn_reset">重置</el-button>
                                            <el-button v-if="$check_option('/rainfall_data/table','export_db')" @click="export_db('dataTable')"  class="derive" style="margin-right: 155px;">导出</el-button>
                                        <el-button v-if="$check_action('/rainfall_data/table','add') || $check_action('/rainfall_data/view','add')" @click="$router.push('./view?')" class="add">添加</el-button>
            <el-button v-if="$check_action('/rainfall_data/table','del') || $check_action('/rainfall_data/view','del')" class="search_btn_del" type="danger" @click="delInfo()">删除</el-button>
                              <download-excel v-if="$check_option('/rainfall_data/table','import_db')" class="export-excel-wrapper" :data="DetailsForm" :fields="json_fields" name="数据导入表格.xls" >
                  <el-button type="success">下载导入文档</el-button>
                </download-excel>
                <el-upload v-if="$check_option('/rainfall_data/table','import_db')" action accept = ".xlsx, .xls" :auto-upload="false" :show-file-list="false" :on-change="handle_import">
                  <el-button type="primary">导入</el-button>
                </el-upload>
                        </el-col>
       
        </el-col>
      </el-row>
    </el-form>
	    <el-table :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" id="dataTable">
	                <el-table-column type="selection" tooltip-effect="dark" width="55">
            </el-table-column>
                                                  <el-table-column prop="region_name" @sort-change="$sortChange" label="地区名称"                                v-if="$check_field('get','region_name')" min-width="200">
                                </el-table-column>
                                              <el-table-column prop="average_rainfall" @sort-change="$sortChange" label="雨量平均值"                                v-if="$check_field('get','average_rainfall')" min-width="200">
                                </el-table-column>
                                              <el-table-column prop="maximum_rainfall" @sort-change="$sortChange" label="雨量最大值"                                v-if="$check_field('get','maximum_rainfall')" min-width="200">
                                </el-table-column>
                                              <el-table-column prop="minimum_rainfall" @sort-change="$sortChange" label="雨量最小值"                                v-if="$check_field('get','minimum_rainfall')" min-width="200">
                                </el-table-column>
                                              <el-table-column prop="rainfall_standard_value" @sort-change="$sortChange" label="雨量标准值"                                v-if="$check_field('get','rainfall_standard_value')" min-width="200">
                                </el-table-column>
                                              <el-table-column prop="rainfall_warning_value" @sort-change="$sortChange" label="雨量预警值"                                v-if="$check_field('get','rainfall_warning_value')" min-width="200">
                                </el-table-column>
                                              <el-table-column prop="data_details" @sort-change="$sortChange" label="数据详情"                                v-if="$check_field('get','data_details')" min-width="200">
                                </el-table-column>
                    				        
        
                
      <el-table-column sortable prop="create_time" label="创建时间" min-width="200">
        <template slot-scope="scope">
          {{ $toTime(scope.row["create_time"],"yyyy-MM-dd hh:mm:ss") }}
        </template>
      </el-table-column>

      <el-table-column sortable prop="update_time" label="更新时间" min-width="200">
        <template slot-scope="scope">
          {{ $toTime(scope.row["update_time"],"yyyy-MM-dd hh:mm:ss") }}
        </template>
      </el-table-column>

        
        
        
        
        

                <el-table-column label="操作" min-width="120" v-if="$check_action('/rainfall_data/table','set') || $check_action('/rainfall_data/view','set') || $check_action('/rainfall_data/view','get')
					 ">
        

      <template slot-scope="scope">
        <router-link class="el-button el-button--small is-plain el-button--success" style="margin: 5px !important;"
                     v-if="$check_action('/rainfall_data/table','set') || $check_action('/rainfall_data/view','set') || $check_action('/rainfall_data/view','get')"
                                 :to="'./view?' + field + '=' + scope.row[field]"
                                 size="small">
          <span>详情</span>
        </router-link>
                                                  		  		        </template>
    </el-table-column>

    </el-table>

    <!-- 分页器 -->
    <div class="mt text_center">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="query.page" :page-sizes="[7, 10, 30, 100]" :page-size="query.size"
                     layout="total, sizes, prev, pager, next, jumper" :total="count">
      </el-pagination>
    </div>
    <!-- /分页器 -->
                                                                                                                                  
    <div class="modal_wrap" v-if="showModal">
      <div class="modal_box">
        <!-- <div class="modal_box_close" @click="closeModal">X</div> -->
        <p class="modal_box_title">重要提醒</p>
        <p class="modal_box_text">当前有数据达到预警值！</p>
        <p class="modal_box_text">{{ message }}</p>
        <div class="btn_box">
          <span @click="closeModal">取消</span>
          <span @click="closeModal">确定</span>
        </div>
      </div>
    </div>


  </el-main>
</template>
<script>
  import mixin from "@/mixins/page.js";
    import * as XLSX from 'xlsx/xlsx.mjs'
  
  export default {
    mixins: [mixin],
    data() {
      return {
        // 弹框
        showModal: false,
        // 获取数据地址
        url_get_list: "~/api/rainfall_data/get_list?like=0",
        url_del: "~/api/rainfall_data/del?",

        // 字段ID
        field: "rainfall_data_id",
        // 查询
        query: {
          "size": 7,
          "page": 1,
                                                                      "region_name": "",
                                                                                                                                                                                                                                                                  "login_time": "",
          "create_time": "",
          "orderby": `create_time desc`
        },

                  // 数据
        list: [],
                                          json_fields: {
                                                  "地区名称":'region_name',
                                  "雨量平均值":'average_rainfall',
                                  "雨量最大值":'maximum_rainfall',
                                  "雨量最小值":'minimum_rainfall',
                                  "雨量标准值":'rainfall_standard_value',
                                  "雨量预警值":'rainfall_warning_value',
                                  "数据详情":'data_details',
                            },
            DetailsForm: [
              {
                                                              region_name:"文本类型",
                                            average_rainfall:"数字类型",
                                            maximum_rainfall:"数字类型",
                                            minimum_rainfall:"数字类型",
                                            rainfall_standard_value:"数字类型",
                                            rainfall_warning_value:"数字类型",
                                            data_details:"多文本类型",
                                },
            ],
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                		  		  message: '',
      }
    },
    methods: {
      // 关闭弹框
      closeModal(){
        this.showModal = false;
      },
        		  /**
		   * @description 获取到列表事件
		   * @param {Object} res 响应结果
		   */
		  get_list_after: function get_list_after(res, func, url) {
		  	let _this = this
																									  },
        
        
                                                                                                                                                                                                                                                          open_tip() {
            const h = this.$createElement;

            var message = "";
            var list = this.list;

            var ifs = [
                                                                                                                                                                                                                                              {
                        title: "雨量预警值",
                        factor: "rainfall_warning_value",
                      start: 0,
                      end: 100,
                        type: "数外",
                        idx: 0
                      },
                                                                                    ];
            for (var n = 0; n < ifs.length; n++) {
              var o = ifs[n];
              for (var i = 0; i < list.length; i++) {
                var lt = list[i];
                if (o.type == "数内") {
                  if ((o.start || o.start === 0) && (o.end || o.end === 0)) {
                    if (lt[o.factor] > o.start && lt[o.factor] < o.end) {
                      o["idx"] = o["idx"] + 1;
                    }
                  } else if (o.start || o.start === 0) {
                    if (lt[o.factor] > o.start) {
                      o["idx"] = o["idx"] + 1;
                    }
                  } else if (o.end || o.end === 0) {
                    if (lt[o.factor] < o.end) {
                      o["idx"] = o["idx"] + 1;
                    }
                  }
                } else if (o.type == "数外") {
                  if ((o.start || o.start === 0) && (o.end || o.end === 0)) {
                    if (lt[o.factor] < o.start || lt[o.factor] > o.end) {
                      o["idx"] = o["idx"] + 1;
                    }
                  } else if (o.start || o.start === 0) {
                    if (lt[o.factor] < o.start) {
                      o["idx"] = o["idx"] + 1;
                    }
                  } else if (o.end || o.end === 0) {
                    if (lt[o.factor] > o.end) {
                      o["idx"] = o["idx"] + 1;
                    }
                  }
                } else if (o.type == "日内") {
                  if ((o.start) && (o.end)) {
                    if (lt[o.factor] > o.start && lt[o.factor] < o.end) {
                      o["idx"] = o["idx"] + 1;
                    }
                  } else if (o.start) {
                    if (lt[o.factor] < o.start) {
                      o["idx"] = o["idx"] + 1;
                    }
                  } else if (o.end) {
                    if (lt[o.factor] > o.end) {
                      o["idx"] = o["idx"] + 1;
                    }
                  }
                } else if (o.type == "日外") {
                  if (o.start && o.end) {
                    if (lt[o.factor] < o.start || lt[o.factor] > o.end) {
                      o["idx"] = o["idx"] + 1;
                    }
                  } else if (o.start) {
                    if (lt[o.factor] < o.start) {
                      o["idx"] = o["idx"] + 1;
                    }
                  } else if (o.end) {
                    if (lt[o.factor] > o.end) {
                      o["idx"] = o["idx"] + 1;
                    }
                  }
                }
              }

              if (o["idx"]) {
                message += o.title;
                if (o["type"] == "数内") {
                  if (o.start || o.start === 0) {
                    message += "大于" + o.start;
                  }
                  if ((o.start || o.start === 0) && (o.end || o.end === 0)) {
                    message += "并且";
                  }
                  if (o.end || o.end === 0) {
                    message += "小于" + o.end;
                  }
                } else if (o["type"] == "数外") {
                  if (o.start || o.start === 0) {
                    message += "小于" + o.start;
                  }
                  if ((o.start || o.start === 0) || (o.end || o.end === 0)) {
                    message += "或者";
                  }
                  if (o.end || o.end === 0) {
                    message += "大于" + o.end;
                  }
                } else if (o["type"] == "日内") {
                  if (o.start) {
                    message += "在" + o.start + "之后";
                  }
                  if (o.start && o.end) {
                    message += "并且";
                  }
                  if (o.end) {
                    message += "在" + o.end + "之前";
                  }
                } else if (o["type"] == "日外") {
                  if (o.start) {
                    message += "在" + o.start + "之前";
                  }
                  if (o.start || o.end) {
                    message += "或者";
                  }
                  if (o.end) {
                    message += "在" + o.end + "之后";
                  }
                }
                message += "的有" + o["idx"] + "条";
              }
            }

            if (message) {
              message += "，请尽快处理。";
              this.showModal = true;
			  this.message = message
              // this.$notify({
              // 	title: '提醒',
              // 	dangerouslyUseHTMLString: true,
              // 	message: h('i', {
              // 		style: 'color: teal'
              // 	}, message)
              // });
            }
          },
		  /**
		   * 页数变更
		   * @param {Object} page
		   */
		  handleCurrentChange(page) {
		      this.query.page = page;
		      this.get_list();
		  	setTimeout(() => {
		  		this.open_tip();
		  	}, 1000)
		  },
        
                  /**
           * 导出
           */
          export_db(tableid){
            // var query = this.query;
            // this.$get("~/api/rainfall_data/export_db?",query,(json)=>{
            // 	if(json.result){
            // 		console.log(json.result);
            // 	}else if(json.error){
            // 		console.error(json.error);
            // 	}
            // });


            //导出表格
            var idTmr;

            function getExplorer() {
              var explorer = window.navigator.userAgent;
              //ie
              if(explorer.indexOf("MSIE") >= 0) {
                return 'ie';
              }
              //firefox
              else if(explorer.indexOf("Firefox") >= 0) {
                return 'Firefox';
              }
              //Chrome
              else if(explorer.indexOf("Chrome") >= 0) {
                return 'Chrome';
              }
              //Opera
              else if(explorer.indexOf("Opera") >= 0) {
                return 'Opera';
              }
              //Safari
              else if(explorer.indexOf("Safari") >= 0) {
                return 'Safari';
              }
            }
            function Cleanup() {
              window.clearInterval(idTmr);
              CollectGarbage();
            }
            var tableToExcel = (function() {
              var uri = 'data:application/vnd.ms-excel;base64,',
                  template = '<html><head><meta charset="UTF-8"></head><body><table  border="1">{table}</table></body></html>',
                  base64 = function(
                      s) {
                    return window.btoa(unescape(encodeURIComponent(s)))
                  },
                  format = function(s, c) {
                    return s.replace(/{(\w+)}/g, function(m, p) {
                      return c[p];
                    })
                  }
              return function(table, name) {
                if(!table.nodeType)
                  table = document.getElementById(table)
                var ctx = {
                  worksheet: name || 'Worksheet',
                  table: table.innerHTML
                }
                window.location.href = uri + base64(format(template, ctx))
              }
            })();
            if(getExplorer() == 'ie') {
              var curTbl = document.getElementById(tableid);
              var oXL = new ActiveXObject("Excel.Application");
              var oWB = oXL.Workbooks.Add();
              var xlsheet = oWB.Worksheets(1);
              var sel = document.body.createTextRange();
              sel.moveToElementText(curTbl);
              sel.select();
              sel.execCommand("Copy");
              xlsheet.Paste();
              oXL.Visible = true;

              try {
                var fname = oXL.Application.GetSaveAsFilename("Excel.xls",
                    "Excel Spreadsheets (*.xls), *.xls");
              } catch(e) {
                print("Nested catch caught " + e);
              } finally {
                oWB.SaveAs(fname);
                oWB.Close(savechanges = false);
                oXL.Quit();
                oXL = null;
                idTmr = window.setInterval("Cleanup();", 1);
              }

            } else {
              tableToExcel(tableid)
            }


          },
        
        
                  /**
           * 导入
           */
          readFile(file){//文件读取
            return new Promise(resolve => {
              let reader = new FileReader();
              reader.readAsBinaryString(file);//以二进制的方式读取
              reader.onload = ev =>{
                resolve(ev.target.result);
              }
            })
          },
          async handle_import(ev){
            let file = ev.raw;
            console.log(file)
            if(!file){
              console.log("文件打开失败")
              return ;
            }else{
              let data = await this.readFile(file);
              let workbook = XLSX.read(data,{ type: "binary"});//解析二进制格式数据
              let worksheet = workbook.Sheets[workbook.SheetNames[0]];//获取第一个Sheet
              let result = XLSX.utils.sheet_to_json(worksheet);//json数据格式
              result.forEach((item) => {
                // 将中文的键名替换成英文的
                for (let k in this.json_fields) {
                  let newKey = this.json_fields[k];
                  if (newKey) {
                    item[newKey] = item[k];
                    delete item[k];
                  }
                }
              });
              let _this = this;
                                  for (let i=0;i<result.length;i++){
                    let url = "~/api/rainfall_data/add?";
                    await this.$post(url, result[i], function (json, status) {
                      console.log("提交结果：", json);
                      if (json.result) {
                        _this.$toast("操作成功", 'success');
                      } else if (json.error) {
                        _this.$toast(json.error.message, 'danger');
                      }
                    });
                  }
                            }
          },
        
                                                                                                                                                                                                                                                                		      deleteRow(index, rows) {
        rows.splice(index, 1);
      },

    },
	    created() {
                                                                                                                                                                                                                                                                  setTimeout(() => {
            this.open_tip();
          }, 1000)
            }
  }
</script>

<style type="text/css">
  .bg {
    background: white;
  }

  .form.p_4 {
    padding: 1rem;
  }

  .form .el-input {
    width: initial;
  }

  .mt {
    margin-top: 1rem;
  }

  .text_center {
    text-align: center;
  }

  .float-right {
    float: right;
  }


  .modal_wrap{
    width: 100vw;
    height: 100vh;
    position: fixed;
    top: 0;
    left: 0;
    background: rgba(0,0,0,0.5);
    z-index: 9999999999;
  }
  .modal_wrap .modal_box{
    width: 400px;
    height: auto;
    background: url("../../assets/modal_bg.jpg") no-repeat center;
    background-size: cover;
    position: absolute;
    top: 50%;
    left: 50%;
    margin-left: -200px;
    margin-top: -100px;
    border-radius: 10px;
    padding: 10px;
  }
  .modal_wrap .modal_box .modal_box_close{
    font-size: 20px;
    position: absolute;
    top: 10px;
    right: 10px;
    cursor: pointer;
  }
  .modal_wrap .modal_box .modal_box_title{
    text-align: center;
    font-size: 18px;
    margin: 16px auto;
    color: #fff;
    border-bottom: 1px solid rgba(117, 116, 116,0.5);
    padding-bottom: 16px;
    width: 356px;
  }
  .modal_wrap .modal_box .modal_box_text{
    text-align: center;
    font-size: 14px;
    color: #fff;
    margin: 5px auto;
    width: 90%;
  }
  .modal_wrap .modal_box .btn_box{
    display: flex;
    flex-direction: row;
    justify-content: center;
    margin-top: 42px;
  }
  .modal_wrap .modal_box .btn_box span{
    display: inline-block;
    width: 80px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    border: 1px solid #ccc;
    font-size: 14px;
    cursor: pointer;
    color: #fff;
  }
  .modal_wrap .modal_box .btn_box span:nth-child(2){
    background: #409EFF;
    color: #fff;
    border-color: #409EFF;
    margin-left: 15px;
  }
  .el-date-editor .el-range-separator{
  	width: 10% !important;
  }
</style>
