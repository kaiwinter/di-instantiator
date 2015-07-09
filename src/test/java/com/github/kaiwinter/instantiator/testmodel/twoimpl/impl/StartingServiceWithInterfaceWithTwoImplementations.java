package com.github.kaiwinter.instantiator.testmodel.twoimpl.impl;

import javax.inject.Inject;

import com.github.kaiwinter.instantiator.testmodel.twoimpl.HaveTwoImplementationsBean;

public class StartingServiceWithInterfaceWithTwoImplementations {

    @Inject
    private HaveTwoImplementationsBean bean;

    public HaveTwoImplementationsBean getBean() {
        return bean;
    }

}
