<template>
	<el-main class="bg table_wrap exam">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row class="rows row1">
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="名称">
						<el-input v-model="query.name"></el-input>
					</el-form-item>
				</el-col>
			</el-row>
			<el-row class="rows row2">
				<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap">
					<el-col :xs="24" :sm="12" :lg="12" class="search_btn_1 btns">
						
							<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
							<el-button @click="reset()" class="search_btn_reset">重置</el-button>
							<el-button v-if="$check_action('/evaluation/table','del')" class="float-right search_btn_del" type="danger" @click="delInfo()">删除</el-button>
							<!-- <router-link v-if="user_group == '管理员' || $check_action('/evaluation/table','add')" class="el-button float-right el-button--default el-button--primary search_btn_add"
								to="./view?"><span>添加</span>
							</router-link> -->
							<el-button  v-if="$check_action('/evaluation/table','add')" class="float-right  search_btn_add" @click="$router.push('./view?')">添加</el-button>
					
					</el-col>
					
				</el-col>
			</el-row>
		</el-form>
		<el-table border :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" stripe>

			<el-table-column fixed type="selection" tooltip-effect="dark" width="50">
			</el-table-column>

			<el-table-column fixed prop="name" label="名称" min-width="180">
			</el-table-column>

			<el-table-column prop="duration" label="答题时长" min-width="120">
			</el-table-column>

			<el-table-column prop="status" label="状态" min-width="180">
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
			<el-table-column fixed="right" label="操作" width="200">
				<template slot-scope="scope">
					<div class="view_a">
					<router-link class="e-button el-button--small is-plain el-button--primary" v-if="$check_action('/evaluation/table','set')"
						:to="'./view?' + field + '=' + scope.row[field]" size="small">
            			<span>详情</span>
					</router-link>
					<router-link class="e-button el-button--small is-plain el-button--primary" v-if="$check_exam('/evaluation/table','setting')"
								 :to="'/evaluation_question_table/table?' + field + '=' + scope.row[field]" size="small">
						<span>题库</span>
					</router-link>
					<!--&& !!-->
					<router-link class="e-button el-button--small is-plain el-button--primary"  :data-num='answered_list[scope.row[field]]' style="margin-left: 0;margin-top: 10px" v-if="($check_exam('/evaluation/table','answers')) && answered_list[scope.row[field]]"
								 :to="'/evaluation_answer_view/view?' + field + '=' + scope.row[field]" size="small">
						<span>查看</span>
					</router-link>
					<router-link class="e-button el-button--small is-plain el-button--primary" style="margin-left: 0;margin-top: 10px" v-else-if="($check_exam('/evaluation/table','answers')) && scope.row.status==='启用'"
								 :to="'/evaluation_answer_view/view?' + field + '=' + scope.row[field]" size="small">
						<span>答题</span>
					</router-link>
					<router-link class="e-button el-button--small is-plain el-button--primary" style="margin-top: 10px" v-if="$check_exam('/evaluation/table','give_score')"
								 :to="'/evaluation_score_table/table?' + field + '=' + scope.row[field]" size="small">
						<span>点评</span>
					</router-link>
					</div>
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

	export default {
		mixins: [mixin],
		data() {
			return {
				// 获取数据地址
				url_get_list: "~/api/evaluation/get_list?like=0",
				url_del: "~/api/evaluation/del?",

				// 字段ID
				field:"exam_id",

				// 查询
				query: {
					size: 10,
					page: 1,
					name: "",
				},

				// 数据
				list: [],

				list_is: [{name:"否" ,value:0},{name:"是" ,value:1}],
				answered_list:[]

			}
		},
		computed:{

		},
		methods: {
			deleteRow(index, rows) {
				rows.splice(index, 1);
			},
			get_list_after(){
				for (let i=0;i<this.list.length;i++){
					this.get_user_answer_state(this.list[i].exam_id);
				}
			},
			get_user_answer_state(exam_id) {
				let _this = this
				this.$get("~/api/evaluation_user_answer/get_obj?user_id="+this.$store.state.user.user_id+"&exam_id="+exam_id).then((res)=>{
					console.log(res)
					if(res.result && res.result.obj != null){
						_this.$set(_this.answered_list,exam_id,true)
					}else {
						_this.$set(_this.answered_list,exam_id,false)
					}
				})
			},
			table_class({row, column, rowIndex, columnIndex}){
				return "table_class";
			}
		},
		created(){
		}
	}
</script>

<style type="text/css">

</style>
