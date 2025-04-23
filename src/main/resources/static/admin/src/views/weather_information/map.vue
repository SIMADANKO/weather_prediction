<template>
  <div id="map"></div>
</template>
<script>
import mixin from "@/mixins/page.js";
import { baiduMap } from "@/utils/baidu_map.js";

export default {
  mixins: [mixin],
  data() {
    return {
      map: null,
    };
  },
  mounted() {
    baiduMap().then((BMap) => {
      this.initMap(BMap);
    });
  },
  methods: {
    async get_list() {
      const res = await this.$get("~/api/weather_information/get_list", {
        page: 1,
        size: 9999,
      });
      return res.result.list || [];
    },

    // 初始化地图
    async initMap(BMap) {
      this.map = new BMap.Map("map");
      const points = await this.get_list();

      // 设置默认中心点和缩放级别
      var defaultPoint = new BMap.Point(116.404, 39.915); // 这里使用北京天安门的经纬度作为示例
      this.map.centerAndZoom(defaultPoint, 15); // 默认缩放级别为15

      // 如果有数据，则添加标注和调整视野
      if (points && points.length) {
        var baiduPoints = [];
        points.map((item) => {
          if (item.mark_address) {
            var point = new BMap.Point(item.mark_lng, item.mark_lat);
            baiduPoints.push(point);
            var marker = new BMap.Marker(point);
            this.map.addOverlay(marker);
            var label = new BMap.Label(item.mark_address, {
              position: point,
              offset: new BMap.Size(-20, 30),
            });
            label.setStyle({
              color: "red",
              fontSize: "12px",
              height: "20px",
              lineHeight: "20px",
              fontFamily: "微软雅黑",
            });
            marker.setLabel(label);
            this.addClickHandler(item, marker);
          }
        });
        this.map.setViewport(baiduPoints);
      }

      // 添加地图控件
      this.map.addControl(new BMap.NavigationControl());
      this.map.addControl(new BMap.ScaleControl());
      this.map.addControl(new BMap.OverviewMapControl());
      this.map.addControl(new BMap.MapTypeControl());
      this.map.enableScrollWheelZoom(true); // 启用滚轮放大缩小
    },

    // 添加点击事件处理函数
    addClickHandler(data, marker) {
      const self = this;
      marker.addEventListener("click", function (e) {
        const content = `
              <div class="map_tips" style="height: 200px; overflow-y: auto;padding-bottom: 20px;">
			  			  				  								地区名称: `+data.region_name+`<br>
			  					  				  			  				  								发布时间: `+data.release_time+`<br>
			  					  				  			  				  								天气图片: <img src="`+self.$fullUrl(data.weather_pictures)+`" style="width: 100%;"><br>
			  					  				  			  				  								天气类型: `+data.weather_type+`<br>
			  					  				  			  				  			  				                </div>
            `;
        self.openInfo(content, e);
      });
    },

    // 打开信息窗口函数
    openInfo(content, e) {
      var p = e.target;
      var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
      // 创建信息窗口对象，并设置偏移量
      var infoWindow = new BMap.InfoWindow(content, {
        width: 250, // 信息窗口宽度
        height: 200, // 信息窗口高度
        title: "详细信息", // 信息窗口标题
        offset: new BMap.Size(10, -30),
      });
      this.map.openInfoWindow(infoWindow, point);
    },
  },
};
</script>

<style type="text/css">
#map {
  width: 100%;
  min-height: 700px;
  max-height: 100%;
}
</style>
