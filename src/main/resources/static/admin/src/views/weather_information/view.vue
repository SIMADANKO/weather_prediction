<template>
	<el-main class="bg edit_wrap comtable_e">
		<el-form ref="form" :model="form" status-icon label-width="120px" v-if="is_view()">
		<el-row class="row_ce"> 
							<el-col v-if="$check_field('get','region_name') || $check_field('add','region_name') || $check_field('set','region_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="地区名称" prop="region_name">
															<el-input id="region_name" v-model="form['region_name']" placeholder="请输入地区名称"
							  v-if="(form['weather_information_id'] && $check_field('set','region_name')) || (!form['weather_information_id'] && $check_field('add','region_name'))" :disabled="disabledObj['region_name_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','region_name')">{{form['region_name']}}</div>
											</el-form-item>
			</el-col>
								<el-col v-if="$check_field('get','release_time') || $check_field('add','release_time') || $check_field('set','release_time')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="发布时间" prop="release_time">
											<el-date-picker :disabled="disabledObj['release_time_isDisabled']" v-if="(form['weather_information_id'] && $check_field('set','release_time')) || (!form['weather_information_id'] && $check_field('add','release_time'))" id="release_time"
						v-model="form['release_time']" type="date" placeholder="选择日期" value-format="yyyy-MM-dd">
					</el-date-picker>
					<div v-else-if="$check_field('get','release_time')">{{form['release_time']}}</div>
							</el-form-item>
			</el-col>
								<el-col v-if="$check_field('get','weather_pictures') || $check_field('add','weather_pictures') || $check_field('set','weather_pictures')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="天气图片" prop="weather_pictures">
											<el-upload :disabled="disabledObj['weather_pictures_isDisabled']" class="avatar-uploader" drag
						accept="image/gif, image/jpeg, image/png, image/jpg" action="" :http-request="upload_weather_pictures"
						:show-file-list="false" v-if="(form['weather_information_id'] && $check_field('set','weather_pictures')) || (!form['weather_information_id'] && $check_field('add','weather_pictures'))">
						<img id="weather_pictures" v-if="form['weather_pictures']" :src="$fullUrl(form['weather_pictures'])" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<el-image v-else-if="$check_field('get','weather_pictures')" style="width: 100px; height: 100px"
						:src="$fullUrl(form['weather_pictures'])" :preview-src-list="[$fullUrl(form['weather_pictures'])]">
						<div slot="error" class="image-slot">
							<img src="../../../public/img/error.png" style="width: 90px; height: 90px" />
						</div>
					</el-image>
							</el-form-item>
			</el-col>
								<el-col v-if="$check_field('get','weather_type') || $check_field('add','weather_type') || $check_field('set','weather_type')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="天气类型" prop="weather_type">
											<el-select id="weather_type" v-model="form['weather_type']"						v-if="(form['weather_information_id'] && $check_field('set','weather_type')) || (!form['weather_information_id'] && $check_field('add','weather_type'))">
						<el-option v-for="o in list_weather_type" :key="o['weather_type']" :label="o['weather_type']"
							:value="o['weather_type']">
						</el-option>
					</el-select>
					<div v-else-if="$check_field('get','weather_type')">{{form['weather_type']}}</div>
							</el-form-item>
			</el-col>
								<el-col v-if="$check_field('get','rainfall') || $check_field('add','rainfall') || $check_field('set','rainfall')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="降雨量" prop="rainfall">
											<el-input-number id="rainfall" v-model.number="form['rainfall']"
						v-if="(form['weather_information_id'] && $check_field('set','rainfall')) || (!form['weather_information_id'] && $check_field('add','rainfall'))" :disabled="disabledObj['rainfall_isDisabled']"></el-input-number>
					<div v-else-if="$check_field('get','rainfall')">{{form['rainfall']}}</div>
							</el-form-item>
			</el-col>
								<el-col v-if="$check_field('get','weather_details') || $check_field('add','weather_details') || $check_field('set','weather_details')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
							<el-form-item label="天气详情" prop="weather_details">
											<el-input type="textarea" id="weather_details" v-model="form['weather_details']" placeholder="请输入天气详情"
						v-if="(form['weather_information_id'] && $check_field('set','weather_details')) || (!form['weather_information_id'] && $check_field('add','weather_details'))" :disabled="disabledObj['weather_details_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','weather_details')">{{form['weather_details']}}</div>
							</el-form-item>
			</el-col>
						
	
	
		
				<el-col :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
			  <el-form-item label="详细地址">
			    <el-input id="mark_address" v-model="form['mark_address']" placeholder="请输入详细地址" clearable v-if="(form['weather_information_id}'] && $check_action('/weather_information/view', 'set')) || (!form['weather_information_id}'] && $check_action('/weather_information/view', 'add'))"></el-input>
			    <div v-else-if="$check_action('/weather_information/view', 'get')" v-html="form['mark_address']"></div>
			  </el-form-item>
			</el-col>
		
	
	
	
		
		
	
		</el-row>
			<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/weather_information/view','set') || $check_action('/weather_information/view','add') || $check_option('/weather_information/table','examine')">
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
	import { baiduMap } from "@/utils/baidu_map.js";
	export default {
		mixins: [mixin],
		data() {
			return {
				field: "weather_information_id",
				url_add: "~/api/weather_information/add?",
				url_set: "~/api/weather_information/set?",
				url_get_obj: "~/api/weather_information/get_obj?",
				url_upload: "~/api/weather_information/upload?",

				query: {
					"weather_information_id": 0,
				},

				form: {
								"region_name":  '', // 地区名称
										"release_time":  '', // 发布时间
										"weather_pictures":  '', // 天气图片
										"weather_type":  '', // 天气类型
										"rainfall":  0, // 降雨量
										"weather_details":  '', // 天气详情
											"weather_information_id": 0, // ID
														"mark_address": "", // 详细地址
							},
				disabledObj:{
								"region_name_isDisabled": false,
										"release_time_isDisabled": false,
										"weather_pictures_isDisabled": false,
										"weather_type_isDisabled": false,
					          			"rainfall_isDisabled": false,
										"weather_details_isDisabled": false,
										},

	
			
			
									// 天气类型选项列表
				list_weather_type: [""],
	
			
			
		
			}
		},
		mounted() {
		  baiduMap().then((BMap) => {
		    this.initMap(BMap);
		  });
		},
		methods: {
			initMap(BMap) {
			  // 创建地图实例
			  var ac = new BMap.Autocomplete({
			    input: "mark_address",
			    location: "中国",
			  });
			  // 监听选中地址事件
			  ac.addEventListener("onconfirm", (e) => {
			    const _value = e.item.value;
			    const value =
			      _value.province +
			      _value.city +
			      _value.district +
			      _value.street +
			      _value.business;
			    this.form.mark_address = value;
			  });
			},
			// 创建地址转经纬度
			getCoordinates(address) {
			  return new Promise((resolve, reject) => {
			    var myGeo = new BMap.Geocoder();
			    myGeo.getPoint(
			      address,
			      function (point) {
			        if (point) {
			          resolve(point);
			        } else {
			          reject("无法获取坐标，请检查地址是否正确");
			        }
			      },
			      "中国"
			    );
			  });
			},

	
	
			
	
						/**
			 * 上传天气图片
			 * @param {Object} param 图片参数
			 */
			upload_weather_pictures(param){
									this.uploadFile(param.file, "weather_pictures");
								},
	
	
			
				/**
			 * 获取天气类型列表
			 */
			
						async get_list_weather_type() {
				var json = await this.$get("~/api/weather_type/get_list?");
				if(json.result && json.result.list){
					this.list_weather_type = json.result.list;
				}
				else if(json.error){
					console.error(json.error);
				}
			},
								
			
	
			
	
		
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
		        if (this.form["release_time"] && JSON.stringify(this.form["release_time"]).indexOf("-")===-1){
          this.form["release_time"] = this.$toTime(parseInt(this.form["release_time"]),"yyyy-MM-dd")
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
											if(this.form["release_time"]=="0000-00-00"){
				  this.form["release_time"] = null;
				}
				if(parseInt(this.form["release_time"]) > 9999){
					this.form["release_time"] = this.$toTime(parseInt(this.form["release_time"]),"yyyy-MM-dd")
				}
																								

			},

																																																						async submit(param, func){
				if (!param) {
					param = this.form;
				}
								if (param["mark_address"]) {
				  const point = await this.getCoordinates(param["mark_address"]);
				  param["mark_lng"] = Number(point.lng.toFixed(8));
				  param["mark_lat"] = Number(point.lat.toFixed(8));
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
					
																		if (!param.release_time){
					return "发布时间不能为空";
				}
																																																												return null;
			},

			is_view(){
				// var bl = this.user_group == "管理员";
				var bl = false;

				if(!bl){
					bl = this.$check_action('/weather_information/table','add');
					console.log(bl ? "你有表格添加权限视作有添加权限" : "你没有表格添加权限");
				}
				if(!bl){
					bl = this.$check_action('/weather_information/table','set');
					console.log(bl ? "你有表格添加权限视作有修改权限" : "你没有表格修改权限");
				}
				if(!bl){
					bl = this.$check_action('/weather_information/view','add');
					console.log(bl ? "你有视图添加权限视作有添加权限" : "你没有视图添加权限");
				}
				if(!bl){
					bl = this.$check_action('/weather_information/view','set');
					console.log(bl ? "你有视图修改权限视作有修改权限" : "你没有视图修改权限");
				}
				if(!bl){
					bl = this.$check_action('/weather_information/view','get');
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
										this.get_list_weather_type();
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
