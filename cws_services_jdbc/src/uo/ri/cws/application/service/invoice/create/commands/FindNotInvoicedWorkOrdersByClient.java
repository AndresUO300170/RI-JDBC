package uo.ri.cws.application.service.invoice.create.commands;

import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.cws.application.persistence.util.jdbc.Jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindNotInvoicedWorkOrdersByClient {

    private static final String TWORKORDERS_FINDNOTINVOICED =
            "select a.id, a.description, a.date, a.state, a.amount"
                    + " from TWorkOrders as a, TVehicles as v, TClients as c"
                    + " where a.vehicle_id = v.id" + " and v.client_id = c.id"
                    + "state like 'APPROVED' and nif like ?";

    private String nif;

    public FindNotInvoicedWorkOrdersByClient(String nif) {
        ArgumentChecks.isNotBlank(nif, "Invalid nif");
        this.nif = nif;
    }

    public List<InvoicingWorkOrderDto> execute(){

        List<InvoicingWorkOrderDto> findByClient = new ArrayList<>();

        try (Connection c = Jdbc.createThreadConnection()) {
            try (PreparedStatement pst = c
                    .prepareStatement(TWORKORDERS_FINDNOTINVOICED)) {
                pst.setString(1, nif);
                try (ResultSet rs = pst.executeQuery();) {
                    while (rs.next()) {
                        InvoicingWorkOrderDto invoicingWorkOrderDto = new InvoicingWorkOrderDto();
                        invoicingWorkOrderDto.id =  rs.getString(1);
                        invoicingWorkOrderDto.description = rs.getString(2);
                        invoicingWorkOrderDto.date = rs.getTimestamp(3).toLocalDateTime();
                        invoicingWorkOrderDto.state = rs.getString(4);
                        invoicingWorkOrderDto.amount = BigDecimal.valueOf(rs.getDouble(5));
                        findByClient.add(invoicingWorkOrderDto);
                    }
                }
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        return findByClient;
    }

}
