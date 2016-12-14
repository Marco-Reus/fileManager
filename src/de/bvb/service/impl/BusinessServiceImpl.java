package de.bvb.service.impl;

import java.util.List;

import de.bvb.dao.UploadFileDao;
import de.bvb.domain.FileInfo;
import de.bvb.factory.DaoFactory;
import de.bvb.service.BusinessService;

public class BusinessServiceImpl implements BusinessService {

    private UploadFileDao dao = DaoFactory.getInstance().getImpl("UploadFileDao", UploadFileDao.class);

    @Override
    public void add(FileInfo fileInfo) {
        dao.add(fileInfo);
    }

    @Override
    public List<FileInfo> getAll() {
        return dao.getAll();
    }

    @Override
    public FileInfo find(String id) {
        return dao.find(id);
    }

    @Override
    public void update(FileInfo fileInfo) {
        dao.update(fileInfo);
    }

    @Override
    public void delete(FileInfo fileInfo) {
        dao.delete(fileInfo);
    }
}
