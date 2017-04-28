
public class PM {
	String site;
	String county;
	String air;
	String recordDate;

	PM(String site, String county, String air, String recordDate) {
		this.site = site;
		this.county = county;
		this.air = air;
		this.recordDate = recordDate;
	}

	String getSite() {
		return site;
	}

	String getCounty() {
		return county;
	}

	String getAir() {
		return air;
	}

	String getRecordDate() {
		return recordDate;
	}
}

