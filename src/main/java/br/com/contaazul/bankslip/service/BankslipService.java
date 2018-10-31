package br.com.contaazul.bankslip.service;

import java.util.List;

import br.com.contaazul.bankslip.entity.Bankslip;
import javassist.NotFoundException;

public interface BankslipService {

    Bankslip createBankslip(Bankslip bankslip);

    List<Bankslip> listBankslips();

    Bankslip detailsBankslip(String bankslipId) throws NotFoundException;

    void payBankslip(String bankslipId, Bankslip bankslipRequest) throws NotFoundException;

    void cancelBankslip(String bankslipId) throws NotFoundException;

}
