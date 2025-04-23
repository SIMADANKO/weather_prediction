<template>
  <div id ="outerdiv">
    <iframe src="http://www.nmc.cn/publish/forecast.html" id ='inneriframe' scrolling="no"></iframe>
  </div>
</template>

<script>
	export default {

	}
</script>

<style scoped="scoped">
    #outerdiv
    {
      margin: 0 auto;
      width:1480px;
      height:2180px;
      overflow:hidden;
      position:relative;
    }

    #inneriframe
    {
      position:absolute;
      top:-150px;
      left:0px;
      width:1580px;
      height:2180px;
    }
</style>
