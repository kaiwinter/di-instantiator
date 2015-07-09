package com.github.kaiwinter.instantiator.testmodel.customannotation;

import javax.inject.Inject;

import com.github.kaiwinter.instantiator.testmodel.inject.ServiceBean;

public class StartingServiceWithCustomAnnotation {

    @MyInjectionAnnotation
    private ServiceBean bean;

    @Inject
    private ServiceBean willNotBeInjected;

    public ServiceBean getBean() {
        return bean;
    }

    public ServiceBean getWillNotBeInjected() {
        return willNotBeInjected;
    }
}
