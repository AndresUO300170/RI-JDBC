package uo.ri.cws.application.ui.manager.mechanic.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

import java.util.Optional;

public class ListMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {

        // Get info
        String nif = Console.readString("nif");

        Console.println("\nMechanic information \n");

        Optional<MechanicDto> dto = Factories.service.forMechanicCrudService().findByNif(nif);

        dto.ifPresent(Printer::printMechanic);
    }
}