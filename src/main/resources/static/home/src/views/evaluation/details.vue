<template>
    <div class="page_exam page_details" id="exam_view">
        <div class="warp exam_warp" v-if="$check_exam('/evaluation/list', 'answers')">
            <div class="container exam-detail">
                <div class="row row_exam">
                    <div v-if="!if_answered" class="countdown">
                        距离考试结束还有：<span>{{minutes+":"+seconds}}</span>
                    </div>
                    <div class="col-24 col-lg-24 col-md-24">
                        <div v-if="if_answered" class="col-24 col-lg-24 col-md-24">
                            <div class="title_margin" style="margin-bottom: 20px" v-if="comment_state===0">未点评</div>
                            <div class="title_margin" style="margin-bottom: 20px" v-else>已点评</div>
                        </div>
                    </div>
                    <div v-for="(o,i) in question_list" :key="o">
                        <div class="title_margin" v-if="o.type === '单选题'">
                            {{o.question_order+'.单选题：'+o.title}}
                          
                        </div>
                        <div class="title_margin" v-else-if="o.type === '多选题'">
                            {{o.question_order+'.多选题：'+o.title}}
                          
                        </div>
                        <div class="title_margin" v-else-if="o.type === '判断题'">
                            {{o.question_order+'.判断题：'+o.title}}
                          
                        </div>
                        <div class="title_margin" v-else-if="o.type === '填空题'">
                            {{o.question_order+'.填空题：'+o.title}}
                        </div>
                        <div class="title_margin" v-else>
                            {{o.question_order+'.主观题：'+o.title}}
                        </div>
                        <div v-if="o.type === '单选题'">
                            <label style="margin-right: 20px;" v-for="q in get_question_list(o.question_item)">
                                <input :disabled="if_answered" :name="o.title" type="radio" :value="q" v-model="answers[i]"/>{{q}}
                            </label>
                        </div>
                        <div v-else-if="o.type === '多选题'">
                            <label style="margin-right: 20px;" v-for="q in get_question_list(o.question_item)">
                                <input :disabled="if_answered" :name="o.title" type="checkbox" :value="q" v-model="answers[i]"/>{{q}}
                            </label>
                        </div>
                        <div v-else-if="o.type === '判断题'">
                            <label style="margin-right: 20px;" v-for="q in get_question_list(o.question_item)">
                                <input :disabled="if_answered" :name="o.title" type="radio" :value="q" v-model="answers[i]"/>{{q}}
                            </label>
                        </div>
                        <div v-else-if="o.type === '填空题'">
                            <input :name="o.title" :disabled="if_answered" style="width: 500px" v-model="answers[i]" />
                        </div>
                        <div v-else-if="o.type === '主观题'">
                            <textarea :name="o.title" type="" :disabled="if_answered" style="width: 500px" rows="3" cols="20" v-model="answers[i]" ></textarea>
                        </div>
                    </div>

                  <div v-if="if_answered" class="title_margin">
                    <div>
                      <span>评语：</span>
                      <textarea
                          type=""
                          :disabled="true"
                          style="width: 500px"
                          rows="3"
                          cols="20"
                          v-model="answer_form.comment_desc"
                      ></textarea>
                    </div>
                  </div>

                  <div class="title_margin">
                        <div>
                            <span>提交人：</span>
                            <input :disabled="true" style="width: 500px" v-model="submit_user_str" />
                        </div>
                    </div>
                </div>
                <div class="row answer_submit_button">
                    <div class="col-12">
                        <div class="btn_box">
                            <button v-if="!if_answered" class="answer_btn_submit" @click="submit()"><span>提交</span></button>
                            <button class="answer_btn_cancel" @click="cancel()"><span>取消</span></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import mixin from "@/mixins/page.js";
    export default {
        mixins: [mixin],
        components: {
        },
        data() {
            return {
                field: "exam_id",
                url_add: "~/api/evaluation_user_answer/add?",
                url_set: "~/api/evaluation_user_answer/set?",
                url_get_obj: "~/api/evaluation/get_obj?",
                url_upload: "~/api/evaluation_user_answer/upload?",

                query: {
                    exam_id: 0,
                },

                form: {
                    exam_id: 0,
                    name: "",
                    duration: 0,
                    status: "启用"
                },
                url_get_question_list: "~/api/evaluation_question/get_list?",
                question_list:[],
                answers:[],
                submit_user_str:"",
                answer_form:{
                    user_answer_id:0,
                    user_id:0,
                    exam_id:0,
                    comment_state:0,
                    answers:[],
                    nickname:"",
                    comment_desc:"",
                },
                if_answered:false,
                maxtime:0,
                minutes:0,
                seconds:0,
                timer:"",
                comment_state:0,
                answer_scores:[]
            };
        },
        methods: {
            async get_obj_after(){
                let userJson = await this.$get("~/api/user/get_obj?user_id="+this.$store.state.user.user_id);
                if(userJson.result){
                    this.submit_user_str = userJson.result.obj.username+"-"+userJson.result.obj.nickname;
                    this.answer_form.user_id = userJson.result.obj.user_id;
                    this.answer_form.nickname = userJson.result.obj.nickname;
                    this.answer_form.comment_desc = userJson.result.obj.comment_desc;
                }else if(userJson.error){
                    console.error(userJson.error);
                }
                let json = await this.$get(this.url_get_question_list+"exam_id="+this.query.exam_id+"&orderby=question_order");
                if(json.result){
                    this.question_list = json.result.list;
                    for (let i=0;i<this.question_list.length;i++){
                        if (this.question_list[i].type==='多选题'){
                            this.answers[i]=[];
                        }else{
                            this.answers[i]="";
                        }
                    }
                }else if(json.error){
                    console.error(json.error);
                }
                let answerJson = await this.$get("~/api/evaluation_user_answer/get_obj?user_id="+this.answer_form.user_id+"&exam_id="+this.query.exam_id);
                if(answerJson.result && answerJson.result.obj != null){
                    this.answer_form = answerJson.result.obj;
                    this.comment_state = answerJson.result.obj.comment_state
                    this.answer_form.answers = JSON.parse(this.answer_form.answers);
                    for(let i=0;i<this.answer_form.answers.length;i++){
                        this.answers[i] = this.answer_form.answers[i].answer;
                    }
                    this.if_answered = true
                }else if(answerJson.error){
                    console.error(answerJson.error);
                }
                if (!this.if_answered){
                    this.maxtime = this.form.duration*60;
                    this.timer = window.setInterval(() => {
                        setTimeout(this.set_countdown(), 0)
                    }, 1000)
                }
            },
            get_question_list(question_str){
                let question_list = question_str.split("\n");
                return question_list;
            },
            submit_main(){
                let _this = this;
                _this.answer_form.exam_id = _this.query.exam_id;
                for (let i = 0; i < _this.answers.length; i++) {
                    let obj = {
                    exam_question_id: _this.question_list[i].exam_question_id,
                    answer: _this.answers[i],
                    };
                    _this.answer_form.answers[i] = obj;
                }
                _this.answer_form.answers = JSON.stringify(_this.answer_form.answers);
                let url = _this.url_add;
                this.$post(url, _this.answer_form, function(json, status) {
                    console.log("提交结果：" ,json);
                    if (json.result) {
                        _this.events("submit_after", json, null);
                    } else if (json.error) {
                        _this.$toast(json.error.message, 'danger');
                    }
                });
            },
            submit_after(json,func){
              this.$toast('提交成功！', 'success');
              this.$router.push("/evaluation/list");
            },
            set_countdown(){
                if (this.maxtime >= 0) {
                    this.minutes = Math.floor(this.maxtime / 60);
                    this.seconds = Math.floor(this.maxtime % 60);
                    --this.maxtime;
                } else{
                    clearInterval(this.timer);
                    alert("时间到，结束!");
                    this.submit();
                }
            }
        },
        mounted() {
        },
    };
</script>

<style scoped>
    .title_margin{
        width: 1000px;
        margin-top: 20px;
    }
    .answer_submit_button{
        margin-top: 30px;
        padding-left: 70px;
        margin-bottom: 30px;
    }
    .answer_submit_button button{
        width: 100px;
        line-height: 40px;
        border-radius: 5px;
    }
    .answer_btn_submit{
        background-color: blue;
        margin-right: 20px;
        color: white;
    }
    .answer_btn_cancel{
    }
    .countdown{
        width: 1000px;
        margin-top: 20px;
    }
    .countdown span{
        color: red;
    }
    .score_show{
        color: red;
    }
</style>

