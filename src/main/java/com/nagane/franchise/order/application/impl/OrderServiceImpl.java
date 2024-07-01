package com.nagane.franchise.order.application.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagane.franchise.order.application.OrderService;
import com.nagane.franchise.order.dao.OrderMenuRepository;
import com.nagane.franchise.order.dao.OrderRepository;
import com.nagane.franchise.order.domain.Order;
import com.nagane.franchise.order.dto.order.OrderDetailDto;
import com.nagane.franchise.order.dto.order.OrderResponseDto;
import com.nagane.franchise.order.dto.order.PaymentResponseDto;
import com.nagane.franchise.order.dto.ordermenu.OrderMenuResponseDto;
import com.nagane.franchise.store.dao.StoreRepository;
import com.nagane.franchise.store.domain.Store;

/**
 * @author ljy
 * @since 2024.07.01
 * Order Service 코드
 * 주문 관련 Service impl
 * **/
@Service
public class OrderServiceImpl implements OrderService {
	
	// 로그 설정
	private final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	// 필요 레포 연결
	private final StoreRepository storeRepository;
	private final OrderRepository orderRepository;
	private final OrderMenuRepository orderMenuRepository;
	
	// 의존성 주입(di)
	@Autowired
	public OrderServiceImpl(
			StoreRepository storeRepository, 
			OrderRepository orderRepository,
			OrderMenuRepository orderMenuRepository) {
		this.storeRepository = storeRepository;
		this.orderRepository = orderRepository;
		this.orderMenuRepository = orderMenuRepository;
	}
		
	/**
	 * 현재 주문 목록 조회
	 * @param Long storeNo
	 * @return List<OrderResponseDto>
	 */
	@Override
	public List<OrderResponseDto> getOrderList(Long storeNo) {
	    LOGGER.info("[getTableList] input storeNo : {}", storeNo);
	    try {
	        // 해당 가맹점 받아오기
	        Store nowStore = this.storeRepository.findById(storeNo)
	                .orElseThrow(() -> new NoSuchElementException("지점을 찾을 수 없습니다."));
	        
	        // 모든 order 목록 조회
	        List<Order> orderList = this.orderRepository.findByStoreNoAndState(storeNo);
	        
	        // return할 changedOrderList 미리 생성
	        List<OrderResponseDto> changedOrderList = new ArrayList<>();
	        
	        // 각 order 엔티티 객체 OrderResponseDto로 변경해서 changedOrderList에 추가
	        orderList.forEach(order -> {
	        	
	        	// 각 orderMenu 항목 먼저 변환(메뉴번호, 메뉴명, 주문한 개수)
	        	List<OrderMenuResponseDto> orderMenuResponseList = new ArrayList<>();
	        	
	        	order.getOrderMenuList().forEach(orderMenu -> {
	        		OrderMenuResponseDto orderMenuResponseDto = OrderMenuResponseDto.builder()
	        				.menuNo(orderMenu.getMenu().getMenuNo())
	        				.menuName(orderMenu.getMenu().getMenuName())
	        				.quantity(orderMenu.getQuantity())
	        				.build();
	        		
	        		orderMenuResponseList.add(orderMenuResponseDto);
	        	});
	        	
	        	
	            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
	            		.orderNo(order.getOrderNo())
	            		.amount(order.getAmount())
	            		.orderDate(order.getOrderDate())
	            		.state(order.getState())
	            		.paymentMethod(order.getPaymentMethod())
	            		.updatedDate(order.getUpdatedDate())
	            		.tableNo(order.getTable().getTableNo())
	            		.orderMenuList(orderMenuResponseList)
	                    .build();
	            
	            changedOrderList.add(orderResponseDto);
	        });
	        
	        return changedOrderList;
	    } catch (Exception e) {
	        LOGGER.error("Error occurred while getting table list: ", e);
	        throw e;
	    }
	}

	/**
	 * 선택한 주문 상세 정보 조회
	 * @param Long orderNo
	 * @return List<OrderDetailDto>
	 */
	@Override
	public List<OrderDetailDto> getOrder(Long orderNo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 결제 목록 조회(영수증)
	 * @param Long storeNo
	 * @return List<PaymentResponseDto>
	 */
	@Override
	public List<PaymentResponseDto> getPaymentList(Long storeNo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 단일 결제 환불
	 * @param Long orderNo
	 * @return void
	 */
	@Override
	public void refundPayment(Long orderNo) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 해당 테이블 주문 내역 조회
	 * @param Long  tableNo
	 * @return List<OrderResponseDto>
	 */
	@Override
	public List<OrderResponseDto> getTableOrder(Long tableNo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 주문 신규 등록
	 * @param OrderCreateDto orderCreateDto
	 * @return void
	 */
	@Override
	public void createOrder(Long tableNo) {
		// TODO Auto-generated method stub
		
	}

}
