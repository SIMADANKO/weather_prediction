<template>
	<div class="diy_list page_weather_information" id="weather_information_list">
		<div class="warp">
			<div class="container diy_list_container">
				<div class="diy_list_title">
					<div class="col">
						<span class="title">天气信息列表</span>
					</div>
				</div>
				<div class="leis_box"> 
				<div class="iudis_box">
			
				<div class="row diy_list_search">
					<div class="col">
						<!-- 搜索栏 -->
						<div class="view Search">
							<span class="diy_list_search_title">关键字搜索：</span>
																															<b-form-input size="sm" class="mr-sm-2" placeholder="地区名称搜索" v-model="query['region_name']" />
																																														<el-date-picker v-model="query['release_time']" type="daterange" range-separator="至" start-placeholder="发布时间开始" end-placeholder="发布时间结束"></el-date-picker>
																																																											<b-form-input size="sm" class="mr-sm-2" placeholder="天气类型搜索" v-model="query['weather_type']" />
																																																												<b-button size="sm" @click="search()" >
								<b-icon icon="search"/>
							</b-button>
						</div>
						<!-- /搜索栏 -->
					</div>
				</div>
				<div class="diy_list_select_box">
					<span class="diy_list_select_title">下拉搜索：</span>
						<div class="diy_list_dropdown_box">
						<div class="col">
							<!-- 筛选 -->
							<div class="view sift">
																				<b-dropdown text="天气类型" variant="outline-dark" left>
									<b-dropdown-item @click="filter_set('全部','weather_type')">全部</b-dropdown-item>
										<b-dropdown-item v-for="(o, i) in list_weather_type" :key="i" @click="filter_set(o['weather_type'],'weather_type')" >
												{{ o['weather_type'] }}
										</b-dropdown-item>
								</b-dropdown>
													<!-- 排序 -->
							
								<b-dropdown text="排序" variant="outline-dark" left>
										<b-dropdown-item v-for="(o, i) in list_sort" :key="i" @click="set_sort(o)" >
												{{ o.name }}
										</b-dropdown-item>
								</b-dropdown>
						
							<!--/排序 -->
							</div>
							<!-- /筛选 -->
						</div>
					</div>
				
				</div>
								<div class="map_btn">
					<el-button class="search_btn_map" @click="openMap()">查看地图</el-button>
				</div>
							</div>
				<div class="row diy_list_box">
					<div class="col">
						<!-- 列表 -->
												<list_weather_information :list="list" />
												<!-- /列表 -->
					</div>
				</div>
	</div>
	<!-- 大盒子结尾 -->
				<div class="row diy_list_page_box">
					<div class="col overflow-auto flex_cc">
						<!-- 分页器 -->
<!--						<diy_pager v-model="query['page']" :size="query['size']" :count="count" v-on:toPage="toPage" v-on:toSize="toSize" ></diy_pager>-->
            <b-pagination
                v-model="query.page"
                :total-rows="count"
                :per-page="query.size"
                @change="goToPage"
            />
						<!-- /分页器 -->
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import list_weather_information from "@/components/diy/list_weather_information.vue";
	import diy_pager from "@/components/diy/diy_pager";
	import mixin from "@/mixins/page.js";

	export default {
		mixins: [mixin],
		components: {
			diy_pager,
			list_weather_information,
		},
		data() {
			return {
				url_get_list: "~/api/weather_information/get_list?like=0",

				// 查询条件
				query: {
					keyword: "",
					page: 1,
					size: 12,
								"region_name": "", // 地区名称
											"release_time": "", // 发布时间
												"weather_type": "", // 天气类型
									},
				// 排序内容
				list_sort: [{
						name: "创建时间从高到低",
						value: "create_time desc",
					},
					{
						name: "创建时间从低到高",
						value: "create_time asc",
					},
					{
						name: "更新时间从高到低",
						value: "update_time desc",
					},
					{
						name: "更新时间从低到高",
						value: "update_time asc",
					},
										],

											// 天气类型列表
				"list_weather_type": [""],
						
			}
		},
		methods: {
      get_list_before(param) {
      },
			/**
			 * 筛选选择
			 */
			filter_set(o,key) {
			    if (o == "全部") {
			        this.query[key] = "";
			    } else {
			        this.query[key] = o;
			    }
			    this.search();
			},

			/**
			 * 排序
			 */
			set_sort(o) {
			    this.query.orderby = o.value;
			    this.search();
			},
							/**
			 * 获取天气类型列表
			 */
			async get_list_weather_type() {
				var json = await this.$get("~/api/weather_type/get_list?");
				if (json.result) {
					this.list_weather_type = json.result.list;
				} else if (json.error) {
					console.log(json.error);
				}
			},
						/**
			 * 筛选
			 */
																			filter_weather_type(o) {
				if (o == "全部") {
					this.query["weather_type"] = "";
				} else {
					this.query["weather_type"] = o;
				}
				this.search();
			},
										/**
			 * 重置
			 */
			reset() {
							this.query.region_name = ""
										this.query.release_time = ""
											this.query.weather_type = ""
									this.search();
			},

			// 返回条数
			toSize(i){
				this.query.size = i;
				this.first();
			},

			// 返回页数
			toPage(i){
				this.query.page = i;
				this.first();
			},

      goToPage(v){
        this.query.page = v;
        this.goToNew(v)
      },

			openMap(){
				this.$router.push("/weather_information/map")
			},
		},
		computed: {
		},
		created() {
										/**
			 * 获取天气类型列表
			 */
			this.get_list_weather_type();
								}
	}
</script>

<style>
.el-date-editor--datetimerange.el-input, .el-date-editor--datetimerange.el-input__inner,.el-date-editor--daterange.el-input, .el-date-editor--daterange.el-input__inner, .el-date-editor--timerange.el-input, .el-date-editor--timerange.el-input__inner{
	width: 100%;
}
.el-date-editor .el-range-separator{
	width: 11% !important;
}
</style>
