package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.util.assertion.BusinessChecks;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

import java.util.List;

public class DeleteMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {

        String idMechanic = Console.readString("Type mechanic id ");

        BusinessChecks.exists(Factories.service.forMechanicCrudService().findById(idMechanic),
                "Mechanic does not exist");

        Factories.service.forMechanicCrudService().delete(idMechanic);

        Console.println("Mechanic deleted");
    }

}