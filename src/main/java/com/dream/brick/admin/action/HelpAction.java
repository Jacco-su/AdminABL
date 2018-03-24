package com.dream.brick.admin.action;

import org.springframework.web.bind.annotation.RequestMapping;


public class HelpAction {
    @RequestMapping("/help")
    public String help() {
//        File file = new File ("/help.htm");
//        System.out.println("=======--------") ;
//        FileInputStream is = null;
//        BufferedOutputStream bos  = null;
//        try {
//            request.setCharacterEncoding("iso_8859_1");
//            //response.reset();
//            response.setContentType("application/vnd.ms-word;charset=8859_1");
//            response.setHeader("Content-disposition","attachment;filename="+"aa.doc");
//            is = new FileInputStream (file);
//
//            bos  =  new  BufferedOutputStream(response.getOutputStream());
//            byte[]  buffer  =  new byte[2048];
//            while  (is.read(buffer)  !=  -1)  {
//                bos.write(buffer);
//            }
//            //String s ="" ;
//            //s.getBytes() ;
//
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                bos.flush();
//                bos.close();
//                is.close();
//
//                // file.delete();
//
//            } catch (IOException e) {}
//        }
//        //读取word中的内容WordExtractor 用te-extractors-0.4.jar包，te-extractors-0.4.jar是apahce下的poi。jar包的从新封装
//
//        FileInputStream in = new FileInputStream ("../help.doc");
//        WordExtractor extractor = new WordExtractor();
//        String str = extractor.extractText(in);
//        System.out.println("the result length is"+str.length());
//        System.out.println("the result is"+str);
//        SprmOperation so = null ;

        return "/help/help";
    }
}
