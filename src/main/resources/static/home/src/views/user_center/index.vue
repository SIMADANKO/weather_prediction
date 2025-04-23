<template>
	<div class="page_user my_home" id="user_address">
		<div class="warp">
			<div class="container">
				<div class="row justify-content-between">
					<div class="col-12 col-md-3">
						<div class="card_menu">
							<!-- 左侧边栏 -->
							<list_admin_menu_user></list_admin_menu_user>
						</div>
					</div>
					<div class="col-12 col-md-9">
						<div class="card_addres pl-2">
							<div class="warp">
								<div class="container-fluid">
									<el-row>
										<div>欢迎来到个人中心</div>
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
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import list_admin_menu_user from "@/components/diy/list_admin_menu_user.vue";
	import newBarChart from "@/components/charts/new_bar_chart";
	export default {
		data() {
			return {
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
				};
		},
		mounted() {
		},
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
		created() {
			setTimeout(() => {
						// 执行天气信息数据获取
			this.get_list_weather_information();
					// 执行降雨量数据数据获取
			this.get_list_rainfall_data();
				}, 1000);
		},
		components: {
			newBarChart,
			list_admin_menu_user
		},
	};
</script>

<style scoped>
	.container {
		min-height: 800px;
	}
</style>
