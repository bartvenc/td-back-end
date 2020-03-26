package no.experis.tbbackend.repository;

import no.experis.tbbackend.HibernateUtil;
import no.experis.tbbackend.model.VacationDays;

import org.hibernate.Session;
import org.hibernate.Transaction;


public class VacationDaysRepo {


    public void save(VacationDays vacationDays) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(vacationDays);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    public VacationDays findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(VacationDays.class, id);
        }
    }


    public void update(VacationDays vacationDays) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.saveOrUpdate(vacationDays);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    public void delete(VacationDays vacationDays) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(vacationDays);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
