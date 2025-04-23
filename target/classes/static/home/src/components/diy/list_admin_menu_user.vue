<template>
  <div class="list_menu diy_menu">
    <router-link
            to="/user_center/index"
            class="menu_item"
            :class="{ selected: this.$route.path=== '/user_center/index'}"
     >
      <span class="left_span">个人首页</span>
      <span class="right_span"><b-icon icon="house-fill"></b-icon></span>
    </router-link>
          <router-link
          to="/ordinary_users/table"
          class="menu_item"
          :class="{ selected: this.$route.path=== '/ordinary_users/table'}"
          v-if="$check_action('/ordinary_users/table','get')">
        <span class="left_span">普通用户</span>
        <span class="right_span"><b-icon icon="house-fill"></b-icon></span>
      </router-link>
              <router-link
          to="/weather_type/table"
          class="menu_item"
          :class="{ selected: this.$route.path=== '/weather_type/table'}"
          v-if="$check_action('/weather_type/table','get')">
        <span class="left_span">天气类型</span>
        <span class="right_span"><b-icon icon="house-fill"></b-icon></span>
      </router-link>
              <router-link
          to="/weather_information/table"
          class="menu_item"
          :class="{ selected: this.$route.path=== '/weather_information/table'}"
          v-if="$check_action('/weather_information/table','get')">
        <span class="left_span">天气信息</span>
        <span class="right_span"><b-icon icon="house-fill"></b-icon></span>
      </router-link>
              <router-link
          to="/rainfall_data/table"
          class="menu_item"
          :class="{ selected: this.$route.path=== '/rainfall_data/table'}"
          v-if="$check_action('/rainfall_data/table','get')">
        <span class="left_span">降雨量数据</span>
        <span class="right_span"><b-icon icon="house-fill"></b-icon></span>
      </router-link>
          <router-link
              to="/message/table"
              class="menu_item"
              :class="{ selected: this.$route.path=== '/message/table'}"
              v-if="$check_action('/message/table','get')">
        <span class="left_span">{{$page_title("/message/table") || "反馈建议"}}</span>
        <span class="right_span"><b-icon icon="house-fill"></b-icon></span>
      </router-link>
      <router-link
              to="/evaluation/table"
              class="menu_item"
              :class="{ selected: this.$route.path=== '/evaluation/table'}"
              v-if="$check_action('/evaluation/table','get')">
        <span class="left_span">{{$page_title("/evaluation/table") || "调查问卷"}}</span>
        <span class="right_span"><b-icon icon="house-fill"></b-icon></span>
      </router-link>
  <router-link
              to="/user/collect"
              class="menu_item"
              :class="{ selected: this.$route.path=== '/user/collect'}"
              v-if="$check_action('/collect/list','get')">
        <span class="left_span">收藏</span>
        <span class="right_span"><b-icon icon="house-fill"></b-icon></span>
  </router-link>
	<router-link
	            to="/comment/table"
	            class="menu_item"
	            :class="{ selected: this.$route.path=== '/comment/table'}"
	            v-if="$check_action('/comment/table','get')">
	      <span class="left_span">评论管理</span>
	      <span class="right_span"><b-icon icon="house-fill"></b-icon></span>
	</router-link>
  </div>
</template>

<script>
import mixin from "@/mixins/page.js";
export default {
  mixins: [mixin],
  data() {
    return {
    };
  },
  mounted() {
  },
  methods: {
  },

  components: {
  },
};
</script>

<style scoped>
.list_menu {
  flex: 0 0 25%;
  border-bottom: 1px solid #eee;
}
.menu_item {
  display: flex;
  justify-content: space-between;
  padding: 15px 20px;
  border: 1px solid #eee;
  border-bottom: none;
  background-color: #fff;
}
.menu_item.selected {
  background-color: #000;
}
.menu_item.selected span {
  color: #fff ;
}
.content {
  flex: 0 0 74%;
}
</style>
