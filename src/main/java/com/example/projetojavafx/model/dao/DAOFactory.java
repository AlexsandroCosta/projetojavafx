package com.example.projetojavafx.model.dao;

import com.example.projetojavafx.db.DB;
import com.example.projetojavafx.model.dao.impl.CampeonatoDaoJDBC;
import com.example.projetojavafx.model.dao.impl.DivisaoDaoJDBC;
import com.example.projetojavafx.model.dao.impl.EstadoDaoJDBC;
import com.example.projetojavafx.model.dao.impl.PaisDaoJDBC;

public class DAOFactory {

    public static DivisaoDao createDivisaoDao(){
        return new DivisaoDaoJDBC(DB.getConnection());
    }

    public static CampeonatoDao createCampeonatoDao(){
        return new CampeonatoDaoJDBC(DB.getConnection());
    }

    public static PaisDao createPaisDao(){
        return new PaisDaoJDBC(DB.getConnection());
    }

    public static EstadoDao createEstadoDao(){
        return new EstadoDaoJDBC(DB.getConnection());
    }
}
