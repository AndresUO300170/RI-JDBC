package uo.ri.cws.application.persistence.invoice.impl;

import uo.ri.cws.application.persistence.invoice.InvoicingWorkOrderGateway;
import uo.ri.cws.application.persistence.invoice.WorkOrderGateway.WorkOrderRecord;
import uo.ri.cws.application.persistence.util.assembler.BaseRecordAssembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.function.Supplier;

public class WorkOrderRecordAssembler extends BaseRecordAssembler<WorkOrderRecord> {

    public WorkOrderRecordAssembler() {
        super(WorkOrderRecord::new);
    }

    public WorkOrderRecord toRecord(ResultSet rs) throws SQLException {
        WorkOrderRecord r = super.toRecord(rs);
        r.vehicleId = rs.getString("vehicle_id");
        r.description = rs.getString("description");
        r.date = ((LocalDate) rs.getObject("date")).atStartOfDay();
        r.state = rs.getString("state");
        r.amount = rs.getBigDecimal("amount");
        return r;
    }
}
