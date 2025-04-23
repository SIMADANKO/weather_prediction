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
              <!-- 调查问卷 -->
              <div><span>调查问卷</span></div>
              <view_exam v-if="$check_action('/evaluation/table','get')"></view_exam>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
  import list_admin_menu_user from "@/components/diy/list_admin_menu_user.vue";
  import view_exam from "../../components/diy/evaluation/view_exam.vue";
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
      view_exam,
    },
  };
</script>

<style scoped>
  .container {
    min-height: 800px;
  }
</style>
