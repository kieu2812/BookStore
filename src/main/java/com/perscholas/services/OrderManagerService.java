package com.perscholas.services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.perscholas.dao.AbstractDAO;
import com.perscholas.dao.BookDAO;
import com.perscholas.dao.CustomerCardDAO;
import com.perscholas.dao.OrderDetailDAO;
import com.perscholas.dao.OrderManagerDAO;
import com.perscholas.dao.OrdersDAO;
import com.perscholas.dao.ShippingAddressDAO;
import com.perscholas.dao.ShippingDetailDAO;
import com.perscholas.exception.OutOfStockException;
import com.perscholas.model.Customer;
import com.perscholas.model.CustomerCard;
import com.perscholas.model.OrderDetail;
import com.perscholas.model.Orders;
import com.perscholas.model.ShippingAddress;
import com.perscholas.model.ShoppingCart;
import com.perscholas.model.ShoppingCartIem;

@Service

public class OrderManagerService extends AbstractDAO implements OrderManagerDAO {
	
	static Logger log = Logger.getLogger(OrderManagerService.class);
	
	@Autowired
	OrdersDAO orderDAO;
	
	@Autowired
	BookDAO bookDAO;
	
	@Autowired
	OrderDetailDAO orderDetailDao;
	
	@Autowired
	ShippingDetailDAO shipDetailDAO;
	
	@Autowired
	ShippingAddressDAO shippingAddressDAO;
	
	@Autowired
	CustomerCardDAO customerCardDAO;
	
	@SuppressWarnings("unused")
	@Override
	@Transactional(rollbackFor= {OutOfStockException.class, SQLException.class, Exception.class})
	public int saveOrders(ShoppingCart cart) throws OutOfStockException, SQLException   {
		
		log.info("Transaction is active? " + TransactionSynchronizationManager.isActualTransactionActive());
		
		boolean isSuccess=false;
		Orders order = new Orders();
		
		Customer cust= cart.getCustomer();
		log.info("Cutomer from myCart session = " + cust);
		order.setCustomerId(cart.getCustomer().getId());
		
		
	
		
		order.setCreateDate(LocalDate.now());
	
		// check if user choose the same address then store shipping address is 0;
		// else create new shipping address;	
		
		//1. insert shipping address
		ShippingAddress shipAdd= cart.getShippingAddress();
		if(shipAdd.getId()!=0) { 
			log.info("1. Create a new shipping address");
			//ShippingAddressDAO shippingAddressDAO = new ShippingAddressService();
			int shippingId = shippingAddressDAO.insertShipping_Address(shipAdd);
			order.setShippingId(shippingId);
			//log.info("Shipping address= " + shippingAddressDAO.getShipping_AddressById(shippingId));
			log.info("new shippingId = " + shippingId);
		} else {
			log.info("1. Use an existing shipping address");
		}
		// 2. insert Customer Card if new card

		CustomerCard customerCard= cart.getCustomerCard();
		Integer cardId= customerCard.getCardId();
		if(cardId==null || cardId==0) {
			cardId = customerCardDAO.insertCard(customerCard);
			
		}
		log.info("2. Customer card id = " + cardId );		

		order.setCardId(cardId);
	
		// 3.insert orders to order table in database
		log.info("3.insert orders");
		int orderId = orderDAO.insertOrder(order);
		log.info("Order Id= " +orderId);
	
		Map<Integer, ShoppingCartIem> cartItems = cart.getCartItems();
		
		//4. insert order detail
		log.info("4.insert Order Details");
		for(Integer bookId: cartItems.keySet()) {
			
			ShoppingCartIem cartItem = cartItems.get(bookId);
		
			// check quantity in stock must be greater than quantity that customer order
			
			OrderDetail od = new OrderDetail();
			od.setOrderId(orderId);
			od.setBookid(bookId);
			od.setQuantity(cartItem.getQuantity());
			od.setUnit_price(cartItem.getBookInfo().getPrice());
			int orderDetailId;
			
			//INSERT ORDER_DETAIL AND UPDATE QUANTITY IN BOOK TABLE BY TRIGGER UPDATEQUANTITYBOOK
			log.info("Insert Order Detail");
			orderDetailId = orderDetailDao.insertOrderDetail(od);
			log.info("Insert Order Detail with orderDetailId = " + orderDetailId);
			
			

		}
		//5. insert shipping detail get from inventory item available and update inventory table
		log.info("5.Insert Shipping Detail");
		isSuccess= shipDetailDAO.insertByOrderId(orderId);
		log.info("insert Shipping_detail from orderId is success ? "+ isSuccess);
		isSuccess=true;
		return orderId;
	}

}
