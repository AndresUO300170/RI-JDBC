package uo.ri.cws.application.service.invoice.create;

import uo.ri.cws.application.persistence.invoice.InvoicingWorkOrderGateway;
import uo.ri.cws.application.persistence.invoice.InvoicingWorkOrderGateway.InvoicingWorkOrderRecord;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;

import java.time.LocalDate;
import java.util.Optional;

public class InvoiceWorkOrderDtoAssembler {

    public InvoicingWorkOrderRecord toRecord(InvoiceDto dto){
        InvoicingWorkOrderRecord r = new InvoicingWorkOrderRecord();
        r.id = dto.id;
        r.number = dto.number;
        r.date = dto.date;
        r.vat = dto.vatPercentage;
        r.amount = dto.subtotal;
        r.state = dto.state;
        r.version = dto.version;
        return r;
    }

    public Optional<InvoicingWorkOrderDto> toDto(Optional<InvoicingWorkOrderRecord> byNif){
        if(byNif.isEmpty())
            return Optional.empty();
        InvoicingWorkOrderRecord r = byNif.get();
        InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();
        dto.id = r.id;
        dto.description = r.description;
        dto.date = LocalDate.from(r.date.atStartOfDay());
        dto.state = r.state;

        return Optional.of(dto);
    }
}
