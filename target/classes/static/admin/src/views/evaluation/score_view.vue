<template>
	<el-main class="bg edit_wrap score_edit_wrap">
		<el-form ref="form" :model="form" status-icon label-width="70px">
			<el-row class="row_e">
				<el-col :xs="24" :sm="24" :lg="24" class="el_form_item_warp">
					<div class="title_margin" style="margin-bottom: 20px" v-if="form.comment_state === 1">已点评</div>
				</el-col>
				<el-col v-for="(o, i) in question_list" :xs="24" :sm="24" :lg="24" class="el_form_item_warp">
					<div class="title_margin" v-if="o.type === '单选题'">
						{{ o.question_order + '.单选题：' + o.title }}
					</div>
					<div class="title_margin" v-else-if="o.type === '多选题'">
						{{ o.question_order + '.多选题：' + o.title }}
					</div>
					<div class="title_margin" v-else-if="o.type === '判断题'">
						{{ o.question_order + '.判断题：' + o.title }}
					</div>
					<div class="title_margin" v-else-if="o.type === '填空题'">
						{{ o.question_order + '.填空题：' + o.title }}
					</div>
					<div class="title_margin" v-else>
						{{ o.question_order + '.主观题：' + o.title }}
					</div>
					<el-form-item v-if="o.type === '单选题'" label="">
						<el-radio :disabled="true" v-for="q in get_question_list(o.question_item)" v-model="answers[i]"
							:label="q">{{ q }}</el-radio>
					</el-form-item>
					<el-form-item v-else-if="o.type === '多选题'" label="">
						<el-checkbox-group :disabled="true" v-model="answers[i]">
							<el-checkbox v-for="q in get_question_list(o.question_item)" :label="q"></el-checkbox>
						</el-checkbox-group>
					</el-form-item>
					<el-form-item v-else-if="o.type === '判断题'" label="">
						<el-radio :disabled="true" v-for="q in get_question_list(o.question_item)" v-model="answers[i]"
							:label="q">{{ q }}</el-radio>
					</el-form-item>
					<el-form-item v-else-if="o.type === '填空题'" label="">
						<el-input :disabled="true" style="width: 500px" v-model="answers[i]"></el-input>
					</el-form-item>
					<el-form-item v-else label="">
						<el-input :disabled="true" type="textarea" v-model="answers[i]">
						</el-input>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="24" class="el_form_item_warp">
					<el-form-item label="评语">
						<el-input :disabled="if_scored" type="textarea" v-model="form.comment_desc"></el-input>
					</el-form-item>
				</el-col>

				<el-col :xs="24" :sm="24" :lg="24" class="el_form_item_warp">
					<el-form-item label="提交人">
						<el-input disabled="" v-model="this.form.nickname"></el-input>
					</el-form-item>
				</el-col>


			</el-row>
			<el-col :xs="24" :sm="24" :lg="24" class="el_form_btn_warp">
				<el-form-item>
					<el-col :xs="24" :sm="24" :lg="12" class="el_form_btn el_form_btn_1">
						<el-button v-if="!if_scored" style="width: 100%; float: left;" type="primary" @click="submit()">
							提交
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
				field: "user_answer_id",
				url_add: "~/api/evaluation_user_answer/add?",
				url_set: "~/api/evaluation_user_answer/set?",
				url_get_obj: "~/api/evaluation_user_answer/get_obj?",
				url_upload: "~/api/evaluation_user_answer/upload?",

				query: {
					user_answer_id: 0,
				},
				form: {
					user_answer_id: 0,
					user_id: 0,
					exam_id: 0,
					answers: [],
					nickname: "",
					comment_desc: ""
				},
				url_get_question_list: "~/api/evaluation_question/get_list?like=0&",
				question_list: [],
				answers: [],
				if_scored: false,
				answer_scores: []
			}
		},
		methods: {
			async get_obj_after() {
				if (this.form.comment_state === 1) {
					this.if_scored = true
				}
				let json = await this.$get(this.url_get_question_list + "exam_id=" + this.form.exam_id + "&orderby=question_order");
				if (json.result) {
					this.question_list = json.result.list;
					for (let i = 0; i < this.question_list.length; i++) {
						if (this.question_list[i].type === '多选题') {
							this.answers[i] = [];
						} else {
							this.answers[i] = "";
						}
					}
				} else if (json.error) {
					console.error(json.error);
				}
				this.form.answers = JSON.parse(this.form.answers);
				for (let i = 0; i < this.form.answers.length; i++) {
					this.answers[i] = this.form.answers[i].answer;
				}
			},
			get_question_list(question_str) {
				let question_list = question_str.split("\n");
				return question_list;
			},
			submit_main() {
				let _this = this;
				_this.form.answers = JSON.stringify(_this.form.answers);
				let url = _this.url_set + "user_answer_id=" + _this.query.user_answer_id;
				this.$post(url, Object.assign(_this.form, {
					comment_state: 1
				}), function(json, status) {
					console.log("提交结果：", json);
					if (json.result) {
						_this.events("submit_after", json, null);
					} else if (json.error) {
						_this.$toast(json.error.message, 'danger');
					}
				});
			},
			submit_after(json, func) {
				this.$toast('提交成功！', 'success');
				this.$router.push("/evaluation_score_table/table?exam_id=" + this.form.exam_id);
			}
		},
		created() {}
	}
</script>

<style>
	.title_margin {
		margin-left: 70px;
	}

	.give_score {
		margin-left: 50px;
	}

	.countdown span {
		color: red;
	}

	.score_show {
		margin-left: 20px;
		color: red;
	}
</style>
