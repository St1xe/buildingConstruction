package ru.sfedu.buildingconstruction.lab3_1.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.sfedu.buildingconstruction.Constants;
import ru.sfedu.buildingconstruction.lab3_1.model.ApartmentHouse;
import ru.sfedu.buildingconstruction.lab3_1.model.Building;
import ru.sfedu.buildingconstruction.lab3_1.model.Garage;
import ru.sfedu.buildingconstruction.lab3_1.model.House;
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
            metadataSources.addAnnotatedClass(Building.class);
            metadataSources.addAnnotatedClass(ApartmentHouse.class);
            metadataSources.addAnnotatedClass(House.class);
            metadataSources.addAnnotatedClass(Garage.class);

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
