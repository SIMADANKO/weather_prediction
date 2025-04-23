<template>
	<el-main class="bg table_wrap">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row class="rows row1">
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="题目名称">
						<el-input v-model="query.title"></el-input>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="题目类型">
						<el-select v-model="query.type">
							<el-option value="" label="">请选择</el-option>
							<el-option value="单选题" label="单选题">单选题</el-option>
							<el-option value="多选题" label="多选题">多选题</el-option>
							<el-option value="判断题" label="判断题">判断题</el-option>
							<el-option value="填空题" label="填空题">填空题</el-option>
							<el-option value="主观题" label="主观题">主观题</el-option>
						</el-select>
					</el-form-item>
				</el-col>
			</el-row>
				<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap">
					<el-col :xs="24" :sm="12" :lg="12" class="search_btn_1">
						<el-form-item>
							<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
							<el-button @click="reset()" class="search_btn_reset">重置</el-button>
							<el-button v-if="$check_action('/evaluation/table','del')" class="float-right search_btn_del" type="danger" @click="delInfo()">删除</el-button>
							
							<el-button  v-if="$check_action('/evaluation/table','add')" class="float-right  search_btn_add" @click="$router.push('/evaluation_question_view/view?exam_id='+query.exam_id)">添加</el-button>
							<el-button v-if="$check_option('/evaluation/table','export_db')" @click="export_db('dataTable')" class="derive" style="margin-right: 155px;">导出</el-button>
							<el-upload v-if="$check_option('/evaluation/table','import_db')" action accept = ".xlsx, .xls" :auto-upload="false" :show-file-list="false" :on-change="handle_import">
								<el-button type="primary">导入</el-button>
							</el-upload>
							<download-excel v-if="$check_option('/evaluation/table','import_db')"
								class="export-excel-wrapper" :data="DetailsForm" :fields="json_fields" name="数据导入表格.xls">
								<el-button type="success">下载导入文档</el-button>
							</download-excel>
						</el-form-item>
					
					</el-col>
				</el-col>
			
		</el-form>
		<el-table :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" id="dataTable">

			<el-table-column type="selection" tooltip-effect="dark" width="50">
			</el-table-column>

			<el-table-column prop="type" label="类型" min-width="120">
			</el-table-column>

			<el-table-column prop="title" label="题目" min-width="200">
			</el-table-column>
			<el-table-column prop="question_order" label="排序" min-width="180">
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

			<!-- 操作 -->
			<el-table-column label="操作" width="150">
				<template slot-scope="scope">
					<router-link class="el-button el-button--small is-plain el-button--primary" v-if="$check_action('/evaluation/table','set')"
						:to="'/evaluation_question_view/view?' + field + '=' + scope.row[field]" size="small">
            详情
					</router-link>
				</template>
			</el-table-column>
			<!-- /操作 -->

		</el-table>

		<!-- 分页器 -->
		<div class="mt text_center">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="query.page"
			 :page-sizes="[7, 10, 30, 100]" :page-size="query.size" layout="total, sizes, prev, pager, next, jumper" :total="count">
			</el-pagination>
		</div>
		<!-- /分页器 -->

	</el-main>
</template>

<script>
	import mixin from "@/mixins/page.js";
	import * as XLSX from 'xlsx/xlsx.mjs'
	
	export default {
		mixins: [mixin],
		data() {
			return {
				// 获取数据地址
				url_get_list: "~/api/evaluation_question/get_list?like=0",
				url_del: "~/api/evaluation_question/del?",

				// 字段ID
				field:"exam_question_id",

				// 查询
				query: {
					size: 10,
					page: 1,
					exam_id: 0,
					type:"",
					title:"",
					orderby:'question_order'
				},

				// 数据
				list: [],
				json_fields: {
					"类型": 'type',
					"题目": 'title',
					"选项": 'question_item',
					"排序": 'question_order',
				},
				DetailsForm: [{
					type: "文本类型",
					title: "文本类型",
					question_item: "编辑类型",
					question_order: "数字类型",
				}, ],
			}
		},
		methods: {
			deleteRow(index, rows) {
				rows.splice(index, 1);
			},
			/**
			 * 重置
			 */
			reset() {
				let exam_id = {"exam_id":this.query.exam_id};
				let orderby = {"exam_id":this.query.orderby};
				$.clear(this.query);
				$.push(this.query, this.config);
				$.push(this.query, exam_id);
				$.push(this.query, orderby);
				this.get_list();
			},
			/**
			 * 导出
			 */
			export_db(tableid) {
				//导出表格
				var idTmr;
			
				function getExplorer() {
					var explorer = window.navigator.userAgent;
					//ie
					if (explorer.indexOf("MSIE") >= 0) {
						return 'ie';
					}
					//firefox
					else if (explorer.indexOf("Firefox") >= 0) {
						return 'Firefox';
					}
					//Chrome
					else if (explorer.indexOf("Chrome") >= 0) {
						return 'Chrome';
					}
					//Opera
					else if (explorer.indexOf("Opera") >= 0) {
						return 'Opera';
					}
					//Safari
					else if (explorer.indexOf("Safari") >= 0) {
						return 'Safari';
					}
				}
			
				function Cleanup() {
					window.clearInterval(idTmr);
					CollectGarbage();
				}
				var tableToExcel = (function() {
					var uri = 'data:application/vnd.ms-excel;base64,',
						template =
						'<html><head><meta charset="UTF-8"></head><body><table  border="1">{table}</table></body></html>',
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
						if (!table.nodeType)
							table = document.getElementById(table)
						var ctx = {
							worksheet: name || 'Worksheet',
							table: table.innerHTML
						}
						window.location.href = uri + base64(format(template, ctx))
					}
				})();
				if (getExplorer() == 'ie') {
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
					} catch (e) {
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
			readFile(file) { //文件读取
				return new Promise(resolve => {
					let reader = new FileReader();
					reader.readAsBinaryString(file); //以二进制的方式读取
					reader.onload = ev => {
						resolve(ev.target.result);
					}
				})
			},
			async handle_import(ev) {
				let file = ev.raw;
				console.log(file)
				if (!file) {
					console.log("文件打开失败")
					return;
				} else {
					let data = await this.readFile(file);
					let workbook = XLSX.read(data, {
						type: "binary"
					}); //解析二进制格式数据
					let worksheet = workbook.Sheets[workbook.SheetNames[0]]; //获取第一个Sheet
					let result = XLSX.utils.sheet_to_json(worksheet); //json数据格式
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
					for (let i = 0; i < result.length; i++) {
						let url = "~/api/evaluation_question/add?";
						result[i]['exam_id'] = this.query.exam_id;
						await this.$post(url, result[i], function(json, status) {
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
		},
		created(){
		}
	}
</script>

<style type="text/css">

</style>
