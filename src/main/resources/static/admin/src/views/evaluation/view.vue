<template>
	<el-main class="bg edit_wrap exam">
		<el-form ref="form" :model="form" status-icon label-width="70px">
			<el-row class="row_e">

				<el-col :xs="24" :sm="24" :lg="24" class="el_form_item_warp">
					<el-form-item label="名称" prop="name">
						<el-input v-model="form.name"></el-input>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="24" class="el_form_item_warp">
					<el-form-item label="答题时长" prop="duration">
						<el-input-number v-model="form.duration"></el-input-number>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="24" class="el_form_item_warp">
					<el-form-item label="状态" prop="status">
						<el-select v-model="form.status" placeholder="请选择">
							<el-option label="启用" value="启用">
								启用
							</el-option>
							<el-option label="禁用" value="禁用">
								禁用
							</el-option>
						</el-select>
					</el-form-item>
				</el-col>

				
			</el-row>
			<el-col :xs="24" :sm="24" :lg="24" class="el_form_btn_warp">
					<el-form-item>
						<el-col :xs="24" :sm="24" :lg="12" class="el_form_btn el_form_btn_1">
							<el-button style="width: 100%; float: left;" type="primary" @click="submit()">提交
							</el-button>
						</el-col>
						<el-col :xs="24" :sm="24" :lg="12" class="el_form_btn el_form_btn_2">
							<el-button style="width: 100%; float: right;" @click="cancel()">取消</el-button>
						</el-col>
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
				field: "exam_id",
				url_add: "~/api/evaluation/add?",
				url_set: "~/api/evaluation/set?",
				url_get_obj: "~/api/evaluation/get_obj?",
				url_upload: "~/api/evaluation/upload?",

				query: {
					exam_id: 0,
				},

				form: {
					exam_id: 0,
					name: "",
					duration: 60,
					status: "启用"
				},

			}
		},
		methods: {
		},
		created() {}
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

	.el-row {
		justify-content: start;
		justify-items: start;
		align-items: start;
		align-content: flex-start;
	}
</style>
