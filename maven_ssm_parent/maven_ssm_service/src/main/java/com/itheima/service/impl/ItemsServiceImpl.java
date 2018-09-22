package com.itheima.service.impl;

import com.itheima.dao.ItemsDao;
import com.itheima.domain.Items;
import com.itheima.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("itemsService")
public class ItemsServiceImpl implements ItemsService {

    @Autowired
    private ItemsDao itemsDao;

    @Override
    public List<Items> findAll() {
        List<Items> itemsList = itemsDao.findAll();
        return itemsList;
    }

    @Override
    public Items findById(int id) {
        Items items = itemsDao.findById(id);
        return items;
    }

    @Override
    public int updateItems(Items items) {
        return itemsDao.updateItems(items);
    }
}
