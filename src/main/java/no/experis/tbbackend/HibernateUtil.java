package no.experis.tbbackend;

import java.util.Properties;

import no.experis.tbbackend.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, "jdbc:postgresql://ec2-54-75-246-118.eu-west-1.compute.amazonaws.com:5432/ddm0anpi2hue4a");
                settings.put(Environment.USER, "iaagqfgrqhzcxn");
                settings.put(Environment.PASS, "19028247a60438ba5608b3d59b740d3ff5ca23377f21c97996593acba47a9520");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "validate");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Comment.class);
                configuration.addAnnotatedClass(IneligiblePeriod.class);
                configuration.addAnnotatedClass(VacationRequest.class);
                configuration.addAnnotatedClass(VacationRequestStatus.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
