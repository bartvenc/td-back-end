package no.experis.tbbackend.repository;

import no.experis.tbbackend.HibernateUtil;
import no.experis.tbbackend.model.VacationRequest;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class VacationRequestRepo implements MainRepository<VacationRequest> {

    @Override
    public void save(VacationRequest vacationRequest) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(vacationRequest);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<VacationRequest> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from VacationRequest", VacationRequest.class).list();
        }
    }

    public List findAllByUserID(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<VacationRequest> list = session.createSQLQuery("SELECT v.* FROM vacation_requests v JOIN request_user vr ON v.request_id = vr.request_id JOIN users u ON vr.user_id = u.id WHERE vr.user_id = ?1").setParameter(1, id).addEntity(VacationRequest.class).getResultList();
            return list;
        }
    }

    public VacationRequest findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(VacationRequest.class, id);
        }
    }

    public List findAllAproved() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List list = session.createSQLQuery("SELECT v.* FROM vacation_requests v JOIN request_state vr ON v.request_id = vr.request_id JOIN vacation_request_status u ON vr.status_id = u.status_id WHERE u.status = 'Approved'").addEntity(VacationRequest.class).getResultList();
            return list;
        }
    }

    @Override
    public void update(VacationRequest vacationRequest) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(vacationRequest);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(VacationRequest vacationRequest) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(vacationRequest);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteRequest_State(long id,long userId, long status_id, long comment_id){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM request_state where request_id = ?1 AND status_id = ?2").setParameter(1, id).setParameter(2, status_id).executeUpdate();
            session.createSQLQuery("DELETE FROM vacation_request_status where status_id = ?1").setParameter(1, status_id).executeUpdate();
            session.createSQLQuery("DELETE FROM request_user where request_id = ?1 and user_id = ?2").setParameter(1, id).setParameter(2, userId).executeUpdate();
            session.createSQLQuery("DELETE FROM vacation_request_comments where request_id = ?1 and comment_id = ?2").setParameter(1, id).setParameter(2, comment_id).executeUpdate();
            session.createSQLQuery("DELETE FROM vacation_requests where request_id = ?1").setParameter(1, id).executeUpdate();

            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteRequest_Comment(long id, long comment_id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM vacation_request_comments where request_id = ?1 AND comment_id = ?2").setParameter(1, id).setParameter(2, comment_id).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
