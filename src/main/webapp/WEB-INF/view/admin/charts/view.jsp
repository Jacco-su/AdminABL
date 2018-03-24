<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
    <script type="text/javascript" src="${basePath}/js/echarts/echarts.min.js"></script>

    <style>
        body {
            padding-left: 21px;
            padding-top: 21px;
        }
    </style>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="vmain" style="width: 600px;height:400px;">1236</div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    //    var myChart = echarts.init(document.getElementById('vmain'));
    //
    //        // 指定图表的配置项和数据
    //        var option = {
    //            title: {
    //                text: 'ECharts 入门示例'
    //            },
    //            tooltip: {},
    //            legend: {
    //                data: ['销量']
    //            },
    //            xAxis: {
    //                data: ["钥钥", "木衫", "例子2", "例子3", "例子4", "例子5"]
    //            },
    //            yAxis: {},
    //            series: [{
    //                name: '销量',
    //                type: 'bar',
    //                data: [5, 20, 36, 10, 10, 20]
    //            }]
    //        };
    //
    //    //    // 使用刚指定的配置项和数据显示图表。
    //        myChart.setOption(option);


    var dom = document.getElementById("vmain");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    function fetchData(cb) {
        // 通过 setTimeout 模拟异步加载
        setTimeout(function () {
            cb({
                categories: ["钥钥", "木衫", "例子2", "例子3", "例子4", "例子5"],
                data: [15, 10, 31, 178, 10, 20]
            }, {
                categories2: ["山崖", "甲胄", "长戈"],
                data2: [780, 10, 20]
            });
        }, 1000);
    }

    // 初始 option
    option = {
        title: {
            text: '钥匙授权统计'
        },
        tooltip: {},
        legend: {
            data: ['月统计2', '年统计']
//            data2:['年统计']
        },

        xAxis: {
            data: [],

        },
        yAxis: {},
        series: [{
            name: '月统计',
            type: 'bar',
            data: [],
        }, {
            name: '年统计',
            type: 'bar',
            data: [],
        }]
    };

    //loading 动画  正在加载...
    myChart.showLoading();

    fetchData(function (data, data2) {

        //loading 动画  关闭...
        myChart.hideLoading();

        myChart.setOption({
            xAxis: {
                data: data.categories,
                data2: data.categories2,
            },
            series: [{
                // 根据名字对应到相应的系列
                name: '月统计2',
                data: data.data,
            }, {
                // 根据名字对应到相应的系列
                name: '年统计',
                data: data2.data2,
            }]

        });
    });
    ;
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
</script>


</body>
</html>
