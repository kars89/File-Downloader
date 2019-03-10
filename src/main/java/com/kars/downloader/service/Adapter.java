package com.kars.downloader.service;

/**
 * @author karthik.subbaramaiah
 *
 * @param <T1> - object1
 * @param <T2> - object2
 */
public interface Adapter<T1, T2> {

	/**
	 * convert object1 to object2
	 * 
	 * @param obj object1
	 * @return object2
	 */
	public T2 adaptFrom(T1 obj);

	/**
	 * convert object2 to object1
	 * 
	 * @param obj object2
	 * @return object1
	 */
	public T1 adaptTo(T2 obj);
}
