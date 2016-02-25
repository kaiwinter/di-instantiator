package com.github.kaiwinter.instantiator;

/**
 * Defines in which scope types are looked up. The {@link PackageScope} defines two general ways to lookup classes: either searching the
 * whole classpath or searching in sub packages of the parent type only. In contrast a custom package name can be set to search only in this
 * package and descendants.
 * 
 * Search for classes as local as possible is a big performance gain (at the first run as we use caching). 
 */
public class LookupContext {

    private PackageScope packageScope;
    private String customPackage;

    /**
     * Advises the factory to search for implementations in the packages defined by <code>packageScope</code>.
     * 
     * @param packageScope
     *            the scope to lookup implementations
     */
    public LookupContext(PackageScope packageScope) {
        this.packageScope = packageScope;
    }

    /**
     * Advises the factory to lookup from <code>customPackage</code> and descendants.
     * 
     * @param customPackage
     *            the package to use as root for the lookup
     */
    public LookupContext(String customPackage) {
        this.customPackage = customPackage;
    }

    public static enum PackageScope {
        /**
         * The complete classpath is searched for implementations (default).
         */
        WHOLE_CLASSPATH,

        /**
         * Only sub-packages are searched for implementations.
         * <p>
         * <b>This expects the implementation of the interface <code>org.package.service</code> to be in a package equal or below
         * <code>org.package.service</code></b>
         * </p>
         */
        SUBPACKAGES_ONLY;
    }

    public PackageScope getPackageScope() {
        return packageScope;
    }

    public String getCustomPackage() {
        return customPackage;
    }
}
