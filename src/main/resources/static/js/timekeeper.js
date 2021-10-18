/**
 * Created by ZHY on 2016/12/28.
 */

// var starttime;
// var endtime;
// var time1;
// var time2;
// var stop1 = 0;
// var stop2 = 0;

function start_timekeeper() {
    // stop1 = 0;
    // stop2 = 0;
    // starttime = new Date().getTime();
    // setTimeout("$('#time-MySQL').text(0)", 0);
    // setTimeout("$('#time-HDFS').text(0)", 0);
    // alert("123");
    // timedCount1();
    var output={"year":document.getElementById("year").value,"month":document.getElementById("month").value,"date":document.getElementById("date").value,"patentType":document.getElementById("patentType").value,"AGY":document.getElementById("AGY").value,"TIVIEW":document.getElementById("TIVIEW").value,"INVIEW":document.getElementById("INVIEW").value,"PAVIEW":document.getElementById("PAVIEW").value};
    $.ajax({
        url:"/patent/query",
        type:"post",
        data:JSON.stringify(output),
        dataType:"json",
        contentType:"application/json",
        success:function f(data) {
            var result=document.getElementById("table-MYSQL");
            var ss="";
            for(var key in data)
            {
                ss+="<tr><td>"+data[key]['ID']+"</td><td>"+data[key]['类型']+"</td><td>"+data[key]['发明人']+"</td><td>"+data[key]['申请人']+"</td><td>"+data[key]['专利名']+"</td><td>"+data[key]['IPC']+"</td><td>"+data[key]['申请日期']+"</td><td>"+data[key]['代理人']+"</td></tr>";
                // for(var key2 in data[key])
                // {
                //     alert(key2+" "+data[key][key2]);
                // }
            }
            // sanic
            result.innerHTML=ss;
        }
    });
    // stop1=1;
    // timedCount2();
}
// function timedCount1() {
//     time1 = new Date().getTime();
//     if (stop1 == 0) {
//         $('#time-MySQL').text(time1 - starttime);
//         setTimeout("timedCount1()", 1);
//     }
// }
// function timedCount2() {
//     time2 = new Date().getTime();
//     if (stop2 == 0) {
//         $('#time-HDFS').text(time2 - starttime);
//         setTimeout("timedCount2()", 1);
//     }
// }

// function end_timekeeper1() {
//     stop1 = 1;
//     endtime = new Date().getTime();
//     setTimeout("console.log(endtime-starttime)", 0);
//     setTimeout("$('#time-MySQL').text(endtime-starttime)", 0);
// }
// function end_timekeeper2() {
//     stop2 = 1;
//     endtime = new Date().getTime();
//     setTimeout("console.log(endtime-starttime)", 0);
//     setTimeout("$('#time-HDFS').text(endtime-starttime)", 0);
// }
