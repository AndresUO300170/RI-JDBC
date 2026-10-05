package uo.ri.cws.application.ui.manager.mechanic.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.BusinessChecks;
import uo.ri.util.console.Console;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.menu.Action;

public class UpdateMechanicAction implements Action {

    @Override
    public void execute() throws BusinessException {
        MechanicDto dto = new MechanicDto();
        // Get info
        dto.id = Console.readString("Type mechanic id to update");

        // check mechanic exists
        BusinessChecks.exists(Factories.service.forMechanicCrudService().findById(dto.id),
                "Mechanic does not exist");
        // Ask for new data
        // nif is the identity, cannot be changed
        dto.name = Console.readString("Name");
        dto.surname = Console.readString("Surname");

        // update
        Factories.service.forMechanicCrudService().update(dto);

        // Print result
        Console.println("Mechanic updated");
    }
}