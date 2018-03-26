<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>开闭锁统计</title>
    <!-- 引入 ECharts 文件 echarts.js -->
    <script type="text/javascript" src="${basePath}/js/echarts/echarts.min.js"></script>


</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;">
    <input type="submit" value="年统计" onclick="myChart.setOption(1)">
    <input type="button" value="月统计" onclick="myChart.setOption(option)">
    <input type="button" value="月统计" onclick="myChart.setOption(onclick)">
</div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('HttpMain'));


    var app = {};
    option = null;

    function fetchData(cb) {
        // 通过 setTimeout 模拟异步加载
        setTimeout(function () {
                cb({
                    categories: ["003", "004", "007", "009", "005", "006"],
                    data: [5, 20, 36, 10, 10, 20],


                });
            },
            3000);
    }


    //     初始 option
    option = {
        title: {
            text: '锁具开闭统计',
            subtext: 'Feature Sample: '
        },
        tooltip: {},
        legend: {
            data: ['次数']
        },
        xAxis: {
            data: [],
            axisLabel: {
                inside: true,
                textStyle: {
                    color: '#b13a38'
                }
            },
            axisTick: {
                show: false
            },
            axisLine: {
                show: false
            },
            z: 10
        },
        yAxis: {
            axisLine: {
                show: false
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                textStyle: {
                    color: '#538d99'
                }
            }
        },
        dataZoom: [
            {
                type: 'inside'
            }
        ],
        series: [
            { // For shadow
                name: '月季',
                type: 'bar',
                itemStyle: {
                    normal: {color: 'rgba(0,0,0,0.5)'}
                },
                barGap: '-100%',
                barCategoryGap: '40%',
                data: [],
                animation: false,

            },
            {
                type: 'bar',
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#83bff6'},
                                {offset: 0.5, color: '#188df0'},
                                {offset: 1, color: '#188df0'}
                            ]
                        )
                    },
                    emphasis: {
                        color: new echarts.graphic.LinearGradient(
                            0, 0, 0, 1,
                            [
                                {offset: 0, color: '#2378f7'},
                                {offset: 0.7, color: '#2378f7'},
                                {offset: 1, color: '#83bff6'}
                            ]
                        )
                    }
                },
//                data: data
            }
        ]
    };

    //     Enable data zoom when user click bar.

    var zoomSize = 6;
    myChart.on('click', function (params) {
        console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
        myChart.dispatchAction({
            type: 'dataZoom',
            startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
            endValue: dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
        });
    });


    // 使用刚指定的配置项和数据显示图表。// 填入数据
    //        myChart.setOption(option);


    //loading 动画  正在加载...
    myChart.showLoading();
    // 异步加载数据
    //            $.get('data.json').done(function (data) {
    fetchData(function (data) {
        //loading 动画  关闭...
        myChart.hideLoading();
        // 填入数据
        myChart.setOption({
            xAxis: {
                data: data.categories
            },
            series: [{
                // 根据名字对应到相应的系列
                name: '月季',
                data: data.data
            }

            ]
//                        title: {
//                            text: '特性示例：渐变色 阴影 点击缩放',
//                            subtext: 'Feature Sample: Gradient Color, Shadow, Click Zoom'
//                        },
//
//                        legend: {
//                            data: ['销量']
//                        },
//                        xAxis: {
//                            data: data.categories,
//                            axisLabel: {
//                                inside: true,
//                                textStyle: {
//                                    color: '#fff'
//                                }
//                            },
//                            axisTick: {
//                                show: false
//                            },
//                            axisLine: {
//                                show: false
//                            },
//                            z: 10
//                        },
//                        yAxis: {
//                            axisLine: {
//                                show: false
//                            },
//                            axisTick: {
//                                show: false
//                            },
//                            axisLabel: {
//                                textStyle: {
//                                    color: '#999'
//                                }
//                            }
//                        },
//                        dataZoom: [
//                            {
//                                type: 'inside'
//                            }
//                        ],
//                        series: [
//                            { // For shadow
//                                type: 'bar',
//                                itemStyle: {
//                                    normal: {color: 'rgba(0,0,0,0.05)'}
//                                },
//                                barGap: '-100%',
//                                barCategoryGap: '40%',
//                                name: '月季',
//                                data: data.data,
//                                animation: false
//                            },
//                            {
//                                type: 'bar',
//                                itemStyle: {
//                                    normal: {
//                                        color: new echarts.graphic.LinearGradient(
//                                            0, 0, 0, 1,
//                                            [
//                                                {offset: 0, color: '#83bff6'},
//                                                {offset: 0.5, color: '#188df0'},
//                                                {offset: 1, color: '#188df0'}
//                                            ]
//                                        )
//                                    },
//                                    emphasis: {
//                                        color: new echarts.graphic.LinearGradient(
//                                            0, 0, 0, 1,
//                                            [
//                                                {offset: 0, color: '#2378f7'},
//                                                {offset: 0.7, color: '#2378f7'},
//                                                {offset: 1, color: '#83bff6'}
//                                            ]
//                                        )
//                                    }
//                                },
//                                data: data
//                            }
//                        ]
        });
//                });
    })

    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
</script>
</body>
</html>
