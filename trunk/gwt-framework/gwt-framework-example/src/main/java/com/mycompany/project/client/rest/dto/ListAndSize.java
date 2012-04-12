package com.mycompany.project.client.rest.dto;

import com.google.web.bindery.autobean.shared.AutoBean;

import java.util.List;

public interface ListAndSize {
    public List<MyPOJO> getList();
    public Integer getSize();
}
