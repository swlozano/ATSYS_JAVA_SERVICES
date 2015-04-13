/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Entidades.Rol;
import Utilidades.DAO;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class RolExampleDao extends DAO {

    public void obtenerRolesPorNombre(String nombreRol) {
        List<Rol> roles = new ArrayList<Rol>();
        try {
            begin();
            Criteria criteria = getSession().createCriteria(Rol.class);
            criteria.add(Restrictions.isNull("nombre"));
            roles = criteria.list();
        } catch (Exception e) {
        } finally {
            close();
        }
    }
}
