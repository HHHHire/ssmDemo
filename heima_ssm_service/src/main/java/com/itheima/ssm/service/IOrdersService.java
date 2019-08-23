package com.itheima.ssm.service;

import com.itheima.ssm.domain.Orders;

import java.util.List;

public interface IOrdersService {

    public List<Orders> findAll(Integer page, Integer size) throws Exception;

    public Orders findById(Integer ordersId) throws Exception;
}
