package com.ecsail.Gybe.dto;

public class PaymentDTO {
	private int payId;
	private int invoiceId;
	private String checkNumber;
	private String paymentType;
	private String paymentDate;
	private String PaymentAmount;
	private int depositId;

	public PaymentDTO(int payId, int invoiceId, String checkNumber, String paymentType, String paymentDate, String paymentAmount, int depositId) {
		this.payId = payId;
		this.invoiceId = invoiceId;
		this.checkNumber = checkNumber;
		this.paymentType = paymentType;
		this.paymentDate = paymentDate;
		PaymentAmount = paymentAmount;
		this.depositId = depositId;
	}

	public int getPayId() {
		return payId;
	}

	public void setPayId(int payId) {
		this.payId = payId;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentAmount() {
		return PaymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		PaymentAmount = paymentAmount;
	}

	public int getDepositId() {
		return depositId;
	}

	public void setDepositId(int depositId) {
		this.depositId = depositId;
	}

	@Override
	public String toString() {
		return "PaymentDTO{" +
				"payId=" + payId +
				", invoiceId=" + invoiceId +
				", checkNumber='" + checkNumber + '\'' +
				", paymentType='" + paymentType + '\'' +
				", paymentDate='" + paymentDate + '\'' +
				", PaymentAmount='" + PaymentAmount + '\'' +
				", depositId=" + depositId +
				'}';
	}
}
