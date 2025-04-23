import Vue from 'vue';
import VueRouter from 'vue-router';
import index from '../views/index.vue'
import login from '../views/account/login.vue';
Vue.use(VueRouter)

const routes = [
	// 主页
	{
		path: '/',
		name: 'index',
		component: index
	},
	// 登录
	{
		path: '/account/login',
		name: 'login',
		component: login
	},
	// 忘记密码
	{
		path: '/account/forgot',
		name: 'forgot',
		component: () => import('../views/account/forgot.vue')
	},
	// 注册账号
	{
		path: '/account/register',
		name: 'register',
		component: () => import('../views/account/register.vue')
	},
		// 媒体图片
	{
		path: '/media/image',
		name: 'media_image',
		component: () => import('../views/media/image.vue')
	},

	// 音乐
	// {
	// 	path: '/media/music',
	// 	name: 'media_music',
	// 	component: () => import('../views/media/music.vue')
	// },
	// 媒体视频
	{
		path: '/media/video',
		name: 'media_video',
		component: () => import('../views/media/video.vue')
	},
	// 媒体视频
	{
		path: '/user_center/index',
		name: 'user_center_index',
		component: () => import('../views/user_center/index.vue')
	},
	// 文章路由
	{
		path: '/article/list',
		name: 'article_list',
		component: () => import('../views/article/list.vue')
	},
	{
		path: '/article/details',
		name: 'article_details',
		component: () => import('../views/article/details.vue')
	},
	// 浏览网站
	// 收藏路由
	{
		path: '/user/collect',
		name: 'collect_list',
		component: () => import('../views/user/collect.vue')
	},


	{
		path: '/comment/table',
		name: 'comment_table',
		component: () => import('../views/comment/table.vue')
	},
	{
		path: '/comment/view',
		name: 'comment_view',
		component: () => import('../views/comment/view.vue')
	},

	
   // 测评问卷路由
   {
      path: '/evaluation/list',
      name: 'evaluation_list',
      component: () => import('../views/evaluation/list.vue')
   },
   {
      path: '/evaluation/details',
      name: 'evaluation_details',
      component: () => import('../views/evaluation/details.vue')
   },
   // 测评问卷路由
   {
      path: '/evaluation/table',
      name: 'evaluation_table',
      component: () => import('../views/evaluation/table.vue')
   },
   {
      path: '/evaluation/view',
      name: 'evaluation_view',
      component: () => import('../views/evaluation/view.vue')
   },
   {
      path: '/evaluation_question_table/table',
      name: 'evaluation_question_table_table',
      component: () => import('../views/evaluation/question_table.vue'),
      meta: {
         index: 0,
         title: '题库列表'
      }
   },
   {
      path: '/evaluation_question_table/table',
      name: 'evaluation_question_table_table',
      component: () => import('../views/evaluation/question_table.vue'),
      meta: {
         index: 0,
         title: '题库列表'
      }
   },
   {
      path: '/evaluation_question_view/view',
      name: 'evaluation_question_view_view',
      component: () => import('../views/evaluation/question_view.vue'),
      meta: {
         index: 0,
         title: '题库详情'
      }
   },
   {
      path: '/evaluation_score_table/table',
      name: 'evaluation_score_table_table',
      component: () => import('../views/evaluation/score_table.vue'),
      meta: {
         index: 0,
         title: '点评列表'
      }
   },
   {
      path: '/evaluation_score_view/view',
      name: 'evaluation_score_view_view',
      component: () => import('../views/evaluation/score_view.vue'),
      meta: {
         index: 0,
         title: '点评详情'
      }
   },
   {
      path: '/evaluation_answer_view/view',
      name: 'evaluation_answer_view_view',
      component: () => import('../views/evaluation/answer_view.vue'),
      meta: {
         index: 0,
         title: '点评详情'
      }
   },


	 // 留言路由
	 {
	 	path: '/message/list',
	 	name: 'message_list',
	 	component: () => import('../views/message/list.vue')
	 },
	 {
		path: '/message/edit',
		name: 'message_edit',
		component: () => import('../views/message/edit.vue')
	 },
	// 留言板路由
	{
		path: '/message/table',
		name: 'message_table',
		component: () => import('../views/message/table.vue')
	},
	{
		path: '/message/view',
		name: 'message_view',
		component: () => import('../views/message/view.vue')
	},

	// 公告路由
	{
		path: '/notice/list',
		name: 'notice_list',
		component: () => import('../views/notice/list.vue')
	},
	{
		path: '/notice/details',
		name: 'notice_details',
		component: () => import('../views/notice/details.vue')
	},
	// 天气预报
	{
		path: '/web/index',
		name: 'web_index',
		component: () => import('../views/web/index.vue')
	},
	// 普通用户表格路由
	{
		path: '/ordinary_users/table',
		name: '/ordinary_users_table',
		component: () => import('../views/ordinary_users/table.vue')
	},
	// 普通用户详情路由
	{
		path: '/ordinary_users/view',
		name: '/ordinary_users_view',
		component: () => import('../views/ordinary_users/view.vue')
	},
	
	
		
		
		
	// 天气类型表格路由
	{
		path: '/weather_type/table',
		name: '/weather_type_table',
		component: () => import('../views/weather_type/table.vue')
	},
	// 天气类型详情路由
	{
		path: '/weather_type/view',
		name: '/weather_type_view',
		component: () => import('../views/weather_type/view.vue')
	},
	
	
		
		
		
	// 天气信息表格路由
	{
		path: '/weather_information/table',
		name: '/weather_information_table',
		component: () => import('../views/weather_information/table.vue')
	},
	// 天气信息详情路由
	{
		path: '/weather_information/view',
		name: '/weather_information_view',
		component: () => import('../views/weather_information/view.vue')
	},
	
		// 天气信息列表路由
	{
		path: '/weather_information/list',
		name: '/weather_information_list',
		component: () => import('../views/weather_information/list.vue')
	},
	
		// 天气信息详情路由
	{
		path: '/weather_information/details',
		name: '/weather_information_details',
		component: () => import('../views/weather_information/details.vue')
	},
		
		// 天气信息地图路由
	{
		path: '/weather_information/map',
		name: '/weather_information_map',
		component: () => import('../views/weather_information/map.vue')
	},
		
		
	// 降雨量数据表格路由
	{
		path: '/rainfall_data/table',
		name: '/rainfall_data_table',
		component: () => import('../views/rainfall_data/table.vue')
	},
	// 降雨量数据详情路由
	{
		path: '/rainfall_data/view',
		name: '/rainfall_data_view',
		component: () => import('../views/rainfall_data/view.vue')
	},
	
	
		
		
		

	// 用户路由
	{
		path: '/user/index',
		name: 'user_index',
		component: () => import('../views/user/index.vue')
	},
	// 基本信息
	{
		path: '/user/info',
		name: 'user_info',
		component: () => import('../views/user/info.vue')
	},
	// 找回密码
	{
		path: '/user/password',
		name: 'user_password',
		component: () => import('../views/user/password.vue')
	},

	// 搜索
	{
		path: '/search',
		name: 'search',
		component: () => import('../views/search/index.vue')
	},
	// 局部搜索
	{
		path: '/search/details',
		name: 'search_details',
		component: () => import('../views/search/details.vue')
	}
]

const router = new VueRouter({
	mode: 'hash',
	base: process.env.BASE_URL,
	routes
})

router.afterEach((to, from, next) => {
	let title = "韶关市降雨量数据分析与可视化系统-home";
	document.title = title;
	document.logo = "韶关市降雨量数据分析与可视化系统"
})

export default router
