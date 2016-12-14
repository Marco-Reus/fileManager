package de.bvb.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.bvb.domain.FileInfo;
import de.bvb.factory.ServiceFactory;
import de.bvb.service.BusinessService;
import de.bvb.utils.ListUtils;

/**
 * 
 * <p><b>Function:     文件列表
 * </b></p>Class Name: ListServlet<br/>
 * Date:2016-12-11下午11:16:18<br/>author:Administrator<br/>since: JDK 1.6<br/>
 */
public class ListServlet extends HttpServlet {

    private int currentOrderId = 1;
    private String orderFiled[] = { "", "getFilename", "getUptime" };
    private boolean isOrderDesc = true;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BusinessService service = ServiceFactory.getInstance().getImpl("BusinessService", BusinessService.class);
        List<FileInfo> list = service.getAll();
        if (request.getParameter("order") != null) {
            String order = request.getParameter("order").toString();
            if (order != null) {
                if (currentOrderId == Integer.parseInt(order)) {
                    isOrderDesc = !isOrderDesc;
                }
                currentOrderId = Integer.parseInt(order);
                ListUtils.Sort(list, orderFiled[currentOrderId], isOrderDesc ? "desc" : "asc");
            }
        }
        request.setAttribute("list", list);
        request.getRequestDispatcher("/WEB-INF/page/fileManager.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
