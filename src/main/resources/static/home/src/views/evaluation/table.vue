<template>
	<div class="page_user emam_table" id="user_address">
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
						<div class="card_addres exam pl-2">
							<!-- 调查问卷 -->
							<div><span>{{$page_title("/evaluation/table") || 调查问卷}}</span></div>
							<table_exam v-if="$check_action('/evaluation/table','get')"></table_exam>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>


<script>
	import list_admin_menu_user from "@/components/diy/list_admin_menu_user.vue";
	import table_exam from "../../components/diy/evaluation/table_exam.vue";
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
			table_exam,
		},
	};
</script>

<style scoped>
	.container {
		min-height: 800px;
	}
</style>
