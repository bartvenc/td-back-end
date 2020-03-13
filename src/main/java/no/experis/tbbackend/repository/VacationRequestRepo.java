package no.experis.tbbackend.repository;

import no.experis.tbbackend.HibernateUtil;
import no.experis.tbbackend.model.VacationRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    @Override
    public VacationRequest findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(VacationRequest.class, id);
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
}
