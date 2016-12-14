package de.bvb.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import de.bvb.domain.FileInfo;
import de.bvb.factory.ServiceFactory;
import de.bvb.service.BusinessService;
import de.bvb.utils.WebUtils;

/**
 * <p><b>Function:     上传
 * </b></p>Class Name: UploadServlet<br/>
 * Date:2016-12-11下午11:15:47<br/>author:Administrator<br/>since: JDK 1.6<br/>
 */
public class UploadServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            request.setAttribute(WebUtils.MESSAGE_KEY, "不支持的操作");
            request.getRequestDispatcher(WebUtils.MESSAGE_URI).forward(request, response);
            return;
        }

        try {
            String savepath = this.getServletContext().getRealPath(WebUtils.UPLOAD_URI);
            //上传文件
            FileInfo fileInfo = WebUtils.upload(request, savepath);
            //保存到数据库
            BusinessService service = ServiceFactory.getInstance().getImpl("BusinessService", BusinessService.class);
            service.add(fileInfo);
            //刷新页面
            request.getRequestDispatcher("/servlet/ListServlet").forward(request, response);
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            request.setAttribute(WebUtils.MESSAGE_KEY, "文件不能超过500m");
            request.getRequestDispatcher(WebUtils.MESSAGE_URI).forward(request, response);
        } catch (FileUploadException e) {
            request.setAttribute(WebUtils.MESSAGE_KEY, e.getMessage());
            request.getRequestDispatcher(WebUtils.MESSAGE_URI).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute(WebUtils.MESSAGE_KEY, "上传失败");
            request.getRequestDispatcher(WebUtils.MESSAGE_URI).forward(request, response);
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
