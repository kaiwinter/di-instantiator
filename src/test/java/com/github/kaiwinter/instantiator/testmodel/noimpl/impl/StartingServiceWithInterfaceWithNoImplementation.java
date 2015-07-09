package com.github.kaiwinter.instantiator.testmodel.noimpl.impl;

import javax.inject.Inject;

import com.github.kaiwinter.instantiator.testmodel.noimpl.HaveNoImplementation;

public class StartingServiceWithInterfaceWithNoImplementation {

    @Inject
    private HaveNoImplementation bean;

    public HaveNoImplementation getBean() {
        return bean;
    }

}
