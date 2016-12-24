<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1" />
		<title>通用后台管理系统</title>
		<script type="text/javascript" src="${contextPath}/static/js/jquery-1.11.0.min.js"></script>
		<script type="text/javascript">
			var contextPath = "${contextPath}";
			
			function login(){
				var $userName = document.getElementById("userName").value;
				var $password = document.getElementById("password").value;
				if($userName == ""){
					$("#tip").html("请输入用户名");
					return;
				}
				if($password == ""){
					$("#tip").html("请输入密码");
					return;
				}
				$.ajax({
					dataType : "json",
					url : "${contextPath}/sys/sysuser/login",
					type : "post",
					data : {
						userName : $userName,
						password : $password
					},
					complete : function(xmlRequest) {
						var returninfo = eval("(" + xmlRequest.responseText + ")");
						if (returninfo.result == 1) {
							document.location.href = "${contextPath}/sys/sysuser/home";
						} else if (returninfo.result == -1) {
							$("#tip").html("用户名有误或已被禁用");
						} else if (returninfo.result == -2) {
							$("#tip").html("密码错误");
						} else {
							$("#tip").html("服务器错误");
						}
					}
				});
			}
			
			function reset(){				
				document.getElementById("userName").value="";
				document.getElementById("password").value="";
			}
		</script>
		<style>
			#bgcc {
				position: absolute;
				width: 1019px;
				height: 577px;
				left: 40%;
				top: 35%;
				margin-left: -325px;
				margin-top: -149px;
				
			}
			body{
			background-position:35% 40%;
			background-image: url('static/img/bgtest.jpg');
			}
			
			#content {
				position: absolute;
				left: 221px;
				top: 111px;
				width: 490px;
				height: 266px;
				background-image: url('static/img/bg1.png');
				background-position: 45% 30%;
			}
			
			.inputcss-tip {
				position: absolute;
				width: 200px;
				height: 30px;
				background: transparent;
				border: 0px solid #ffffff;
				color: red;
				font-size: 12px;
				left: 290px;
				top: 100px;
				top: 115px\9;
			}
			
			.inputcss-userName {
				position: absolute;
				width: 200px;
				height: 30px;
				background: transparent;
				border: 0px solid #ffffff;
				font-size: 15px;
				left: 290px;
				top: 121px;
				top: 136px\9;
			}
			
			.inputcss-password {
				position: absolute;
				width: 200px;
				height: 30px;
				background: transparent;
				border: 0px solid #ffffff;
				font-size: 15px;
				left: 290px;
				top: 159px;
				top: 172px\9;
			}
			
			.save {
				position: absolute;
				left: 290px;
				top: 202px;
			}
			
			.reset {
				position: absolute;
				left: 365px;
				top: 202px;
			}
		</style>
	</head>
	<body>
		<div id="bgcc">
			<div id="content">
				<form name="form1" id="form1" method="post" action="${contextPath}/sys/user/login">
					<div id="tip" class="inputcss-tip"></div>
					<div>
						<input name="userName" type="text" class="inputcss-userName" id="userName" value="aaa" /><br>
						<input name="password" type="password" class="inputcss-password" id="password" value="bbb" />
					</div>
					<div class="save">
						<a href="javascript:login();"> 
							<img src="static/img/btnlogin.png"></img>
						</a>
					</div>
					<div class="reset">
						<a href="javascript:reset();"> 
							<img src="static/img/reset.png"></img>
						</a>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>
