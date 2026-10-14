package uo.ri.cws.application.persistence.invoice.impl;

import uo.ri.cws.application.persistence.invoice.InvoicingWorkOrderGateway;
import uo.ri.cws.application.persistence.invoice.InvoicingWorkOrderGateway.InvoicingWorkOrderRecord;
import uo.ri.cws.application.persistence.util.assembler.BaseRecordAssembler;

import java.sql.ResultSet;
import java.sql.SQLException;


public class InvoicingWorkOrderRecordAssembler extends BaseRecordAssembler<InvoicingWorkOrderRecord> {

    public InvoicingWorkOrderRecordAssembler() {
        super(InvoicingWorkOrderRecord::new);
    }

    public InvoicingWorkOrderRecord toRecord(ResultSet rs) throws SQLException {
        InvoicingWorkOrderRecord r = super.toRecord(rs);
        r.description = rs.getString("description");
        r.date = (java.time.LocalDate) rs.getObject("date");
        r.state = rs.getString("state");
        r.amount = rs.getBigDecimal("amount");
        return r;
    }
}
