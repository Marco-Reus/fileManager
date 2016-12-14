package de.bvb.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import de.bvb.dao.UploadFileDao;
import de.bvb.domain.FileInfo;
import de.bvb.utils.JdbcUtils;

public class UploadFileDaoImpl implements UploadFileDao {
    @Override
    public void add(FileInfo fileInfo) {
        try {
            QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
            String sql = "insert into fileManager(id,uuidname,filename, savepath, uptime, description ) values(?,?,?,?,?,?);";
            Object[] params = { fileInfo.getId(), fileInfo.getUuidname(), fileInfo.getFilename(), fileInfo.getSavepath(), fileInfo.getUptime(),
                    fileInfo.getDescription() };
            runner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FileInfo> getAll() {
        try {
            QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
            String sql = "select * from fileManager order by uptime desc;";
            return (List<FileInfo>) runner.query(sql, new BeanListHandler(FileInfo.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public FileInfo find(String id) {
        try {
            QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
            String sql = "select * from fileManager where id=?;";
            return (FileInfo) runner.query(sql, id, new BeanHandler(FileInfo.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(FileInfo fileInfo) {
        try {
            QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
            String sql = "update fileManager set uuidname=? , set filename=? , set  savepath=? , set  uptime=? , set  description=? where id=? ;";
            Object[] params = { fileInfo.getUuidname(), fileInfo.getFilename(), fileInfo.getSavepath(), fileInfo.getUptime(), fileInfo.getDescription(),
                    fileInfo.getId() };
            runner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(FileInfo fileInfo) {
        try {
            QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
            String sql = "delete from fileManager where id=? ;";
            runner.update(sql, fileInfo.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
