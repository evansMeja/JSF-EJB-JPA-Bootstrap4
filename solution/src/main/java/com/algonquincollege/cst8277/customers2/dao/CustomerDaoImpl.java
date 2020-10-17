/*****************************************************************c******************o*******v******id********
 * File: CustomerDaoImpl.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 *
 */
package com.algonquincollege.cst8277.customers2.dao;

import static com.algonquincollege.cst8277.customers2.model.CustomerPojo.ALL_CUSTOMERS_QUERY_NAME;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import com.algonquincollege.cst8277.customers2.model.CustomerPojo;

/**
* Description: Implements the C-R-U-D API for the database
*/
@Named
@ApplicationScoped
public class CustomerDaoImpl implements CustomerDao, Serializable {
    /** explicitly set serialVersionUID */
    private static final long serialVersionUID = 1L;

    public static final String CUSTOMER_PU = "acmeCustomers-PU";

    protected EntityManager em;
    
    protected ServletContext sc;

    @Inject
    public CustomerDaoImpl(ServletContext sc) {
        super();
        this.sc = sc;
    }
    
    protected void logMsg(String msg) {
        sc.log(msg);
    }
    

    // delegate all C-R-U-D operations to EntityManager

    @Override
    public List<CustomerPojo> readAllCustomers() {
        logMsg("reading all customers");
        TypedQuery<CustomerPojo> allCustomersQuery = em.createNamedQuery(ALL_CUSTOMERS_QUERY_NAME, CustomerPojo.class);
        return allCustomersQuery.getResultList();
    }

    @Override
    @Transactional
    public CustomerPojo createCustomer(CustomerPojo customer) {
        logMsg("creating an customer");
        em.something(customer);
        return customer;
    }

    @Override
    public CustomerPojo readCustomerById(int customerId) {
        logMsg("read a specific customer");
        return em.something(CustomerPojo.class, customerId);
    }

    @Override
    @Transactional
    public CustomerPojo updateCustomer(CustomerPojo customerWithUpdates) {
        logMsg("updating a specific customer");
        CustomerPojo mergedCustomerPojo = em.something(customerWithUpdates);
        return mergedCustomerPojo;
    }

    @Override
    @Transactional
    public void deleteCustomerById(int customerId) {
        logMsg("deleting a specific customer");
        CustomerPojo customer = readCustomerById(customerId);
        if (customer != null) {
            em.refresh(customer);
            em.remove(customer);
        }
    }

}