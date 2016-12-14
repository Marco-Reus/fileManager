package de.bvb.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.bvb.domain.FileInfo;
import de.bvb.factory.ServiceFactory;
import de.bvb.service.BusinessService;
import de.bvb.utils.WebUtils;

/**
 * 
 * <p><b>Function:     下载
 * </b></p>Class Name: DownloadServlet<br/>
 * Date:2016-12-11下午11:16:40<br/>author:Administrator<br/>since: JDK 1.6<br/>
 */
public class DownloadServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        BusinessService service = ServiceFactory.getInstance().getImpl("BusinessService", BusinessService.class);
        FileInfo fileInfo = service.find(id);
        File file = new File(fileInfo.getSavepath() + File.separator + fileInfo.getUuidname());
        if (!file.exists()) {
            request.setAttribute(WebUtils.MESSAGE_KEY, "文件不存在或已经被删除");
            request.getRequestDispatcher(WebUtils.MESSAGE_URI).forward(request, response);
            return;
        }

        //下载的文件名要做乱码处理,不然下载的时候保存的文件名为乱码
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileInfo.getFilename().getBytes("UTF-8"), "ISO-8859-1"));
        FileInputStream in = new FileInputStream(file);
        int len = 0;
        byte buffer[] = new byte[1024];
        OutputStream out = response.getOutputStream();
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        in.close();

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
