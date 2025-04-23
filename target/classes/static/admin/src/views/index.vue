<template>
	<div class="page_root" id="root_index">
		<div class="warp">
			<div class="container-fluid">
				<el-row>
<!--					<el-col :span="4">-->
<!--						<div_label bg_color="bg_red" icon="el-icon-message" :url="url_message_count" unit="条"-->
<!--								  title="留言信息"></div_label>-->
<!--					</el-col>-->
					<el-col :span="4">
						<div_label bg_color="bg_purple" icon="el-icon-user-solid" :url="url_user_count" unit="人"
								  title="用户数量"></div_label>
					</el-col>
<!--					<el-col :span="4">-->
<!--						<div_label bg_color="bg_green" icon="el-icon-view" :url="url_article_hits" unit="次"-->
<!--								  title="文章浏览量"></div_label>-->
<!--					</el-col>-->
				</el-row>

				<el-row>
								<el-col v-if="$check_figure('/weather_information/table')" :span="8">
						<div class="card chart">
									<newBarChart v-if="bar_obj_weather_information.values.length > 0" id="bar_obj_weather_information" :vm="bar_obj_weather_information" :title="'天气信息统计'">
							</newBarChart>
							<div v-if="!bar_obj_weather_information.values.length">天气信息没有符合条件的数据</div>
								</div>
					</el-col>
							<el-col v-if="$check_figure('/rainfall_data/table')" :span="8">
						<div class="card chart">
									<newBarChart v-if="bar_obj_rainfall_data.values.length > 0" id="bar_obj_rainfall_data" :vm="bar_obj_rainfall_data" :title="'降雨量数据统计'">
							</newBarChart>
							<div v-if="!bar_obj_rainfall_data.values.length">降雨量数据没有符合条件的数据</div>
								</div>
					</el-col>
					</el-row>

				<div id ="outerdiv">
					<iframe src="http://www.nmc.cn/publish/forecast.html" id ='inneriframe' scrolling="no"></iframe>
				</div>
			</div>
		</div>
	</div>
</template>
<script>
	import mixin from "@/mixins/page.js";
	import newBarChart from "@/components/charts/new_bar_chart";
	import div_label from "@/components/div_label.vue";
	export default {
		mixins: [mixin],
		name: "Home",
		components: {
			newBarChart,
			div_label
		},
		data() {
			return {
				activeName: "third",
									bar_obj_weather_information: {
					names:[],
					xAxis: [],
					values:[]
				},
						bar_obj_rainfall_data: {
					names:[],
					xAxis: [],
					values:[]
				},
					url_message_count: "~/api/message/count?",
				url_user_count: "~/api/user/count?",
				url_article_hits: "~/api/article/sum?field=hits",
			};
		},
		created() {
						// 执行天气信息数据获取
			this.get_list_weather_information();
					// 执行降雨量数据数据获取
			this.get_list_rainfall_data();
			},
		mounted() {},
		methods: {
			async get_nickname(list,flag){
				if (flag) {
					for (let i=0;i<list.length;i++){
						await this.$get(
								"~/api/user/get_obj?user_id="+list[i],
								null,
								(json) => {
									if (json.result) {
										list[i] = json.result.obj.nickname;
									}
								});
					}
				}else {
					for (let i=0;i<list.length;i++){
						await this.$get(
								"~/api/user/get_obj?user_id="+list[i].name,
								null,
								(json) => {
									if (json.result) {
										list[i].name = json.result.obj.nickname;
									}
								});
					}
				}
			},
								// 获取天气信息统计图
			async get_list_weather_information() {
				let name_list = [];
				let query_str = "";
																	let group_by_value = "release_time";
								let flag = false;
												let date_flag = "日期"
																																						name_list.push("降雨量");
				query_str = query_str+"rainfall"+","
																	this.bar_obj_weather_information.names = name_list
				query_str = query_str.substr(0,query_str.length-1);
				let data = {};
						await this.$get(
						"~/api/weather_information/bar_group?field="+query_str+"&groupby="+group_by_value,
						data,
						(json) => {
							if (json.result) {
								let xAxis = [];
								let values = [];
								json.result.list.map((o) => {
									if (date_flag === "日期") {
										xAxis.push(this.$toTime(o[0] ,"yyyy-MM-dd"));
									}else if (date_flag === "时间") {
										xAxis.push(this.$toTime(o[0] ,"hh:mm:ss"));
									}else if (date_flag === "日长") {
										xAxis.push(this.$toTime(o[0] ,"yyyy-MM-dd hh:mm:ss"));
									}else {
										xAxis.push(o[0]);
									}
									values.push(o.splice(1))
								});
								this.bar_obj_weather_information.xAxis = xAxis;
								this.bar_obj_weather_information.values = values;
							}
							if (flag){
								this.get_nickname(this.bar_obj_weather_information.xAxis,true);
							}
						});
			},
					// 获取降雨量数据统计图
			async get_list_rainfall_data() {
				let name_list = [];
				let query_str = "";
									let group_by_value = "region_name";
								let flag = false;
												let date_flag = "其他"
																						name_list.push("雨量平均值");
				query_str = query_str+"average_rainfall"+","
															name_list.push("雨量最大值");
				query_str = query_str+"maximum_rainfall"+","
															name_list.push("雨量最小值");
				query_str = query_str+"minimum_rainfall"+","
															name_list.push("雨量标准值");
				query_str = query_str+"rainfall_standard_value"+","
															name_list.push("雨量预警值");
				query_str = query_str+"rainfall_warning_value"+","
																	this.bar_obj_rainfall_data.names = name_list
				query_str = query_str.substr(0,query_str.length-1);
				let data = {};
						await this.$get(
						"~/api/rainfall_data/bar_group?field="+query_str+"&groupby="+group_by_value,
						data,
						(json) => {
							if (json.result) {
								let xAxis = [];
								let values = [];
								json.result.list.map((o) => {
									if (date_flag === "日期") {
										xAxis.push(this.$toTime(o[0] ,"yyyy-MM-dd"));
									}else if (date_flag === "时间") {
										xAxis.push(this.$toTime(o[0] ,"hh:mm:ss"));
									}else if (date_flag === "日长") {
										xAxis.push(this.$toTime(o[0] ,"yyyy-MM-dd hh:mm:ss"));
									}else {
										xAxis.push(o[0]);
									}
									values.push(o.splice(1))
								});
								this.bar_obj_rainfall_data.xAxis = xAxis;
								this.bar_obj_rainfall_data.values = values;
							}
							if (flag){
								this.get_nickname(this.bar_obj_rainfall_data.xAxis,true);
							}
						});
			},
	
		},
		computed:{
			recognitionHeight(){
				return "830px"
			},
			recognitionUrl(){
				return "https://www.faceplusplus.com.cn/${model.filter.recognitionType}/"
			}
		}
	};
</script>

<style scoped="scoped">
	.chart {
		display: block;
		width: 100%;
		height: 400px;
		padding: 1rem;
		position: relative;
	}

	.el-col {
		padding: 0.5rem;
	}

	.card {
		overflow: hidden;
	}

	.iframe_box ,.iframe_box_change{
		width: 100%;
		height: 1180px;
		position: relative;
		margin-top: 25px;
	}
	.iframe_box_change{
		height: 580px;
		padding-top: 50px;
	}
	.iframe_box	.iframe_box_content, .iframe_box_change .iframe_box_content{
		width: 100%;
		height: 100%;
	}
	.iframe_box_top{
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100px;
		font-size: 25px;
		line-height: 100px;
		background: #fff;
		z-index: 99999999;
		padding-left: 50px;
	}
	#iframe_box_face div::before {
		content: '';
		width: 100px;
		position: absolute;
		top: 154px;
		right: 129px;
		z-index: 999;
		height: 20px;
		background-color: #FFFFFF;
	}

	#iframe_box_face>h1 {
		margin-top: 100px;
		margin-bottom: 20px;
	}
	#outerdiv
	{
		margin: 0 auto;
		width:1480px;
		height:2180px;
		overflow:hidden;
		position:relative;
	}

	#inneriframe
	{
		position:absolute;
		top:-150px;
		left:0px;
		width:1580px;
		height:2180px;
	}
</style>
