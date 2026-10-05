package uo.ri.cws.application.persistence;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.impl.MechanicGatewayImpl;

public class PersistenceFactoryImpl implements PersistenceFactory{

    @Override
    public MechanicGateway forMechanic() {
        return new MechanicGatewayImpl();
    }
}
