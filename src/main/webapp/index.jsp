<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>单词库列表</title>
<%
	pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<script type="text/javascript"
	src="${APP_PATH }/static/js/jquery-3.3.1.js"></script>
<link
	href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>

<!-- 单词添加的模态框 -->
<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">单词添加</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal">
		  <!-- <div class="form-group">
		    <label class="col-sm-2 control-label">UserName</label>
		    <div class="col-sm-10">
		      <input type="text" name="belong" class="form-control" id="UserName_add_input" placeholder="用户名">
		      <span class="help-block"></span>
		    </div>
		  </div> -->
		  <!-- <div class="form-group">
		    <label class="col-sm-2 control-label">Type</label>
		    <div class="col-sm-10">
		      <input type="text" name="type" class="form-control" id="Type_add_input" placeholder="单词类型">
		      <span class="help-block"></span>
		    </div>
		  </div> -->
		  <div class="form-group">
		     <label class="col-sm-2 control-label">English</label>
		    <div class="col-sm-10">
		      <input type="text" name="origin" class="form-control" id="English_add_input" placeholder="英文">
		      <span class="help-block"></span>
		    </div>
		    </div>
		  <div class="form-group">
		     <label class="col-sm-2 control-label">中文</label>
		    <div class="col-sm-10">
		      <input type="text" name="end" class="form-control" id="Chinese_add_input" placeholder="中文">
		      <span class="help-block"></span>
		    </div>
		    </div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
      </div>
    </div>
  </div>
</div>

	<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h1>单词库</h1>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
			<div class="col-md-4 col-md-offset-8">
				<button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
				<button class="btn btn-danger" id="emp_delete_all_btn">删除</button>
				<!-- <button class="btn btn-primary" id="emp_change_all_btn">翻译</button> -->
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover" id="emps_table">
					<thead>
						<tr>
							<th>
								<input type="checkbox" id="check_all"/>
							</th>
							<th>#</th>
							<th>User</th>
							<th>English</th>
							<th>中文</th>
							<th>time</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					
					</tbody>
				</table>
			</div>
		</div>

		<!-- 显示分页信息 -->
		<div class="row">
			<!--分页文字信息  -->
			<div class="col-md-6" id="page_info_area"></div>
			<!-- 分页条信息 -->
			<div class="col-md-6" id="page_nav_area">
				
			</div>
		</div>
		
	</div>
	<script type="text/javascript">
		var totalRecord,currentPage;
		$(function(){
			//去首页
			to_page(1);
		});
		function to_page(pn){
			$.ajax({
				url:"${APP_PATH}/emps",
				data:"pn="+pn,
				type:"GET",
				success:function(result){
					//解析并显示单词
					build_word_table(result);
					//解析并显示分页信息
					build_page_info(result);
					//解析显示分页条数据
					build_page_nav(result);
					}
				});
			}
		function build_word_table(result){
			$("#emps_table tbody").empty();
			var words = result.extend.pageInfo.list;
			$.each(words,function(index,item){
				var checkBoxTd = $("<td><input type='checkbox' class='check_item'/></td>");
				var wordTd = $("<td></td>").append(item.id);
				var belongTd = $("<td></td>").append(item.belong);
				var originTd = $("<td></td>").append(item.origin);
				var endTd = $("<td></td>").append(item.end);
				//var endTime=new Date(item.inserttime).toString.substring(0,12);
				var endTime=new Date(item.inserttime).toString().substring(0,25);
				var inserttimeTd = $("<td></td>").append(endTime);

				/* var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit_btn")
							  .append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("修改");
				  editBtn.attr("edit-id",item.id); */
				var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
							 .append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
				  delBtn.attr("del-id",item.id);
 
				  var btnTd= $("<td></td>").append(" ").append(delBtn);
				 $("<tr></tr>").append(checkBoxTd)
				 			   .append(wordTd)
				 			   .append(belongTd)
				 			   .append(originTd)
				 			   .append(endTd)
				 			   .append(inserttimeTd)
				 			   .append(btnTd)
				 			   .appendTo("#emps_table tbody");
				});
			}
		//分页信息
		function build_page_info(result){
			$("#page_info_area").empty();
			$("#page_info_area").append("当前"+result.extend.pageInfo.pageNum+"页,总"+
											result.extend.pageInfo.pages+"页,总"+
											result.extend.pageInfo.total+"条记录");
			totalRecord = result.extend.pageInfo.total;
			currentPage = result.extend.pageInfo.pageNum;
			}
		//显示分页条
		function build_page_nav(result){
			$("#page_nav_area").empty();
			var ul =$("<ul></ul>").addClass("pagination");
			var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
			var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
			if(result.extend.pageInfo.hasPreviousPage == false){
				firstPageLi.addClass("disabled");
				prePageLi.addClass("disabled");
				}else{
					firstPageLi.click(function(){
							to_page(1);
						});
					prePageLi.click(function(){
							to_page(result.extend.pageInfo.pageNum-1);
						});
					}
			var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
			var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
			if(result.extend.pageInfo.hasNextPage == false){
				nextPageLi.addClass("disabled");
				lastPageLi.addClass("disabled");
				}else{
					nextPageLi.click(function(){
						to_page(result.extend.pageInfo.pageNum+1);
						});
					lastPageLi.click(function(){
						to_page(result.extend.pageInfo.pages);
						});
					}
			ul.append(firstPageLi).append(prePageLi);
			$.each(result.extend.pageInfo.navigatepageNums,function(index,item){
				var numLi = $("<li></li>").append($("<a></a>").append(item));
				if(result.extend.pageInfo.pageNum == item){
					numLi.addClass("active");
					}
				numLi.click(function(){
					to_page(item);
					});
				ul.append(numLi);
				});
			//添加下一页和末页 的提示
			ul.append(nextPageLi).append(lastPageLi);
			
			//把ul加入到nav
			var navEle = $("<nav></nav>").append(ul);
			navEle.appendTo("#page_nav_area");
			}
		//清空表单样式及内容
		function reset_form(ele){
			$(ele)[0].reset();
			//清空表单样式
			$(ele).find("*").removeClass("has-error has-success");
			$(ele).find(".help-block").text("");
		}
		//点击新增弹出模态框
		$("#emp_add_modal_btn").click(function(){
			//清除表单数据（表单完整重置（表单的数据，表单的样式））
			reset_form("#empAddModal form");
			//弹出模态框
			$("#empAddModal").modal({
				backdrop:"static"
			});
		});
		/* //点击翻译弹出模态框
		$("#emp_change_all_btn").click(function(){
			reset_form("#empChangModal form");
			$("#empChangModal").modal({
				backdrop:"static"
				});
			}); */
		$("#emp_save_btn").click(function(){
			//1.模拟框中填写的表单数据提交给服务器进行保存
			//对提交给数据库的数据进行校验
			//2.发送ajax请求保存员工
			$.ajax({
				url:"${APP_PATH}/emp",
				type:"POST",
				data:$("#empAddModal form").serialize(),
				success:function(result){
					//员工保存成功
						//1.关闭模态框
						if(result.code==100){
						$("#empAddModal").modal('hide');
						//2.显示保存的信息
						to_page(totalRecord);
						}
					}
				});
			});
		/*  $(document).on("click",".edit_btn",function(){
			   

	            $("#emp_update_btn").attr("edit-id",$(this).attr("edit-id"));
			    
			    $("#empUpdateModal").modal({
				    	backdrop:"static"
				    });
			    }); */ 
		    //单个删除
		    $(document).on("click",".delete_btn",function(){
			    var empName = $(this).parents("tr").find("td:eq(4)").text();
			    var empId = $(this).attr("del-id");
			    if(confirm("确定删除【"+empName+"】吗？")){
				    $.ajax({
					    url:"${APP_PATH}/emp/"+empId,
					    type:"DELETE",
					    success:function(result){
						    alert(result.msg);
						    to_page(currentPage);
						    }
					    });
				    }
			    });
		    $("#check_all").click(function(){
				//attr:获取自定义属性的值
				//prop修改和读取dom原生属性的值
				$(".check_item").prop("checked",$(this).prop("checked"));
				});
			$(document).on("click",".check_item",function(){
				var flag = $(".check_item:checked").length==$(".check_item").length;
				$("#check_all").prop("checked",flag);
				});
			//点击全部删除
	        $("#emp_delete_all_btn").click(function(){
	            var empNames = "";
	            var del_idstr = "";
	            $.each($(".check_item:checked"),function(){
	                empNames +=$(this).parents("tr").find("td:eq(4)").text()+",";
	                del_idstr +=$(this).parents("tr").find("td:eq(1)").text()+"-";
	                });
	          //去除empNames多余的,
				empNames = empNames.substring(0, empNames.length-1);
				//去除删除的id多余的-
				del_idstr = del_idstr.substring(0, del_idstr.length-1);
				if(confirm("确认删除【"+empNames+"】吗?")){
					//发送ajax请求删除
					$.ajax({
						url:"${APP_PATH}/emp/"+del_idstr,
						type:"DELETE",
						success:function(result){
							alert(result.msg);
							//回到当前页面
							to_page(currentPage);
						}
					});
					}
	            });
	</script>
</body>
</html>