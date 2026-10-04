package uo.ri.cws.application.service.invoice.create;

import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.create.commands.FindNotInvoicedWorkOrdersByClient;
import uo.ri.util.exception.BusinessException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InvoiceServiceImpl implements InvoicingService {

    @Override
    public InvoiceDto create(List<String> workOrderIds) throws BusinessException {
        return null;
    }

    @Override
    public List<InvoicingWorkOrderDto> findWorkOrdersByClientNif(String nif) throws BusinessException {
        return List.of();
    }

    @Override
    public List<InvoicingWorkOrderDto> findNotInvoicedWorkOrdersByClientNif(String nif) throws BusinessException {
        return new FindNotInvoicedWorkOrdersByClient(nif).execute();
    }

    @Override
    public List<InvoicingWorkOrderDto> findWorkOrdersByPlateNumber(String plate) throws BusinessException {
        return List.of();
    }

    @Override
    public Optional<InvoiceDto> findInvoiceByNumber(Long number) throws BusinessException {
        return Optional.empty();
    }

    @Override
    public List<PaymentMeanDto> findPayMeansByClientNif(String nif) throws BusinessException {
        return List.of();
    }

    @Override
    public void settleInvoice(String invoiceId, Map<String, BigDecimal> charges) throws BusinessException {

    }
}
