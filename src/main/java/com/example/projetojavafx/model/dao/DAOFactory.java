package com.example.projetojavafx.model.dao;

import com.example.projetojavafx.db.DB;
import com.example.projetojavafx.model.dao.impl.CampeonatoDaoJDBC;
import com.example.projetojavafx.model.dao.impl.DivisaoDaoJDBC;

public class DAOFactory {

    public static DivisaoDao createDivisaoDao(){
        return new DivisaoDaoJDBC(DB.getConnection());
    }

    public static CampeonatoDao createCampeonatoDao(){
        return new CampeonatoDaoJDBC(DB.getConnection());
    }
}
