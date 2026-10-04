package uo.ri.cws.application.ui.cashier.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class FindNotInvoicedWorkOrdersByClientAction implements Action {

    /**
     * Process:
     * - Ask customer nif
     * - Display all uncharged workorder (status <> 'INVOICED').
     * For each workorder, display id, vehicle id, date, status, amount
     * and description
     *
     * @return
     */

    @Override
    public void execute() throws BusinessException {
        String nif = Console.readString("Client nif ");

        Console.println("\nClient's not invoiced work orders\n");

        List<InvoicingWorkOrderDto> dto = Factories.service.forCreateInvoiceService()
                .findNotInvoicedWorkOrdersByClientNif(nif);

        for(InvoicingWorkOrderDto wo: dto)
            Console.printf("\t%s \t%-40.40s \t%td/%<tm/%<tY \t%-12.12s \t%.2f\n"
                    , wo.id, wo.description, wo.date, wo.state, wo.amount);
    }

}