package uo.ri.cws.application.persistence.invoice;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.invoice.InvoicingWorkOrderGateway.InvoicingWorkOrderRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface InvoicingWorkOrderGateway extends Gateway<InvoicingWorkOrderRecord> {

    public List<InvoicingWorkOrderRecord> findNotInvoicedWorkOrdersByClient(String nif);
    public long generateInvoiceNumber();

    public class InvoicingWorkOrderRecord extends BaseRecord{
        public long number;
        public String description;
        public LocalDate date;
        public String state;
        public BigDecimal amount;
        public BigDecimal vat;
    }
}
