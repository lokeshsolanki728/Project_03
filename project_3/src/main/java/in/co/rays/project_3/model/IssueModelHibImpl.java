package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.IssueDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * @author lokesh solanki
 *
 */
public class IssueModelHibImpl implements IssueModelInt{
	public long add(IssueDTO dto) throws ApplicationException, DuplicateRecordException {

		System.out.println("in addddddddddddd");
		/* log.debug("usermodel hib start"); */

		Session session = HibDataSource.getSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();

			session.save(dto);

			dto.getId();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
		
			session.close();
		}
		/* log.debug("Model add End"); */
		return dto.getId();

	}

	/**
	 * Delete a User.
	 *
	 * @param dto the bean
	 * @throws ApplicationException the application exception
	 */
	public void delete(IssueDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	/**
	 * Update a user.
	 *
	 * @param bean the bean
	 * @throws ApplicationException     the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 * @throws RecordNotFoundException  the record not found exception
	 */
	public void update(IssueDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	/**
	 * Find User by PK.
	 *
	 * @param pk : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */

	public IssueDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		IssueDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (IssueDTO) session.get(IssueDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	/**
	 * Find User by Login.
	 *
	 * @param login : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */

	public IssueDTO findByLogin(String client) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		IssueDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(IssueDTO.class);
			criteria.add(Restrictions.eq("client", client));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (IssueDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting User by Login " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	/**
	 * Get List of User.
	 *
	 * @return list : List of User
	 * @throws ApplicationException the application exception
	 */
	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	/**
	 * Get List of User with pagination.
	 *
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @return list : List of users
	 * @throws ApplicationException the application exception
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(IssueDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Users list");
		} finally {
			session.close();
		}

		return list;
	}

	/**
	 * Search User.
	 *
	 * @param bean : Search Parameters
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List search(IssueDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	/**
	 * Search User with pagination.
	 *
	 * @param bean     : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @return list : List of Users
	 * @throws ApplicationException the application exception
	 */
	public List search(IssueDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub

		System.out.println(
				"hellllo" + pageNo + "....." + pageSize + "........" + dto.getId() + "......" );

		Session session = null;
		ArrayList<IssueDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(IssueDTO.class);
			if (dto != null) {
				if (dto.getId() != null) {
					criteria.add(Restrictions.like("id", dto.getId()));
				}
				if (dto.getTitle() != null && dto.getTitle().length() > 0) {
					criteria.add(Restrictions.like("title", dto.getTitle() + "%"));
				}

				if (dto.getAssignTo() != null && dto.getAssignTo().length() > 0) {
					criteria.add(Restrictions.like("assignTo", dto.getAssignTo() + "%"));
					System.out.println("Assign to is ======="+dto.getAssignTo());
				}
				if (dto.getOpenDate() != null && dto.getOpenDate().getDate() > 0) {
					criteria.add(Restrictions.like("openDate", dto.getOpenDate()));
				}
				if (dto.getDescription() != null && dto.getDescription().length()> 0) {
					criteria.add(Restrictions.eq("description", dto.getDescription()));
				}
		
				if (dto.getStatus() != null && dto.getStatus().length()> 0) {
					criteria.add(Restrictions.eq("status", dto.getStatus()));
				}
			}
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<IssueDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in user search");
		} finally {
			session.close();
		}
		return list;
}
}
