package com.github.kaiwinter.instantiator.testmodel.inject.impl;

import javax.inject.Inject;

import com.github.kaiwinter.instantiator.testmodel.inject.DaoBean;
import com.github.kaiwinter.instantiator.testmodel.inject.ServiceBean;

public class ServiceBeanImpl implements ServiceBean {

    @Inject
    private DaoBean daoInterface;

    @Inject
    private DaoBeanImpl daoClass;

    @Override
    public DaoBeanImpl getDaoClass() {
        return daoClass;
    }

    @Override
    public DaoBean getDaoInterface() {
        return daoInterface;
    }
}
