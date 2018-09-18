package me.helloworlds.iPou;

//192.168.1. wifi rumah
//192.168.43.157 hp
//10.133.1.154
public class BaseAPI {
    public static final String baseURL = "http://192.168.1.8/iPou/";
    public static final String registerURL = baseURL + "register.php";
    public static final String loginURL = baseURL + "login.php";
    public static final String hargaURL = baseURL + "harga.php";
    public static final String hargaayamURL = baseURL + "hargaayam.php";
    public static final String tampilKandangURL = baseURL + "invest.php?apicall=get_kandang";
    public static final String tambahInvestURL = baseURL + "invest.php?apicall=create_invest";
    public static final String tampilTotalInvestorURL = baseURL + "invest.php?apicall=get_total_investor";
    public static final String tampilInvestorURL = baseURL + "invest.php?apicall=get_investor_by_id";
    public static final String verifInvestURL = baseURL + "invest.php?apicall=verif_invest";
    public static final String tampilJumlahUangURL = baseURL + "invest.php?apicall=get_jumlah_uang";
}
