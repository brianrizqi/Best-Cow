package me.helloworlds.iPou;

//192.168.1. wifi rumah
//192.168.43.157 hp
//10.133.1.154
public class BaseAPI {
    public static final String baseURL = "http://192.168.1.106/iPou/";
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
    public static final String tampilStatusAyamURL = baseURL + "schedule.php?apicall=get_status_ayam";
    public static final String tampilJadwalURL = baseURL + "schedule.php?apicall=get_schedule";
    public static final String tambahScheduleURL = baseURL + "schedule.php?apicall=create_schedule";
    public static final String getUserURL = baseURL + "user.php?apicall=get_user";
    public static final String updateUserURL = baseURL + "user.php?apicall=update_user";
}
