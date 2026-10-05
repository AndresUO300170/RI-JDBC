package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class ListAllMechanicsAction implements Action {

    private static final String TMECHANICS_FINDALL = "SELECT ID, NAME, "
            + "SURNAME, NIF, VERSION FROM TMECHANICS";

    @Override
    public void execute() throws BusinessException {

        Console.println("\nList of mechanics \n");

        List<MechanicDto> result = Factories.service.forMechanicCrudService().findAll();
        for (MechanicDto m : result)
            Printer.printMechanic(m);

    }
}