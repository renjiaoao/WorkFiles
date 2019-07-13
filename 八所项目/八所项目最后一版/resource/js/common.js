// var commonUrl = "http://127.0.0.1:8080";
var commonUrl = "";
// var commonPicUrl = "http://127.0.0.1:8080/";
var commonPicUrl = "/";
var commonToken = "";

function takeAjax(url,type,data,sucFn) {	
	$.ajax({
		url: commonUrl+url,
		type: type,
		dataType: 'json',
		data: data,
		//headers: {"token": commonToken},
		success:sucFn,
		error:function() {			
			toastFn("网络错误111，请联系管理员！")
		}
	})
}

function takeAjaxL(url,type,data,sucFn){
	$.ajax({
		url: url,
		type: type,
		dataType: "json",
		data: data,
		success:sucFn,
		error:function(){
			toastFn("网络错误www，请联系管理员!!！")
		}
	})
}

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return decodeURI(r[2]);
    return null;
}
function GetParentQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.parent.location.search.substr(1).match(reg);
    if (r != null)
        return decodeURI(r[2]);
    return null;
}

function toastFn(message) {
    var self = this;
    $(".errorBox").html(message)
    $(".errorBox").fadeIn().delay(1500).fadeOut()
}

function HtmlFontsize(ob){
	if(ob){
		ob.css("fontSize",$(window).width()/19.2+'px');
	}else{
		$("html").css("fontSize",$(window).width()/19.2+'px');
		$("body").css("fontSize",$(window).width()/19.2+'px');
	}
}

//编辑

//取消
$("#cancleEdit").click(function(event) {
  /* Act on the event */
   $(".toClose").click()
});
$(".toClose").on('click', function(event) {
  event.preventDefault();
  $(".sureEdit").fadeOut();
  $("#shadow").fadeOut();
});

function currentTimes(){
	var nowDate = new Date();
    var hours = nowDate.getHours();
    var minute = nowDate.getMinutes();
    var seconds = nowDate.getSeconds();
    if(hours<10) hours = "0"+hours ;
    if(minute<10) minute = "0"+minute ;
    if(seconds<10) seconds = "0"+seconds ;
	  $("#currentTimes").text("");
	  var timeString = hours+":"+minute+":"+seconds;
	  $("#currentTimes").text(timeString);
}

function headerLink(){
	var ob=$('#header a'),url='/html/logic/monitoringImg?departId='+GetQueryString("departId");
	if($('iframe').length>0){
		if($.trim($('.contentContainerBody .title').text())=='系统初始化'){
			url='javascript:;';
			ob.click(function(){
				$('.tabContainer>li>.active').removeClass('active');
				$('iframe').attr('src','/html/logic/welcome');
			});
		}else{}
		ob.attr('href',url);
	}else if(ob.length>0&&$.trim(ob.attr('href'))=="/html/logic/monitoringImg"){
		ob.attr('href',url);
	}
}
//select模拟绑定
function selectFunc(){
    $('body').click(function(){
    	$(".selectDownContent:visible").fadeOut();
    })
    $(".selectBox .selectDown").on('click', function(event) {
      event.preventDefault();
      event.stopPropagation();
      $(".selectBox .selectDownContent").fadeOut();
      $(this).siblings(".selectDownContent").fadeIn();
    });
    $(".selectBox .selectDownContent").on('click', 'li', function(event) {
      event.preventDefault();
      var lId = $(this).attr("lId");
      var liText = $(this).text();
      $(this).parent("ul").siblings("p").attr("lId",lId).text(liText);
      $(this).parent("ul").siblings('input').val(lId).trigger('changes');
      $(this).closest('.selectDownContent').fadeOut();
    });
    function changesFn(){
    	var v=$(this).val();
    	$(this).siblings('p').attr("lId",v).text( $('[lId='+v+']',$(this).siblings("ul")).text() );
    }
    $(".selectBox input").each(changesFn).on('changes',changesFn);
}
//获取处室列表 //带使用人联动
function rankFunc(vObj){
	var usersData = null;
	function getUserList(v) {
		var data=usersData[v]?usersData[v]:[],userList = ['<li lId="0">未选择</li>'],ul=$('#yonghuid').siblings('ul'),vUser=vObj&&vObj.user ? vObj.user : 0;
		for (var j=0; j<data.length; j++) {
			var u = data[j];
			userList.push('<li lId="'+ u.id_user +'">'+ u.name +'</li>');
		}
		ul.html(userList.join(''));
		$('#yonghuid').val(vUser).siblings('p').attr('lId',vUser).text($('[lId='+vUser+']',ul).text());
	};
	(function loadSubdepartList() {
		var departId = GetQueryString("departId");
		$.get('/depart/user/query/all', function (data) {
			data = typeof data==='object' ? data : JSON.parse(data);
			if (Number(data.type)===0){
				if (!data.data || !data.data.subDeptList){return;}
				var subInfoData = data.data.subDeptList , subdepartList = ['<li lId="0">未选择</li>'],ul=$('#chushiid').siblings('ul'),vSubdepart=vObj&&vObj.subdepart ? vObj.subdepart : 0;
				usersData = data.data.userMap?data.data.userMap:{};
				for (var i=0;i<subInfoData.length;i++) {
					var d = subInfoData[i];
					subdepartList.push('<li lId="'+ d.id_sub_department +'">'+ d.name +'</li>');
				}
				ul.html(subdepartList.join(''));
				$('#chushiid').val(vSubdepart).siblings('p').attr('lId',vSubdepart).text($('[lId='+vSubdepart+']',ul).text());
				getUserList(vSubdepart);
			}else if (Number(data.type)===4){
				window.parent.location.href = "/html/login";
			}else{
				toastFn(data.desc+' ['+data.type+']'); return false;
			}
		});
	})();
	$('#chushiid').on('changes', function() {
		var v=$(this).val();
		getUserList(v?v:0);
	});
}
//获取处室列表 //不带使用人联动
function onlyRankFunc(option){
	var ops=$.extend({
		el:$('#chushiid'),
		default:true,
		val:0
	},option)
	$.get('/depart/subdepartment/query?departId=0', function (data) {
		data = typeof data==='object' ? data : JSON.parse(data);
		
		if (Number(data.type)===0){
			if(data.data && data.data.departmentList) {
				var subInfoData = data.data.departmentList , subdepartList = ops.default ? ['<li lId="0">未选择</li>'] : [],ul=ops.el.siblings('ul'),vSubdepart=ops.val;
				usersData = data.data.userMap?data.data.userMap:{};
				for (var i=0;i<subInfoData.length;i++) {
					var d = subInfoData[i];
					subdepartList.push('<li lId="'+ d.id_sub_department +'">'+ d.name +'</li>');
				}
				ul.html(subdepartList.join(''));
				ops.el.val(vSubdepart).siblings('p').attr('lId',vSubdepart).text($('[lId='+vSubdepart+']',ul).text());
			}
		}else if (Number(data.type)===4){
			window.parent.location.href = "/html/login";
		}else{
			toastFn(data.desc+' ['+data.type+']'); return false;
		}
	});
}
//向head添加link
function createLink(cssURL,charset,media){
	var head = document.getElementsByTagName('body')[0], linkTag = null;
	if(!cssURL){ return false; }
	linkTag = document.createElement('link');
	linkTag.setAttribute('rel','stylesheet');
	linkTag.setAttribute('type','text/css');
	linkTag.href = cssURL;
	head.appendChild(linkTag);
}
//列表数据处理
function dataRender(option){
	var ops=$.extend({
		laypageId:'IDlaypage',
		listMain:$('.listMain'),
		data:[], //数据
		nums:10, //单页条数
		itemHtml:function(index,itemData){ return ''; },
		otherData:{},
		htmlData:function(curr){
		    var str = '',data=this.data,nums=this.nums, last = curr*nums - 1;
		    last = last >= data.length ? (data.length-1) : last;
		    for(var i = (curr*nums - nums); i <= last; i++){
		        str += this.itemHtml(i,data[i]);
		    }
		    return str;
		}
	},option);
	ops.pages = Math.ceil(ops.data.length/ops.nums); //得到总页数
	(function int(ops){
		laypage({
		    cont:ops.laypageId,
			dir:'',
		    pages:ops.pages,
		    jump:function(obj){
    			ops.listMain.empty().html( ops.htmlData(obj.curr) );
    		}
		});
	})(ops);
}
//载体/柜门 的status状态  载体【未用（0）、在位（1）、取走（2）、外出（3）】   柜门【关闭（0）、开启（1）、被撬（2）、超时未关（3）】
function statusText(type,index){
	var i=Number(index),txt='';
	if(type===0){
		txt=i==0?'未用':i==1?'在位':i==2?'取走':i==3?'外出':i; //载体
	}else if(type===1){
		txt=i==0?'关闭':i==1?'开启':i==2?'被撬':i==3?'超时未关':i; //柜门
	}
	return txt;
}


