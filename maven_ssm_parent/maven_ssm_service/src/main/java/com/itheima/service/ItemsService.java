package com.itheima.service;

import com.itheima.domain.Items;

import java.util.List;

public interface ItemsService {
    /**
     * 查询所有信息
     * @return
     */
    public List<Items> findAll();

    /**
     * 根据id查询商品信息
     * @return
     */
    public Items findById(int id);

    /**
     * 修改商品信息
     * @param items
     */
    public int updateItems(Items items);
}
