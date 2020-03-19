package no.experis.tbbackend.repository;

import no.experis.tbbackend.HibernateUtil;
import no.experis.tbbackend.model.VacationRequestStatus;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class VacationRequestStatusRepo implements MainRepository<VacationRequestStatus> {

    @Override
    public void save(VacationRequestStatus vacationRequestStatus) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(vacationRequestStatus);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<VacationRequestStatus> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from VacationRequestStatus", VacationRequestStatus.class).list();
        }
    }

    @Override
    public VacationRequestStatus findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(VacationRequestStatus.class, id);
        }
    }

    @Override
    public void update(VacationRequestStatus vacationRequestStatus) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.saveOrUpdate(vacationRequestStatus);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(VacationRequestStatus vacationRequestStatus) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(vacationRequestStatus);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}