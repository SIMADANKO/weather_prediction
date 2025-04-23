<template>
	<el-main class="bg table_wrap exam_score">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row class="rows row1">
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="提交人">
						<el-input v-model="query.nickname"></el-input>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="点评状态" prop="comment_state">
						<el-select v-model="query.comment_state">
							<el-option value="" label="">请选择</el-option>
							<el-option :value="0" label="未点评">未点评</el-option>
							<el-option :value="1" label="已点评">已点评</el-option>
						</el-select>
					</el-form-item>
				</el-col>
			</el-row>
			<el-row class="rows row2">
				<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap">
					<el-col :xs="24" :sm="12" :lg="12" class="search_btn_1 btns">
						
							<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
							<el-button @click="reset()" class="search_btn_reset">重置</el-button>
             				 <el-button class="float-right search_btn_del" type="danger" @click="delInfo()">删除</el-button>
					
					</el-col>
				</el-col>
			</el-row>
		</el-form>
		<el-table border :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" stripe>

			<el-table-column fixed type="selection" tooltip-effect="dark" width="50">
			</el-table-column>

			<el-table-column fixed prop="nickname" label="提交人" min-width="120">
			</el-table-column>

			<el-table-column prop="comment_desc" label="评语" min-width="180">
				<template slot-scope="scope">
					{{ scope.row["comment_desc"] }}
				</template>
			</el-table-column>
	
			<el-table-column prop="comment_state" label="点评状态" min-width="180">
				<template slot-scope="scope">
					{{ scope.row["comment_state"] ===1?"已点评":"未点评" }}
				</template>
			</el-table-column>

			<el-table-column sortable prop="create_time" label="提交时间" min-width="200">
			    <template slot-scope="scope">
			        {{ $toTime(scope.row["create_time"],"yyyy-MM-dd hh:mm:ss") }}
			    </template>
			</el-table-column>

			<el-table-column sortable prop="update_time" label="点评时间" min-width="200">
			    <template slot-scope="scope">
			        {{ scope.row["comment_state"] ===1?$toTime(scope.row["update_time"],"yyyy-MM-dd hh:mm:ss"):"/" }}
			    </template>
			</el-table-column>

			<!-- 操作 -->
			<el-table-column fixed="right" label="操作" width="200">
				<template slot-scope="scope">
					<div class="view_a">
					<router-link v-if="scope.row['comment_state']===1" class="e-button el-button--small is-plain el-button--primary"
						:to="'/evaluation_score_view/view?user_answer_id=' + scope.row['user_answer_id']" size="small">
						<span>查看</span>
					</router-link>
					<router-link v-else class="e-button el-button--small is-plain el-button--primary"
								 :to="'/evaluation_score_view/view?user_answer_id=' + scope.row['user_answer_id']" size="small">
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
				url_get_list: "~/api/evaluation_user_answer/get_list?like=0",
				url_del: "~/api/evaluation_user_answer/del?",

				// 字段ID
				field:"user_answer_id",

				// 查询
				query: {
					size: 10,
					page: 1,
					exam_id: 0,
					nickname:"",
					comment_state:""
				},

				// 数据
				list: []
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
				$.clear(this.query);
				$.push(this.query, this.config);
				$.push(this.query, exam_id);
				this.get_list();
			},
		},
		created(){
		}
	}
</script>

<style type="text/css">

</style>
