package com.github.kaiwinter.instantiator.testmodel.diffpackage2;

import javax.inject.Inject;

import com.github.kaiwinter.instantiator.testmodel.diffpackage1.DifferentPackageInterface;

public class DifferentPackageServiceImpl {

    @Inject
    public DifferentPackageInterface differentPackageInterface;
}
