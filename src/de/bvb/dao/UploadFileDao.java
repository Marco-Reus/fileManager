package de.bvb.dao;

import java.util.List;

import de.bvb.domain.FileInfo;

public interface UploadFileDao {

    public void add(FileInfo fileInfo);

    public List<FileInfo> getAll();

    public FileInfo find(String id);

    public void update(FileInfo fileInfo);

    public void delete(FileInfo fileInfo);

}