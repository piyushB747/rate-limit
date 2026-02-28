package net.kanth.enums;

public enum EnumOrganizationType {

	CUSTOMER,VENDOR,DISTRIBUTER,RETAILER,SYSTEM;
	
	/*
	SELLER(1), DISTRIBUTER(2), PURCHASER(3), BUYER(4);

	private final int organizationType;

	private EnumOrganizationType(int organizationType) {
		this.organizationType = organizationType;
	}

	public int getOrganizationType() {
		return organizationType;
	}


	public static EnumOrganizationType getOrganization(int code) {
		if(code != 0) {
			for(EnumOrganizationType type : EnumOrganizationType.values()) {
				if(type.getOrganizationType() == code) {
					return type;
				}
			}
		}
		throw new RuntimeException("Account not found");
	}
	
	*/
}
