package com.kars.downloader.exception;

import java.util.ArrayList;
import java.util.List;

import com.kars.downloader.enums.Errors;

/**
 * InvalidDataException
 * 
 * @author karthik.subbaramaiah
 *
 */
public class ValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Errors> errors = new ArrayList<>();

	public ValidationException() {
		super();
	}

	public ValidationException(String msg) {
		super(Errors.GENERIC_ERROR.getMsg());
		this.errors.add(Errors.GENERIC_ERROR);
	}

	public ValidationException(Errors error) {
		super(error.toString());
		this.errors.add(error);
	}

	public ValidationException(List<Errors> errors) {
		super(errors.toString());
		StringBuilder msg = new StringBuilder();
		errors.forEach(e -> msg.append(e.getMsg()).append("\n"));
		this.errors = errors;
	}

	public List<Errors> getErrors() {
		return errors;
	}

}
