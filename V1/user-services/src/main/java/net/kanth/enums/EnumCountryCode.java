package net.kanth.enums;

public enum EnumCountryCode {

    IN("India", "+91"),
    US("United States", "+1"),
    UK("United Kingdom", "+44"),
    CA("Canada", "+1"),
    AU("Australia", "+61"),
    UAE("United Arab Emirates", "+971"),
    SA("Saudi Arabia", "+966"),
    SG("Singapore", "+65"),
    JP("Japan", "+81"),
    CN("China", "+86"),
    DE("Germany", "+49"),
    FR("France", "+33"),
    RU("Russia", "+7"),
    BR("Brazil", "+55"),
    ZA("South Africa", "+27");

    private final String countryName;
    private final String phoneCode;

    EnumCountryCode(String countryName, String phoneCode) {
        this.countryName = countryName;
        this.phoneCode = phoneCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getPhoneCode() {
        return phoneCode;
    }
}