package in.co.rays.project_3.test;

import java.text.SimpleDateFormat;

import in.co.rays.project_3.dto.OrderDTO;
import in.co.rays.project_3.model.OrderModelHibImp;

public class OrderModelTest {

	public static void main(String[] args) throws Exception {
		testadd();
		//testUpdate();
		//testFindByPk();
		//testDelete();
		//testSearch();
		
	}

	

	private static void testadd() throws Exception {
		OrderDTO dto=new OrderDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		dto.setId(1L);
		dto.setQuantity(1);
		dto.setProduct("pen");
		dto.setAmount(56789);
		dto.setDate(sdf.parse("2001-08-09"));
		OrderModelHibImp model = new OrderModelHibImp();
		long pk=model.add(dto);
		
	}

}
