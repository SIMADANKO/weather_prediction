<template>
	<el-main class="bg edit_wrap">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce">
							<el-col v-if="$check_field('get','region_name') || $check_field('add','region_name') || $check_field('set','region_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="地区名称" prop="region_name">
												<el-input id="region_name" v-model="form['region_name']" placeholder="请输入地区名称"
							  v-if="(form['rainfall_data_id'] && $check_field('set','region_name')) || (!form['rainfall_data_id'] && $check_field('add','region_name'))" :disabled="disabledObj['region_name_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','region_name')">{{form['region_name']}}</div>
											</el-form-item>
			</el-col>
								<el-col v-if="$check_field('get','average_rainfall') || $check_field('add','average_rainfall') || $check_field('set','average_rainfall')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="雨量平均值" prop="average_rainfall">
								<el-input-number id="average_rainfall" v-model.number="form['average_rainfall']"
						v-if="(form['rainfall_data_id'] && $check_field('set','average_rainfall')) || (!form['rainfall_data_id'] && $check_field('add','average_rainfall'))" :disabled="disabledObj['average_rainfall_isDisabled']"></el-input-number>
					<div v-else-if="$check_field('get','average_rainfall')">{{form['average_rainfall']}}</div>
							</el-form-item>
			</el-col>
								<el-col v-if="$check_field('get','maximum_rainfall') || $check_field('add','maximum_rainfall') || $check_field('set','maximum_rainfall')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="雨量最大值" prop="maximum_rainfall">
								<el-input-number id="maximum_rainfall" v-model.number="form['maximum_rainfall']"
						v-if="(form['rainfall_data_id'] && $check_field('set','maximum_rainfall')) || (!form['rainfall_data_id'] && $check_field('add','maximum_rainfall'))" :disabled="disabledObj['maximum_rainfall_isDisabled']"></el-input-number>
					<div v-else-if="$check_field('get','maximum_rainfall')">{{form['maximum_rainfall']}}</div>
							</el-form-item>
			</el-col>
								<el-col v-if="$check_field('get','minimum_rainfall') || $check_field('add','minimum_rainfall') || $check_field('set','minimum_rainfall')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="雨量最小值" prop="minimum_rainfall">
								<el-input-number id="minimum_rainfall" v-model.number="form['minimum_rainfall']"
						v-if="(form['rainfall_data_id'] && $check_field('set','minimum_rainfall')) || (!form['rainfall_data_id'] && $check_field('add','minimum_rainfall'))" :disabled="disabledObj['minimum_rainfall_isDisabled']"></el-input-number>
					<div v-else-if="$check_field('get','minimum_rainfall')">{{form['minimum_rainfall']}}</div>
							</el-form-item>
			</el-col>
								<el-col v-if="$check_field('get','rainfall_standard_value') || $check_field('add','rainfall_standard_value') || $check_field('set','rainfall_standard_value')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="雨量标准值" prop="rainfall_standard_value">
								<el-input-number id="rainfall_standard_value" v-model.number="form['rainfall_standard_value']"
						v-if="(form['rainfall_data_id'] && $check_field('set','rainfall_standard_value')) || (!form['rainfall_data_id'] && $check_field('add','rainfall_standard_value'))" :disabled="disabledObj['rainfall_standard_value_isDisabled']"></el-input-number>
					<div v-else-if="$check_field('get','rainfall_standard_value')">{{form['rainfall_standard_value']}}</div>
							</el-form-item>
			</el-col>
								<el-col v-if="$check_field('get','rainfall_warning_value') || $check_field('add','rainfall_warning_value') || $check_field('set','rainfall_warning_value')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="雨量预警值" prop="rainfall_warning_value">
								<el-input-number id="rainfall_warning_value" v-model.number="form['rainfall_warning_value']"
						v-if="(form['rainfall_data_id'] && $check_field('set','rainfall_warning_value')) || (!form['rainfall_data_id'] && $check_field('add','rainfall_warning_value'))" :disabled="disabledObj['rainfall_warning_value_isDisabled']"></el-input-number>
					<div v-else-if="$check_field('get','rainfall_warning_value')">{{form['rainfall_warning_value']}}</div>
							</el-form-item>
			</el-col>
								<el-col v-if="$check_field('get','data_details') || $check_field('add','data_details') || $check_field('set','data_details')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
				<el-form-item label="数据详情" prop="data_details">
								<el-input type="textarea" id="data_details" v-model="form['data_details']" placeholder="请输入数据详情"
						v-if="(form['rainfall_data_id'] && $check_field('set','data_details')) || (!form['rainfall_data_id'] && $check_field('add','data_details'))" :disabled="disabledObj['data_details_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','data_details')">{{form['data_details']}}</div>
							</el-form-item>
			</el-col>
						
	
	
		
		
	
	
	
	
	</el-row>
			<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/rainfall_data/view','set') || $check_action('/rainfall_data/view','add') || $check_option('/rainfall_data/table','examine')">
					<el-button type="primary" @click="submit()">提交</el-button>
					<el-button @click="cancel()">取消</el-button>
				</el-form-item>
				<el-form-item v-else>
					<el-button @click="cancel()">返回</el-button>
				</el-form-item>
			</el-col>

		</el-form>
	</el-main>
</template>

<script>
	import mixin from "@/mixins/page.js";

	export default {
		mixins: [mixin],
		data() {
			return {
				field: "rainfall_data_id",
				url_add: "~/api/rainfall_data/add?",
				url_set: "~/api/rainfall_data/set?",
				url_get_obj: "~/api/rainfall_data/get_obj?",
				url_upload: "~/api/rainfall_data/upload?",

				query: {
					"rainfall_data_id": 0,
				},

				form: {
								"region_name":  '', // 地区名称
										"average_rainfall":  0, // 雨量平均值
										"maximum_rainfall":  0, // 雨量最大值
										"minimum_rainfall":  0, // 雨量最小值
										"rainfall_standard_value":  0, // 雨量标准值
										"rainfall_warning_value":  0, // 雨量预警值
										"data_details":  '', // 数据详情
											"rainfall_data_id": 0, // ID
													},
				disabledObj:{
								"region_name_isDisabled": false,
					          			"average_rainfall_isDisabled": false,
					          			"maximum_rainfall_isDisabled": false,
					          			"minimum_rainfall_isDisabled": false,
					          			"rainfall_standard_value_isDisabled": false,
					          			"rainfall_warning_value_isDisabled": false,
										"data_details_isDisabled": false,
										},

	
				
				
				
				
				
				
			
			}
		},
		methods: {

	
	
			
	
			
	
			
	
			
	
			
	
			
	
		
			/**
			 * 获取对象之前
			 * @param {Object} param
			 */
			get_obj_before(param) {
				var form = "";
								if(this.form && form){
					Object.keys(this.form).forEach(key => {
						Object.keys(form).forEach(dbKey => {
							// if(dbKey === "charging_standard"){
							// 	this.form['charging_rules'] = form[dbKey];
							// 	this.disabledObj['charging_rules_isDisabled'] = true;
							// };
							if(key === dbKey){
								this.disabledObj[key+'_isDisabled'] = true;
								this.form[key] = form[dbKey]
							}
							if(dbKey === "source_table"){
								this.form['source_table'] = form[dbKey];
							}
							if(dbKey === "source_id"){
								this.form['source_id'] = form[dbKey];
							}
							if(dbKey === "source_user_id"){
								this.form['source_user_id'] = form[dbKey];
							}
						})
					})
				}
																		$.db.del("form");
				return param;
			},

			/**
			 * 获取对象之后
			 * @param {Object} json
			 * @param {Object} func
			 */
			get_obj_after(json, func){

																																			

			},

																																																							async submit(param, func){
				if (!param) {
					param = this.form;
				}
								var pm = this.events("submit_before", Object.assign({}, param)) || param;
				var msg = await this.events("submit_check", pm);
				var ret;
				if (msg) {
					this.$toast(msg, 'danger');
				} else {
																																																									ret = this.events("submit_main", pm, func);
				}
				return ret;
			},
			/**
			 * 提交前验证事件
			 * @param {Object} 请求参数
			 * @return {String} 验证成功返回null, 失败返回错误提示
			 */
						submit_check(param) {
																																																																																							return null;
			},

			is_view(){
				// var bl = this.user_group == "管理员";
				var bl = false;

				if(!bl){
					bl = this.$check_action('/rainfall_data/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/rainfall_data/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/rainfall_data/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/rainfall_data/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/rainfall_data/view','get');
					console.log(bl ? "你有视图查询权限视作有查询权限" : "你没有视图查询权限");
				}

				console.log(bl ? "具有当前页面的查看权，请注意这不代表你有字段的查看权" : "无权查看当前页，请注意即便有字段查询权限没有页面查询权限也不行");

				return bl;
			},
			/**
			 * 上传文件
			 * @param {Object} param
			 */
			uploadimg(param) {
				this.uploadFile(param.file, "avatar");
			},

		},
		created() {
																},
	}
</script>

<style>
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}
	
	.img_multiple{
		overflow: hidden;
	}
	.img_multiple .img_block{
		float: left;
		margin-right: 5px;
		margin-bottom: 5px;
		position: relative;
	}
	.img_multiple .img_block img{
		height: 100px;
		width: auto;
	}
	.img_multiple .img_del{
		position: absolute;
		top: 5px;
		right: 5px;
		width: 20px;
		height: 20px;
		background: #0000008a;
		color: #fff;
		line-height: 20px;
		text-align: center;
		border-radius: 100%;
		cursor: pointer;
	}




</style>
