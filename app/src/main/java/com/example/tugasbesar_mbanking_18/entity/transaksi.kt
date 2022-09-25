package com.example.tugasbesar_mbanking_18.entity

class Transaksi (var balance: String, var nama: String){

    companion object{
        @JvmField
        var listofTransaksi = arrayOf(
            Transaksi("+10000", "mama"),
            Transaksi("+15000", "kakak"),
            Transaksi("+20000", "mama"),
            Transaksi("-10000", "tante"),
            Transaksi("+15000", "papa"),
            Transaksi("+10000", "kakak"),
            Transaksi("-20000", "tante"),
            Transaksi("+30000", "mama"),
            Transaksi("+50000", "kakak"),
            Transaksi("+50000", "papa")
        )
    }
}