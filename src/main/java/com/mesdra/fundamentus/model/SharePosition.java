package com.mesdra.fundamentus.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import org.jsoup.select.Elements;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SharePosition {

    private String papel;
    private Float cotacao;
    private Float pl;
    private Float pvp;
    private Float psr;
    private Float divYeld;
    private Float pAtivo;
    private Float pCapGiro;
    private Float pEbit;
    private Float pAtivCircLiq;
    private Float evEbit;
    private Float evEbitda;
    private Float mrgEbit;
    private Float mrgLiq;
    private Float mrgCorr;
    private Float roic;
    private Float roe;
    private Float liq2Messes;
    private BigDecimal patrimLiq;
    private BigDecimal divBrutPatrim;
    private Float cresRec5a;

    public SharePosition(Elements columns) {
        columns.get(0).text();
        this.papel = columns.get(0).text();
        this.cotacao = parseFloat(columns.get(1).text());
        this.pl = parseFloat(columns.get(2).text());
        this.pvp = parseFloat(columns.get(3).text());
        this.psr = parseFloat(columns.get(4).text());
        this.divYeld = parseFloat(columns.get(5).text());
        this.pAtivo = parseFloat(columns.get(6).text());
        this.pCapGiro = parseFloat(columns.get(7).text());
        this.pEbit = parseFloat(columns.get(8).text());
        this.pAtivCircLiq = parseFloat(columns.get(9).text());
        this.evEbit = parseFloat(columns.get(10).text());
        this.evEbitda = parseFloat(columns.get(11).text());
        this.mrgEbit = parseFloat(columns.get(12).text());
        this.mrgLiq = parseFloat(columns.get(13).text());
        this.mrgCorr = parseFloat(columns.get(14).text());
        this.roic = parseFloat(columns.get(15).text());
        this.roe = parseFloat(columns.get(16).text());
        this.liq2Messes = parseFloat(columns.get(17).text());
        this.patrimLiq = parseBigDecimal(columns.get(18).text());
        this.divBrutPatrim = parseBigDecimal(columns.get(19).text());
        this.cresRec5a = parseFloat(columns.get(20).text());

    }

    private BigDecimal parseBigDecimal(String text) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        String pattern = "#,###.#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        BigDecimal parsedStringValue;
        try {
            parsedStringValue = (BigDecimal) decimalFormat.parse(text.replaceAll("%", ""));
            return parsedStringValue;
        } catch (ParseException e) {
            return new BigDecimal(-1);
        }

    }

    private Float parseFloat(String text) {

        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            DecimalFormat format = new DecimalFormat();
            format.setDecimalFormatSymbols(symbols);
            return format.parse(text.replace("%", "")).floatValue();
        } catch (NumberFormatException | ParseException e) {
            return -1F;
        }

    }

}
