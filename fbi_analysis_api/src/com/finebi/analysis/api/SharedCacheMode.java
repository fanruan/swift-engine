
package com.finebi.analysis.api;

/**
 * Specifies how the provider must use a second-level cache for the
 * persistence unit.  Corresponds to the value of the <code>persistence.xml</code>
 * <code>shared-cache-mode</code> element, and returned as the result of
 * {@link com.finebi.analysis.api.spi.PersistenceUnitInfo#getSharedCacheMode()}.
 * 
 * @since Java Persistence 2.0
 */
public enum SharedCacheMode {

    /**
     * All entities and entity-related state and data are cached.
     */
    ALL, 

    /**
     * Caching is disabled for the persistence unit.
     */
    NONE, 

    /**
     * Caching is enabled for all entities for <code>Cacheable(true)</code>
     * is specified.  All other entities are not cached.
     */
    ENABLE_SELECTIVE, 

    /**
     * Caching is enabled for all entities except those for which
     * <code>Cacheable(false) is specified.  Entities for which
     * <code>Cacheable(false) is specified are not cached.
     */
    DISABLE_SELECTIVE, 

    /**
     * 
     * Caching behavior is undefined: provider-specific defaults may apply.
     */
    UNSPECIFIED
}
