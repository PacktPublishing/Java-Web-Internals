package io.isiflix.calc;

import java.io.Serializable;

public class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	private String status;
	private Double value;
	public Response(String status, Double value) {
		super();
		this.status = status;
		this.value = value;
	}
	public Response() {
		super();
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Response [status=" + status + ", value=" + value + "]";
	}
}
