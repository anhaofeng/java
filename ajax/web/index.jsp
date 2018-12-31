<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>AJAX_Study_01</title>

  <!-- <script type="text/javascript" src="js/jquery-3.0.0.js" ></script>
   <script type="text/javascript" src="js/check.js" ></script>-->

  <script type="text/javascript" src="js/check_2.js" ></script>

</head>
<body>
请输入用户名:
<!-- ajax方式下不需要使用表单来提交数据 -->
<!-- ajax方式不需要name属性,需要一个id属性 -->
<input type="text" id="username" />
<input type="button" value="校验" onclick="check()" />

<!-- 该div用于显示服务器端返回的信息 -->
<span id="result" color="red"></span>
</body>
</html>