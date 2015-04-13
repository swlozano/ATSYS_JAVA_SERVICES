package Entidades;
// Generated 26/10/2009 11:06:54 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Country generated by hbm2java
 */
public class Country  implements java.io.Serializable {


     private String code;
     private String name;
     private String continent;
     private String region;
     private Integer capital;
     private String code2;
     private Set cities = new HashSet(0);

    public Country(String code, String name) {
       this.code = code;
       this.name = name;
      
    }

     public Country() {
    }

	
    public Country(String code) {
        this.code = code;
    }
    public Country(String code, String name, String continent, String region, Integer capital, String code2, Set cities) {
       this.code = code;
       this.name = name;
       this.continent = continent;
       this.region = region;
       this.capital = capital;
       this.code2 = code2;
       this.cities = cities;
    }
   
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getContinent() {
        return this.continent;
    }
    
    public void setContinent(String continent) {
        this.continent = continent;
    }
    public String getRegion() {
        return this.region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    public Integer getCapital() {
        return this.capital;
    }
    
    public void setCapital(Integer capital) {
        this.capital = capital;
    }
    public String getCode2() {
        return this.code2;
    }
    
    public void setCode2(String code2) {
        this.code2 = code2;
    }
    public Set getCities() {
        return this.cities;
    }
    
    public void setCities(Set cities) {
        this.cities = cities;
    }




}

