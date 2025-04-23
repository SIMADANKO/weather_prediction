<template>
  <div class="page_search search_index">
	<div class="warp">
	  <div class="container">
		<div class="row">
		  <div class="col-12">
			<div class="card_result_search">
			  <div class="title">搜索结果</div>

				<!-- 文章搜索结果 -->
			  <list_result_search
				:list="result_article"
				title="天气资讯"
				source_table="article"
			  ></list_result_search>


						  <list_result_search
				v-if="$check_action('/ordinary_users/list', 'get')"
				:list="result_ordinary_users_user_name"
				title="普通用户用户姓名"
				source_table="ordinary_users"
			  ></list_result_search>
												  <list_result_search
				v-if="$check_action('/weather_type/list', 'get')"
				:list="result_weather_type_weather_type"
				title="天气类型天气类型"
				source_table="weather_type"
			  ></list_result_search>
									  <list_result_search
				v-if="$check_action('/weather_information/list', 'get')"
				:list="result_weather_information_region_name"
				title="天气信息地区名称"
				source_table="weather_information"
			  ></list_result_search>
								  <list_result_search
				v-if="$check_action('/weather_information/list', 'get')"
				:list="result_weather_information_release_time"
				title="天气信息发布时间"
				source_table="weather_information"
			  ></list_result_search>
											  <list_result_search
				v-if="$check_action('/weather_information/list', 'get')"
				:list="result_weather_information_weather_type"
				title="天气信息天气类型"
				source_table="weather_information"
			  ></list_result_search>
															  <list_result_search
				v-if="$check_action('/rainfall_data/list', 'get')"
				:list="result_rainfall_data_region_name"
				title="降雨量数据地区名称"
				source_table="rainfall_data"
			  ></list_result_search>
																								</div>
		  </div>
		</div>
	  </div>
	</div>
  </div>
</template>

<script>
import mixin from "../../mixins/page.js";
import list_result_search from "../../components/diy/list_result_search.vue";

export default {
  mixins: [mixin],
  data() {
	return {
	  "query": {
		word: "",
	  },
	  "result_article": [],
						"result_ordinary_users_user_name":[],
												"result_weather_type_weather_type":[],
									"result_weather_information_region_name":[],
								"result_weather_information_release_time":[],
											"result_weather_information_weather_type":[],
															"result_rainfall_data_region_name":[],
																						};
  },
  methods: {
	/**
	 * 获取文章
	 */
	get_article() {
	  this.$get("~/api/article/get_list?like=0", { page: 1, size: 10, title: this.query.word }, (json) => {
		if (json.result) {
		  this.result_article = json.result.list;
		}
	  });
	},

				/**
	 * 获取user_name
	 */
	get_ordinary_users_user_name(){
		let url = "~/api/ordinary_users/get_list?like=0";
				this.$get(url, { page: 1, size: 10, "user_name": this.query.word }, (json) => {
		  if (json.result) {
			var result_ordinary_users_user_name = json.result.list;
			result_ordinary_users_user_name.map(o => o.title = o['user_name'])
	  			this.result_ordinary_users_user_name = result_ordinary_users_user_name
		 	}
		});
	},
										/**
	 * 获取weather_type
	 */
	get_weather_type_weather_type(){
		let url = "~/api/weather_type/get_list?like=0";
				this.$get(url, { page: 1, size: 10, "weather_type": this.query.word }, (json) => {
		  if (json.result) {
			var result_weather_type_weather_type = json.result.list;
			result_weather_type_weather_type.map(o => o.title = o['weather_type'])
	  			this.result_weather_type_weather_type = result_weather_type_weather_type
		 	}
		});
	},
							/**
	 * 获取region_name
	 */
	get_weather_information_region_name(){
		let url = "~/api/weather_information/get_list?like=0";
				this.$get(url, { page: 1, size: 10, "region_name": this.query.word }, (json) => {
		  if (json.result) {
			var result_weather_information_region_name = json.result.list;
			result_weather_information_region_name.map(o => o.title = o['region_name'])
	  			this.result_weather_information_region_name = result_weather_information_region_name
		 	}
		});
	},
						/**
	 * 获取release_time
	 */
	get_weather_information_release_time(){
		let url = "~/api/weather_information/get_list?like=0";
				this.$get(url, { page: 1, size: 10, "release_time": this.query.word }, (json) => {
		  if (json.result) {
			var result_weather_information_release_time = json.result.list;
			result_weather_information_release_time.map(o => o.title = o['release_time'])
	  			this.result_weather_information_release_time = result_weather_information_release_time
		 	}
		});
	},
									/**
	 * 获取weather_type
	 */
	get_weather_information_weather_type(){
		let url = "~/api/weather_information/get_list?like=0";
				this.$get(url, { page: 1, size: 10, "weather_type": this.query.word }, (json) => {
		  if (json.result) {
			var result_weather_information_weather_type = json.result.list;
			result_weather_information_weather_type.map(o => o.title = o['weather_type'])
	  			this.result_weather_information_weather_type = result_weather_information_weather_type
		 	}
		});
	},
													/**
	 * 获取region_name
	 */
	get_rainfall_data_region_name(){
		let url = "~/api/rainfall_data/get_list?like=0";
				this.$get(url, { page: 1, size: 10, "region_name": this.query.word }, (json) => {
		  if (json.result) {
			var result_rainfall_data_region_name = json.result.list;
			result_rainfall_data_region_name.map(o => o.title = o['region_name'])
	  			this.result_rainfall_data_region_name = result_rainfall_data_region_name
		 	}
		});
	},
																					
  },
  components: { list_result_search },
	created(){
    this.query.word = this.$route.query.word || "";
  },
  mounted() {
	this.get_article();
					this.get_ordinary_users_user_name();
											this.get_weather_type_weather_type();
								this.get_weather_information_region_name();
							this.get_weather_information_release_time();
										this.get_weather_information_weather_type();
														this.get_rainfall_data_region_name();
																					  },
  watch: {
	$route() {
	  $.push(this.query, this.$route.query);
	  this.get_article();
				  this.get_ordinary_users_user_name();
										  this.get_weather_type_weather_type();
							  this.get_weather_information_region_name();
						  this.get_weather_information_release_time();
									  this.get_weather_information_weather_type();
													  this.get_rainfall_data_region_name();
																						},
  },
};
</script>

<style scoped>
.card_search {
  text-align: center;
}
.card_result_search>.title {
  text-align: center;
  padding: 10px 0;
}
</style>
