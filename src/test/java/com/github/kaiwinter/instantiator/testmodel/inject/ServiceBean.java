package com.github.kaiwinter.instantiator.testmodel.inject;

import com.github.kaiwinter.instantiator.testmodel.inject.impl.DaoBeanImpl;

public interface ServiceBean {

    DaoBeanImpl getDaoClass();

    DaoBean getDaoInterface();

}
