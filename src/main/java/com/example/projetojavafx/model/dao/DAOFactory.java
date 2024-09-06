package com.example.projetojavafx.model.dao;

import com.example.projetojavafx.db.DB;
import com.example.projetojavafx.model.dao.impl.*;

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

    public static AdminDao createAdminDao(){
        return new AdminDaoJDBC(DB.getConnection());
    }

    public static ClassificacaoDao createClassificacaoDao(){
        return new ClassificacaoDaoJDBC(DB.getConnection());
    }

    public static ClubeDao createClubeDao(){
        return new ClubeDaoJDBC(DB.getConnection());
    }

    public static RodadaDao createRodadaDao(){
        return new RodadaDaoJDBC(DB.getConnection());
    }

    public static PartidaDao createPartidaDao(){
        return new PartidaDaoJDBC(DB.getConnection());
    }
}
