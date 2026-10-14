package uo.ri.cws.application.persistence.mechanic.impl;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.persistence.util.assembler.BaseRecordAssembler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MechanicRecordAssembler extends BaseRecordAssembler<MechanicRecord> {

    public MechanicRecordAssembler() {
        super(MechanicRecord::new);
    }

    public MechanicRecord toRecord (ResultSet rs) throws SQLException {
        MechanicRecord r = super.toRecord(rs);
        r.nif = rs.getString("nif");
        r.name = rs.getString("name");
        r.surname = rs.getString("surname");
        return r;
    }
}
