<template>
  <div class="page_user exam_view" id="user_address">
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
            <div class="card_address pl-2">
              <!-- 点评 -->
              <div><span>点评</span></div>
              <exam_answer_view v-if="$check_action('/evaluation/table','get')"></exam_answer_view>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
  import list_admin_menu_user from "@/components/diy/list_admin_menu_user.vue";
  import exam_answer_view from "../../components/diy/evaluation/exam_answer_view.vue";
  export default {
    data() {
      return {
      };
    },
    mounted() {
    },
    methods: {
    },
    components: {
      list_admin_menu_user,
      exam_answer_view,
    },
  };
</script>

<style scoped>
  .container {
    min-height: 800px;
  }
</style>
