package no.experis.tbbackend.repository;

import no.experis.tbbackend.HibernateUtil;
import no.experis.tbbackend.model.IneligiblePeriod;
import no.experis.tbbackend.model.VacationRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class IneligiblePeriodRepo implements MainRepository<IneligiblePeriod> {

    @Override
    public void save(IneligiblePeriod ineligiblePeriod) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(ineligiblePeriod);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<IneligiblePeriod> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from IneligiblePeriod", IneligiblePeriod.class).list();
        }
    }


    public IneligiblePeriod findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(IneligiblePeriod.class, id);
        }
    }


    @Override
    public void update(IneligiblePeriod ineligiblePeriod) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(ineligiblePeriod);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void delete(IneligiblePeriod ineligiblePeriod) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(ineligiblePeriod);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteInel(long id,long u_id){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM ip_user where ip_id = ?1 and user_id =?2").setParameter(1, id).setParameter(2, u_id).executeUpdate();
            session.createSQLQuery("DELETE FROM ineligible_periods where ip_id = ?1").setParameter(1, id).executeUpdate();
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}



