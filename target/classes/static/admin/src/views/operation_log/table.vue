<template>
	<el-main class="bg table_wrap sides">
		<el-form label-position="right" :model="query" class="form p_4" label-width="120">
			<el-row class="rows row1">
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="操作角色">
						<el-input v-model="query.user_group"></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="操作人">
						<el-input v-model="query.user_name"></el-input>
					</el-form-item>
				</el-col>
				<el-col :xs="24" :sm="24" :lg="8" class="el_form_search_wrap">
					<el-form-item label="操作模块">
						<el-input v-model="query.routes"></el-input>
					</el-form-item>
				</el-col>
			</el-row>
			<el-row class="rows row2">
				<el-col :xs="24" :sm="24" :lg="24" class="search_btn_wrap">
					<el-col :xs="24" :sm="12" :lg="12" class="search_btn_1">
							<el-button type="primary" @click="search()" class="search_btn_find">查询</el-button>
							<el-button @click="reset()" class="search_btn_reset">重置</el-button>
							<el-button v-if="user_group == '管理员'" class="float-right search_btn_del" type="danger" @click="delInfo()">删除</el-button>
					</el-col>
					
				</el-col>

			</el-row>
		</el-form>

		<el-table border :data="list" @selection-change="selectionChange" @sort-change="$sortChange" style="width: 100%" stripe>
			<!-- 多选按钮 -->
			<el-table-column fixed type="selection" tooltip-effect="dark" width="55">
			</el-table-column>
			<!-- /多选按钮 -->
			<el-table-column label="角色" prop="user_group" width="150">
			</el-table-column>

			<el-table-column label="账户" prop="user_name" width="200">
			</el-table-column>
			<el-table-column label="操作模块" prop="routes">
			</el-table-column>
			<el-table-column label="操作时间" prop="create_time" width="250">
				<template slot-scope="scope">
				  {{ $toTime(scope.row["create_time"], "yyyy-MM-dd hh:mm:ss") }}
				</template>
			</el-table-column>
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
				field: "operation_log_id",
				url_get_list: "~/api/operation_log/get_list?like=0",
				url_del: "~/api/operation_log/del?",

				query: {
					size: 10,
					page: 1,
					user_group: "",
					user_name: "",
					routes: ""
				},

				// 数据
				list: [],

				// 获取用户信息
				list_user: [],
			}
		},
		methods: {
			table_class({row, column, rowIndex, columnIndex}){
				return "table_class";
			}
		},
		created() {

		}
	}
</script>

<style>
	.float-right {
		float: right;
	}

	.mr-1 {
		margin-right: 1rem;
	}

	.el-table .table_class {
		background: rgba(150, 150, 150, 0.1);
		text-align: center;
	}
</style>
