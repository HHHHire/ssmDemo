package com.itheima.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.ssm.dao.IOrdersDao;
import com.itheima.ssm.domain.Orders;
import com.itheima.ssm.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrdersServiceImpl implements IOrdersService {

    @Autowired
    private IOrdersDao iOrderDao;

    @Override
    public List<Orders> findAll(Integer page, Integer size) throws Exception {
        PageHelper.startPage(page,size);
        return iOrderDao.findAll();
    }

    @Override
    public Orders findById(Integer ordersId) throws Exception {
        return iOrderDao.findById(ordersId);
    }
}
