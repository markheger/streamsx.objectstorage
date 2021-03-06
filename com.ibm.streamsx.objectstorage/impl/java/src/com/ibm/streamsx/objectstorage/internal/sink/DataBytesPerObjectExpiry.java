package com.ibm.streamsx.objectstorage.internal.sink;

import org.ehcache.ValueSupplier;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expiry;

public class DataBytesPerObjectExpiry implements Expiry<Object, Object> {

	private int fDataBytesPerObject = 0;
	
	public DataBytesPerObjectExpiry(int dataBytesPerObject) {
		fDataBytesPerObject = dataBytesPerObject;
	}
	
	@Override
	public Duration getExpiryForCreation(Object paramK, Object paramV) {
		// the mapping will never expire
		return Duration.INFINITE;
	}

	@Override
	public Duration getExpiryForAccess(Object paramK, ValueSupplier<? extends Object> paramValueSupplier) {
		OSObject value = (OSObject)paramValueSupplier.value();
		
		if (value.fDataBufferSize >= fDataBytesPerObject) {
			// threshold has been reached - immediately expire
			return Duration.ZERO; 
		}
	    // number of tuples in the current object is still lower than
		// threshold - do not touch the entry
		return null;
	}

	@Override
	public Duration getExpiryForUpdate(Object paramK, ValueSupplier<? extends Object> paramValueSupplier,
			Object paramV) {
		
		OSObject value = (OSObject)paramValueSupplier.value();
		
		if (value.fDataBufferSize >= fDataBytesPerObject) {
			// threshold has been reached - immediately expire
			return Duration.ZERO; 
		}
	    // number of tuples in the current object is still lower than
		// threshold - do not touch the entry
		return null;
	}

}


