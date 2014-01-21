package be.vdab.web;


import be.vdab.constraints.Postcode;

class VanTotPostcodeForm {
	@Postcode
	private Integer vanpostcode;
	@Postcode
	private Integer totpostcode;
	
	VanTotPostcodeForm() { // default constructor (package visibility)
	}

	// constructor om command object te initialiseren vanuit Controller:
	VanTotPostcodeForm(Integer vanpostcode, Integer totpostcode) {
		this.vanpostcode = vanpostcode;
		this.totpostcode = totpostcode;
	}

	public Integer getVanpostcode() {
		return vanpostcode;
	}

	public Integer getTotpostcode() {
		return totpostcode;
	}

	public void setVanpostcode(Integer vanpostcode) {
		this.vanpostcode = vanpostcode;
	}

	public void setTotpostcode(Integer totpostcode) {
		this.totpostcode = totpostcode;
	}

	@Override
	public String toString() {
		return String.format("%d-%d", vanpostcode, totpostcode);
	}

	boolean isValid() {
		if (vanpostcode == null || totpostcode == null) {
			return false;
		}
		return vanpostcode <= totpostcode;
	}
}
