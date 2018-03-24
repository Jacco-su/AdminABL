//import com.dream.util.extend.Charts;

import com.dream.brick.equipment.action.CaptureAction;

public class test1 {

//    public static void main(String[] arga) {
//        CaptureAction.CalculateGeoServlet().;
//        System.out.println(os);
//    }


    //获取目录下全部文件及文件夹
    public static void main(String[] args) {
        try {
            CaptureAction.getFiles("/uploads");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    //ftp文件拉取测试
//    public static void main(String[] args) {
//        try {
//            FtpUtil.downfile("D:/ftp/");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


////     void add(@ModelAttribute Qgorg orga){
////        Area area=BasicData.findAreaByAreacode(orga.getAreacode());
////    }
//public ChartsAction charts;
//    ChartsAction a = charts.init();
//    ChartsAction b = charts.service(1,2);
//    public static void main (String[] a){
//
//
//       System.out.println(a);
//    }
//
}
