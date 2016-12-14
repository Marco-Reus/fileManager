package de.bvb.web.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.bvb.domain.FileInfo;
import de.bvb.factory.ServiceFactory;
import de.bvb.service.BusinessService;
import de.bvb.utils.FileUtils;
import de.bvb.utils.WebUtils;

public class DeleteServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");
            BusinessService service = ServiceFactory.getInstance().getImpl("BusinessService", BusinessService.class);
            FileInfo fileInfo = service.find(id);
            if (fileInfo == null) {
                request.setAttribute(WebUtils.MESSAGE_KEY, "文件不存在或已经被删除");
                request.getRequestDispatcher(WebUtils.MESSAGE_URI).forward(request, response);
                return;
            }
            File file = new File(fileInfo.getSavepath() + File.separator + fileInfo.getUuidname());
            //删除文件
            FileUtils.deleteFile(file);
            //删除目录
            if (FileUtils.isEmptyDirectory(file.getParentFile())) {
                FileUtils.deleteFile(file.getParentFile());
            }
            if (FileUtils.isEmptyDirectory(file.getParentFile().getParentFile())) {
                FileUtils.deleteFile(file.getParentFile().getParentFile());
            }

            //删除数据库记录
            service.delete(fileInfo);
            //刷新页面
            request.getRequestDispatcher("/servlet/ListServlet").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(WebUtils.MESSAGE_KEY, "删除文件失败");
            request.getRequestDispatcher(WebUtils.MESSAGE_URI).forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
