package com.example.controller;
 
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import com.example.domain.PdfDataTest;
import com.example.utils.PdfTemplateUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
@RequestMapping("/pdf")
public class PdfController {
    /**
     * @param : [response]
     * @return : void
     * @date : 2019/8/10 13:20
     * @exception:
     * @Description: 有返回值的
     */
    @RequestMapping("/export")
    public void exportPdf(HttpServletResponse response) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = null;
        OutputStream out = null;
        try {
            Map<String, Object> data = getStringObjectMap();
            byteArrayOutputStream = PdfTemplateUtil.createPDF(data, "pdf.ftl");
            // 设置响应消息头，告诉浏览器当前响应是一个下载文件
            response.setContentType("application/x-msdownload");
            // 告诉浏览器，当前响应数据要求用户干预保存到文件中，以及文件名是什么 如果文件名有中文，必须URL编码
            String fileName = URLEncoder.encode("月度报告.pdf", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            out = response.getOutputStream();
            byteArrayOutputStream.writeTo(out);
            byteArrayOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
 
    private Map<String, Object> getStringObjectMap() {
        // 模板中的数据，实际运用从数据库中查询
        Map<String, Object> data = new HashMap<>();
        data.put("curr", 1);
        data.put("one", 2);
        data.put("two", 1);
        data.put("three", 6);
        List<PdfDataTest> detailList = new ArrayList<>();
        detailList.add(new PdfDataTest(123456, "测试", "测试", "测试", "测试"));
        detailList.add(new PdfDataTest(111111, "测试", "测试", "测试", "测试"));
        detailList.add(new PdfDataTest(222222, "测试", "测试", "测试", "测试"));
        data.put("detailList", detailList);
        return data;
    }
 
    /**
     * @param : [response]
     * @return : void
     * @date : 2019/8/10 13:20
     * @exception: Exception
     * @Description: 改进后无返回值的
     */
    @RequestMapping("/export2")
    public void exportPdf2(HttpServletResponse response) throws Exception {
        Map<String, Object> data = getStringObjectMap();
        String fileName = URLEncoder.encode("xxx报告.pdf", "UTF-8");
        try {
            PdfTemplateUtil.createPDF(data, "pdf.ftl", fileName, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("生成pdf失败：" + e.getMessage());
        }
    }
}
 