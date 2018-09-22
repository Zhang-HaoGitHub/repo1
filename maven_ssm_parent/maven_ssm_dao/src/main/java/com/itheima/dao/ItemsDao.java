package com.itheima.dao;

import com.itheima.domain.Items;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("itemsDao")
public interface ItemsDao {
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
