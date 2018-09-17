package com.perscholas.dao;

import java.sql.SQLException;

import org.springframework.transaction.annotation.Transactional;

import com.perscholas.exception.OutOfStockException;
import com.perscholas.model.ShoppingCart;

@Transactional
public interface OrderManagerDAO  {

	public boolean saveOrders(ShoppingCart carts) throws OutOfStockException, SQLException;
}
