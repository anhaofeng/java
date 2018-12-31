/**
 * Created by 游戏2 on 2016/10/24.
 */

//用户校验方法
//使用XMLHTTPRequest对象来进行ajax的异步数据交互

var xmlhttprequest;

function check() {

    //1，获取文本框内容
    //使用dom
    var username=document.getElementById("username").value;

    //2，将数据传送至服务器端
    //1,创建XMLHTTPRequest对象
    //需要针对IE和各浏览器建立这个对象的不同方式写不同的代码
    if (window.XMLHttpRequest){
        //针对FireFox,Mozillar,Opera,Safari,IE7,IE8
        xmlhttprequest=new XMLHttpRequest();
        if (xmlhttprequest.overrideMimeType){
            xmlhttprequest.overrideMimeType("text/xml");
        }

    }else if (window.ActiveXObject){

        var actionxName=['MSXML2.XMLHTTP','Microsoft.XMLHTTP'];
        for (var i=0;i<actionxName.length;i++){
            try{
                xmlhttprequest=new ActiveXObject(actionxName[i]);
                break;
            }catch(e) {
                //异常
            }
        }
    }

    //2，注册回调函数（只写函数名）
    xmlhttprequest.onreadystatechange=callback;

    //3，设置连接信息（参数 1，http请求方式; 2,url地址; 3,采用异步还是同步（true为异步））
    //get方式请求
    //xmlhttprequest.open("GET","AJAXServlet?name="+username,true);
    //post方式请求
    xmlhttprequest.open("POST","AJAXServlet",true);
    //POST方式需设置http请求头
    xmlhttprequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");


    //4，发送数据
    //get请求方式
    /*xmlhttprequest.send(null);*/
    //post请求方式
    xmlhttprequest.send("name="+username);
}

//回调函数
function callback(){


    //判断对象是否交互完成
    if (xmlhttprequest.readyState == 4){
        //判断http的交互是否完成
        if (xmlhttprequest.status == 200){
            //3，接受服务器端数据
            var text=xmlhttprequest.responseText;
            //4，将数据显示在页面
            var divNode=document.getElementById('result');
    divNode.innerHTML = text;
}
}


}