package com.mangoer.indexlietview;

/**
 * @ClassName People
 * @Description TODO()
 * @author Mangoer
 * @Date 2017/12/18 15:27
 */
public class People {

    String name;
    String pinyin;
    String company_line;
    String phone_line;
    String office_line;

    public People(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;
    }

    public String getCompany_line() {
        return company_line;
    }

    public void setCompany_line(String company_line) {
        this.company_line = company_line;
    }

    public String getPhone_line() {
        return phone_line;
    }

    public void setPhone_line(String phone_line) {
        this.phone_line = phone_line;
    }

    public String getOffice_line() {
        return office_line;
    }

    public void setOffice_line(String office_line) {
        this.office_line = office_line;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }


    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }

}
