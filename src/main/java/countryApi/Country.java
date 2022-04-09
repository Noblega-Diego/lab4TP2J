package countryApi;

@lombok.Data
public class Country {
    private String name;
    private String[] topLevelDomain;
    private String alpha2Code;
    private String alpha3Code;
    private String[] callingCodes;
    private String capital;
    private String[] altSpellings;
    private String subregion;
    private String region;
    private Long population;
    private long[] latlng;
    private String demonym;
    private Long area;
    private Double gini;
    private String[] timezones;
    private String[] borders;
    private String nativeName;
    private String numericCode;
    private Flags flags;
    private Currency[] currencies;
    private Language[] languages;
    private Translations translations;
    private String flag;
    private RegionalBloc[] regionalBlocs;
    private String cioc;
    private Boolean independent;
}
