package ru.sfedu.buildingconstruction.lab2.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.sfedu.buildingconstruction.Constants;
import ru.sfedu.buildingconstruction.lab2.model.TestEntity;
import ru.sfedu.buildingconstruction.util.ConfigurationUtil;


public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {

            Configuration configuration = new Configuration()
                    .configure(ConfigurationUtil.getConfigurationValue(Constants.HIBERNATE_CONFIG_LAB_2));
            ServiceRegistry serviceRegistry
                    = new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties())
                            .build();

            MetadataSources metadataSources
                    = new MetadataSources(serviceRegistry);
            metadataSources.addAnnotatedClass(TestEntity.class);

            sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
        }

        return sessionFactory;

    }

    public static void closeSession() {

        if (sessionFactory != null) {
            sessionFactory.close();
        }

    }

}
