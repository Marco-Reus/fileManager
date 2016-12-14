package de.bvb.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import de.bvb.domain.FileInfo;

public class WebUtils {
    public static final String MESSAGE_URI = "/WEB-INF/page/message.jsp";
    public static final String MESSAGE_KEY = "message";
    public static final String UPLOAD_URI = "/WEB-INF/upload";
    public static final String UPLOAD_CACHE_URI = "/WEB-INF/uploadCache";

    @SuppressWarnings("unchecked")
    public static FileInfo upload(HttpServletRequest request, String uppath) throws FileSizeLimitExceededException {
        FileInfo bean = new FileInfo();

        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024 * 1024);// 设置临时文件缓冲区大小 1M

            //设置临时文件目录
            File cacheDir = new File(request.getSession().getServletContext().getRealPath(UPLOAD_CACHE_URI));
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            factory.setRepository(cacheDir);

            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8"); //处理文件类型的乱码
            upload.setFileSizeMax(1000 * 1000 * 500);//限制文件大小为500M
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");//处理普通字段乱码
                    BeanUtils.setProperty(bean, name, value);
                } else {
                    //得到上传文件名
                    String filename = item.getName().substring(item.getName().lastIndexOf("\\") + 1);
                    if("".equals(filename)){
                        throw new FileUploadException("文件不能为空");
                    }
                    //得到文件的保存名称
                    String uuidname = generateFilename(filename);
                    //得到文件的保存路径
                    String savepath = generateSavePath(uppath, uuidname);
                    //保存文件
                    InputStream in = item.getInputStream();
                    int len = 0;
                    byte buffer[] = new byte[1024];
                    FileOutputStream out = new FileOutputStream(savepath + "\\" + uuidname);
                    while ((len = in.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                    in.close();
                    out.close();
                    item.delete();

                    bean.setFilename(filename);
                    bean.setId(UUID.randomUUID().toString());
                    bean.setSavepath(savepath);
                    bean.setUptime(new Date());
                    bean.setUuidname(uuidname);
                }
            }
            return bean;
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateFilename(String filename) {
        String ext = filename.substring(filename.lastIndexOf(".") + 1);//获取后缀名
        return UUID.randomUUID().toString() + "." + ext;
    }

    private static String generateSavePath(String path, String filename) {
        int hashcode = filename.hashCode();
        int dir1 = hashcode & 15;
        int dir2 = (hashcode >> 4) & 0xf;

        String savepath = path + File.separator + dir1 + File.separator + dir2;
        File file = new File(savepath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return savepath;
    }
}
