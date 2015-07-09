package com.github.kaiwinter.instantiator.testmodel.inject.impl;

import javax.inject.Inject;

import com.github.kaiwinter.instantiator.testmodel.inject.ServiceBean;

public class StartingServiceAsInject {

    @Inject
    private ServiceBean serviceBeanInterface;

    @Inject
    private ServiceBean serviceBeanClass;

    public ServiceBean getServiceBeanInterface() {
        return serviceBeanInterface;
    }

    public ServiceBean getServiceBeanClass() {
        return serviceBeanClass;
    }

}
