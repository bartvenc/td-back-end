package no.experis.tbbackend.repository;

import no.experis.tbbackend.HibernateUtil;
import no.experis.tbbackend.model.User;
import no.experis.tbbackend.model.VacationRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            return session.createQuery("from VacationRequest").list();
        }
    }

    // @Override

    public List<VacationRequest> findAllByUserID(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM VacationRequest ";
            Query query = session.createQuery(hql);
            List<VacationRequest> lol = new ArrayList<>();
            List<VacationRequest> results = query.list();
            System.out.println("query size " + results.size());
            for (VacationRequest vacationRequest : results) {
                //System.out.println("asdasdasdasd" + vacationRequest.getOwner().size());
                if ((vacationRequest.getOwner().iterator().next().getId() == id)) {
                    lol.add(vacationRequest);
                }
            }
            return lol;
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
