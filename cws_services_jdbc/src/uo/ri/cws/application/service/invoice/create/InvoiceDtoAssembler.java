package uo.ri.cws.application.service.invoice.create;

import uo.ri.cws.application.persistence.invoice.InvoicingWorkOrderGateway.InvoicingWorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;

import java.util.Optional;

public class InvoiceDtoAssembler {

    public InvoicingWorkOrderRecord toRecord(InvoiceDto dto){
        InvoicingWorkOrderRecord r = new InvoicingWorkOrderRecord();
        r.id = dto.id;
        r.number = dto.number;
        r.date = dto.date;
        r.vat = dto.vatPercentage;
        r.amount = dto.subtotal;
        r.state = dto.state;
        return r;
    }

    public Optional<InvoiceDto> toDto(Optional<InvoicingWorkOrderRecord> record){
        if(record.isEmpty())
            return Optional.empty();
        InvoicingWorkOrderRecord r = record.get();
        InvoiceDto dto = new InvoiceDto();
        dto.id = r.id;
        dto.number = r.number;
        dto.date = r.date;
        dto.vatPercentage = r.vat;
        dto.subtotal = r.amount;
        dto.state = r.state;

        return Optional.of(dto);
    }
}
