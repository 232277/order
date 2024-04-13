package com.dyc.order.cashier.data.response;

/**
 * func: 终端信息
 * author:丁语成 on 2020/2/24 11:28
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class TerminalInfoData {
	private String channelName;//渠道商名称
	private int deptId;//机构编码
	private int id;
	private int merchantId;
	private String merchantName;
	private PrinterInfo printerInfo;
	private String producerName;
	private boolean status;
	private String terminalCd;
	private String terminalModel;
	private String terminalSn;
	private int type;//设备类型 0打印机 1云打印机 2收银机

	public static class PrinterInfo{//打印机信息
		private int id;
		private boolean isCloud;
		private String printerKey;
		private int specification;
		private String terminalIp;
		private String terminalSn;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public boolean isCloud() {
			return isCloud;
		}

		public void setCloud(boolean cloud) {
			isCloud = cloud;
		}

		public String getPrinterKey() {
			return printerKey;
		}

		public void setPrinterKey(String printerKey) {
			this.printerKey = printerKey;
		}

		public int getSpecification() {
			return specification;
		}

		public void setSpecification(int specification) {
			this.specification = specification;
		}

		public String getTerminalIp() {
			return terminalIp;
		}

		public void setTerminalIp(String terminalIp) {
			this.terminalIp = terminalIp;
		}

		public String getTerminalSn() {
			return terminalSn;
		}

		public void setTerminalSn(String terminalSn) {
			this.terminalSn = terminalSn;
		}
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public PrinterInfo getPrinterInfo() {
		return printerInfo;
	}

	public void setPrinterInfo(PrinterInfo printerInfo) {
		this.printerInfo = printerInfo;
	}

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getTerminalCd() {
		return terminalCd;
	}

	public void setTerminalCd(String terminalCd) {
		this.terminalCd = terminalCd;
	}

	public String getTerminalModel() {
		return terminalModel;
	}

	public void setTerminalModel(String terminalModel) {
		this.terminalModel = terminalModel;
	}

	public String getTerminalSn() {
		return terminalSn;
	}

	public void setTerminalSn(String terminalSn) {
		this.terminalSn = terminalSn;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
