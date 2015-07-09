package com.github.kaiwinter.instantiator.testmodel.mock.impl;

import javax.inject.Inject;

import com.github.kaiwinter.instantiator.testmodel.mock.ServiceMockBean;

public class StartingServiceWithMock {

    @Inject
    private ServiceMockBean bean;

    public ServiceMockBean getBean() {
        return bean;
    }

}
