/*
 * 创建一个包含所有卡片的数组
 */

var Myarr = ['fa-github-alt','fa-github-alt','fa-android','fa-android','fa-btc','fa-btc','fa-bolt','fa-bolt','fa-facebook-square','fa-facebook-square','fa-steam','fa-steam','fa-leaf','fa-leaf','fa-apple','fa-apple']
var Harr = '';
var Vhtml = '';
// var Tablehtml = ' <caption th:text="${username}"></caption><tr><td>No.</td><td>Time</td><td>Click</td></tr>';
// var countline = 0;
// var countrow = 0;
var count = 0;
/*
 * 显示页面上的卡片
 *   - 使用下面提供的 "shuffle" 方法对数组中的卡片进行洗牌
 *   - 循环遍历每张卡片，创建其 HTML
 *   - 将每张卡的 HTML 添加到页面
 */

// 洗牌函数来自于 http://stackoverflow.com/a/2450976
function shuffle(array) {
    var currentIndex = array.length, temporaryValue, randomIndex;

    while (currentIndex !== 0) {
        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex -= 1;
        temporaryValue = array[currentIndex];
        array[currentIndex] = array[randomIndex];
        array[randomIndex] = temporaryValue;
    }

    return array; 
}
function refresh(){
    //使按钮生效可以点击
    $("#sbtn1").removeAttr("disabled");
    $("#sbtn2").html("开始");
    //重置时间
    reset();
    Harr =  shuffle(Myarr); //"shuffle" 方法对数组中的卡片进行洗牌
    Vhtml='';//定义卡片html
    for(var i=0;i<Harr.length;i++){
        Vhtml = Vhtml+'<li class="card"> <i class="fa '+Harr[i]+'"></i> </li>'
    }
    //循环遍历每张卡片，生成卡片html
    $("#deck").html(Vhtml);//将每张卡的 HTML 添加到页面
    $(".moves").html(0);
}
function run(){
    Vhtml='';
    for(var i=0;i<Harr.length;i++){
        Vhtml = Vhtml+'<li class="card"> <i class="fa '+Harr[i]+'"></i> </li>'
    }
    $("#deck").html(Vhtml);//将每张卡的 HTML 添加到页面
    //console.log(Vhtml)
    fclick();//对每个卡片注册点击事件
    sessionStorage.setItem("ss",0);//初始化点击次数
    sessionStorage.setItem("pp",0);//初始化已匹配数量
    sessionStorage.setItem("tt","xx");//用来判断当前匹配效果是否完成，xx为已完成
    $(".moves").html(0);//点击次数初始化赋值
}
function start(){
    //使按钮失效，不可以点击
    $("#sbtn1").attr("disabled",true);
    $("#sbtn2").html("!");
    Vhtml='';
    for(var i=0;i<Harr.length;i++){
        Vhtml = Vhtml+'<li class="card open show"> <i class="fa '+Harr[i]+'"></i> </li>'
    }
    //循环遍历每张卡片，生成卡片html
    $("#deck").html(Vhtml);//将每张卡的 HTML 添加到页面

    setTimeout(function(){
        run();
        begin();
    },10000);

}
function fclick(){
    $("#deck li").click(function(){
        if($(this).hasClass("open")||$(this).hasClass("match")){
            return false;//如果当前卡片是打开或者已经匹配了的状态则点击无效
        }
        if(sessionStorage.getItem("tt")!="xx"){
            return false;//如果匹配效果还未完成点击无效，防止一次点三个
        }
        var ss = parseInt(sessionStorage.getItem("ss"))+1;//点击次数加1
        var pp = parseInt(sessionStorage.getItem("pp"));
        sessionStorage.setItem("ss",ss);
        $(".moves").html(ss);
        if(parseInt(sessionStorage.getItem("ss"))%2==0){
            //如果是第二次点击，则进入匹配机制
            sessionStorage.setItem("tt","yy");
            $(this).addClass("open show open2");//第二个加上open2
            //console.log($(".open1").html());
            //console.log($(".open2").html());
            if($(".open1").html()==$(".open2").html()){  //如果两个里面的html都是一样的  则匹配成功
                setTimeout(function(){
                    $(".open1,.open2").addClass("match");
                    $(".open1").removeClass("show open1");
                    $(".open2").removeClass("show open2");
                    sessionStorage.setItem("tt","xx");
                    sessionStorage.setItem("pp",pp+1);
                    if(pp==7){ //当匹配成功次数达到8，则匹配完成
                        // setTimeout(function(){
                        //     alert("你共用了"+ss+"步");
                        // },500)
                        begin();
                        var table=document.getElementById("tableshow");
                        var row=table.insertRow(count+1);
                        var cell1=row.insertCell(0);
                        var cell2=row.insertCell(1);
                        var cell3=row.insertCell(2);
                        cell1.innerHTML=count+1;
                        var time = gettime();
                        var msec = doubleNumber(time % 60);
                        var sec = doubleNumber(parseInt(time / 60) % 60);
                        var min = doubleNumber(parseInt(time / 3600) % 60);
                        cell2.innerHTML=min+":"+sec+":"+msec;
                        cell3.innerHTML=ss;
                        count++;
                    }
                },0)
            }else{
                setTimeout(function(){
                    //匹配不成功，关闭卡片
                    $(".open1").removeClass("open show open1");
                    $(".open2").removeClass("open show open2");
                    sessionStorage.setItem("tt","xx");
                },500)
            }
        }else{
            //alert(1)
            $(this).addClass("open show open1");//第一个加上open1
        }
    })
}
refresh()

/*
 * 设置一张卡片的事件监听器。 如果该卡片被点击：
 *  - 显示卡片的符号（将这个功能放在你从这个函数中调用的另一个函数中）
 *  - 将卡片添加到状态为 “open” 的 *数组* 中（将这个功能放在你从这个函数中调用的另一个函数中）
 *  - 如果数组中已有另一张卡，请检查两张卡片是否匹配
 *    + 如果卡片匹配，将卡片锁定为 "open" 状态（将这个功能放在你从这个函数中调用的另一个函数中）
 *    + 如果卡片不匹配，请将卡片从数组中移除并隐藏卡片的符号（将这个功能放在你从这个函数中调用的另一个函数中）
 *    + 增加移动计数器并将其显示在页面上（将这个功能放在你从这个函数中调用的另一个函数中）
 *    + 如果所有卡都匹配，则显示带有最终分数的消息（将这个功能放在你从这个函数中调用的另一个函数中）
 */
