package de.bvb.domain;

import java.util.Date;

/*
 *  create database  study DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
    use study ;
    create table fileManager
    (
            id varchar(40) primary key,
            uuidname varchar(100) not null unique,
            filename varchar(100) not null,
            savepath varchar(255) not null,
            uptime datetime not null,
            description varchar(255)
    )engine=innodb default character set=utf8;   
 */
public class FileInfo {
    private String id;
    private String uuidname; //上传文件的名称，文件的uuid名
    private String filename; //上传文件的真实名称
    private String savepath; //记住文件的位置
    private Date uptime; //文件的上传时间
    private String description; //文件的描述

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuidname() {
        return uuidname;
    }

    public void setUuidname(String uuidname) {
        this.uuidname = uuidname;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSavepath() {
        return savepath;
    }

    public void setSavepath(String savepath) {
        this.savepath = savepath;
    }

    public Date getUptime() {
        return uptime;
    }

    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
